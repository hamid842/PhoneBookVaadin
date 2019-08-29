package com.vaadin.example.vaadin;

import com.vaadin.flow.component.page.Page;
import org.springframework.stereotype.Component;

import java.awt.print.Pageable;
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
            final String[] names = new String[]{"Gabrielle Patel", "Brian Robinson", "Eduardo Haugen",
                    "Koen Johansen", "Alejandro Macdonald", "Angel Karlsson", "Yahir Gustavsson", "Haiden Svensson",
                    "Emily Stewart", "Corinne Davis", "Ryann Davis", "Yurem Jackson", "Kelly Gustavsson",
                    "Eileen Walker", "Katelyn Martin", "Israel Carlsson", "Quinn Hansson", "Makena Smith",
                    "Danielle Watson", "Leland Harris", "Gunner Karlsen", "Jamar Olsson", "Lara Martin",
                    "Ann Andersson", "Remington Andersson", "Rene Carlsson", "Elvis Olsen", "Solomon Olsen",
                    "Jaydan Jackson", "Bernard Nilsen"};
            Random r = new Random(0);
            for (String name : names) {
                String[] split = name.split(" ");
                Customer c = new Customer();
                c.setFirstName(split[0]);
                c.setLastName(split[1]);
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
        List<Customer> result = null;
        filter.forEach((k, v) -> {
                    filter.get("AAAA");
                    filter.get("BBBB");
                    filter.get("CCCC");
                });
        result = arrayList.stream()
                .filter(it->it.equals(filter))
                .collect(Collectors.toList());
        return result;
    }
}