package com.vaadin.example.vaadin.service;

import com.vaadin.example.vaadin.domain.Customer;
import com.vaadin.example.vaadin.domain.Email;
import com.vaadin.example.vaadin.domain.PhoneNumber;
import com.vaadin.example.vaadin.enumeration.CustomerStatus;
import com.vaadin.example.vaadin.enumeration.EmailType;
import com.vaadin.example.vaadin.enumeration.PhoneNumberType;
import com.vaadin.example.vaadin.enumeration.QuickSearchItem;
import com.vaadin.example.vaadin.form.MainView;
import com.vaadin.example.vaadin.form.SearchForm;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


@Component
public class CustomerService {
    private SearchForm s;
    private static CustomerService instance;
    private static final Logger LOGGER = Logger.getLogger(CustomerService.class.getName());
    private final HashMap<Long, Customer> contacts = new HashMap<>();
    private long nextId = 0;
    private MainView mainView;
    private Customer customer;

    private CustomerService() {
    }


    public static CustomerService getInstance() {
        if (instance == null) {
            instance = new CustomerService();
            instance.ensureTestData();
        }
        return instance;
    }


    public synchronized List<Customer> findAll() {
        return findAll(null);
    }


    public synchronized List<Customer> findAll(String stringFilter) {
        ArrayList<Customer> arrayList = new ArrayList<>();
        for (Customer contact : contacts.values()) {
            try {
                boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                        || contact.toString().toLowerCase().contains(stringFilter.toLowerCase());
                if (passesFilter) {
                    arrayList.add(contact.clone());
                }
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(CustomerService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Collections.sort(arrayList, new Comparator<Customer>() {

            @Override
            public int compare(Customer o1, Customer o2) {
                return (int) (o2.getId() - o1.getId());
            }
        });
        return arrayList;
    }


    public synchronized List<Customer> findAll(String stringFilter, int start, int maxresults) {
        ArrayList<Customer> arrayList = new ArrayList<>();
        for (Customer contact : contacts.values()) {
            try {
                boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                        || contact.toString().toLowerCase().contains(stringFilter.toLowerCase());
                if (passesFilter) {
                    arrayList.add(contact.clone());
                }
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(CustomerService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Collections.sort(arrayList, new Comparator<Customer>() {

            @Override
            public int compare(Customer o1, Customer o2) {
                return (int) (o2.getId() - o1.getId());
            }
        });
        int end = start + maxresults;
        if (end > arrayList.size()) {
            end = arrayList.size();
        }
        return arrayList.subList(start, end);
    }


    public synchronized long count() {
        return contacts.size();
    }


    public synchronized void delete(Customer value) {
        contacts.remove(value.getId());
    }


    public synchronized void save(Customer entry) {
        if (entry == null) {
            LOGGER.log(Level.SEVERE,
                    "Customer is null. Are you sure you have connected your form to the application as described in tutorial chapter 7?");
            return;
        }
        if (entry.getId() == null) {
            entry.setId(nextId++);
        }
        try {
            entry = (Customer) entry.clone();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        contacts.put(entry.getId(), entry);
    }


    public void ensureTestData() {
        if (findAll().isEmpty()) {
            final String[] names = new String[]{"Gabrielle Patel FRIEND 09120658719 m@m.com",
                    "Brian Robinson PERSONAL 09011019011 d@d.com",
                    "Eduardo Haugen COLLEAGUE 09125673456 f@d.com",
                    "Koen Johansen OTHER 09125678976 h@s.com",
                    "Alejandro Macdonald FRIEND 09398904567 e@r.com",
                    "Angel Karlsson PERSONAL 09125674536 k@i.com"};
            Random r = new Random(0);
            for (String name : names) {
                String[] split = name.split(" ");
                Customer c = new Customer();
                c.setFirstName(split[0]);
                c.setLastName(split[1]);
                c.setStatus(CustomerStatus.valueOf(split[2]));
                PhoneNumber p = new PhoneNumber(split[3]  , PhoneNumberType.HOME ) ;
                c.setPhoneNumber(p);
                Email e = new Email(split[4] , EmailType.PERSONAL) ;
                c.setEmail(e);
                c.setStatus(CustomerStatus.values()[r.nextInt(CustomerStatus.values().length)]);
                c.setBirthDate(LocalDate.now().minusDays(r.nextInt(365 * 100)));
                save(c);
            }
        }
    }

    public List<Customer> quickSearch(Object searchItem, Object searchValue) {
        List<Customer> arrayList = findAll();
        List<Customer> result = null;

        if (QuickSearchItem.FirstName.equals(searchItem)) {
            result = arrayList.stream().
                    filter(it -> it.getFirstName().equals(searchValue))
                    .collect(Collectors.toList());
        } else if (QuickSearchItem.LastName.equals(searchItem)) {
            result = arrayList.stream().
                    filter(it -> it.getLastName().equals(searchValue))
                    .collect(Collectors.toList());
        } else if (QuickSearchItem.BirthDate.equals(searchItem)) {
            result = arrayList.stream()
                    .filter(it -> it.getBirthDate().equals(searchValue))
                    .collect(Collectors.toList());
        }
        return result;
    }

    public List<Customer> advancedSearch(Map<String, Object> filter) {
        List<Customer> arrayList = findAll();

        return arrayList.stream()
                .filter(s -> filter.get("FirstName").equals("EMPTY") || s.getFirstName().equals(filter.get("FirstName")))
                .filter(s -> filter.get("LastName").equals("EMPTY") || s.getLastName().equals(filter.get("LastName")))
                .filter(s -> filter.get("Email").equals("EMPTY") || s.getEmail().getEmail().equals(filter.get("Email")))
                .filter(s -> filter.get("BirthDate").equals("EMPTY") || s.getBirthDate().equals(filter.get("BirthDate")))
                .collect(Collectors.toList());

    }
}