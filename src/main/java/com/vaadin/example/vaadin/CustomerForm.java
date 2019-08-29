package com.vaadin.example.vaadin;


import com.sun.org.apache.xml.internal.utils.StringToStringTable;
import com.sun.org.apache.xml.internal.utils.StringToStringTableVector;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.PropertyId;
import com.vaadin.flow.data.converter.StringToIntegerConverter;

import java.awt.print.Pageable;

public class CustomerForm extends FormLayout {
    private TextField firstName = new TextField();
    private TextField lastName = new TextField();
    private TextField phoneNumber = new TextField();
    private EmailField email = new EmailField();
    private ComboBox<CustomerStatus> status = new ComboBox<>();
    private ComboBox<PhoneNumberType> phoneNumberType = new ComboBox<>();
    private ComboBox<EmailType> emailType = new ComboBox<>();
    private DatePicker birthDate = new DatePicker();
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Button addPhBtn = new Button();
    private Button addEmail = new Button();
    private Binder<Customer> binder = new Binder<>(Customer.class);
    private Binder<HorizontalLayout> binderPh = new Binder<>(HorizontalLayout.class);
    private HorizontalLayout horizonPh = new HorizontalLayout();

    private MainView mainView;
    private CustomerService customerService = CustomerService.getInstance();


    public CustomerForm(MainView mainView) {
        this.mainView = mainView;
        status.setItems(CustomerStatus.values());
        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        save.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        delete.setIcon(VaadinIcon.TRASH.create());

        firstName.setPlaceholder("First name");
        firstName.setPrefixComponent(VaadinIcon.USER.create());

        lastName.setPlaceholder("Last name");
        lastName.setPrefixComponent(VaadinIcon.USER.create());

        phoneNumber.setPrefixComponent(VaadinIcon.PHONE.create());
        phoneNumber.setPlaceholder("Phone number");

        phoneNumberType.setPlaceholder("select one ...");
        phoneNumberType.setItems(PhoneNumberType.values());
        addPhBtn.setIcon(VaadinIcon.PLUS.create());
        addPhBtn.addClickListener(event -> horizonPh.add(addPhBtn, phoneNumberType, phoneNumber));
        addEmail.setIcon(VaadinIcon.PLUS.create());

        email.setPlaceholder("Email");
        email.setPrefixComponent(VaadinIcon.ENVELOPE_O.create());

        emailType.setPlaceholder("select email type ...");
        emailType.setItems(EmailType.values());

        birthDate.setPlaceholder("Pick a date");

        status.setPlaceholder("select contact type ...");

        horizonPh.add(addPhBtn, phoneNumberType, phoneNumber);

        HorizontalLayout horizonE = new HorizontalLayout(addEmail, emailType, email);
        //horizonPh.setBoxSizing();
        horizonE.setSizeFull();
//        binder.forField(phoneNumber)
//                .withConverter(new StringToIntegerConverter(""))
//                .bind(Customer::getPhoneNumber, Customer::setPhoneNumber);


        add(firstName, lastName, birthDate, status,horizonE, horizonPh, buttons);

        binder.bindInstanceFields(this);
        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
    }



    public void setCustomer(Customer customer) {
        binder.setBean(customer);

        if (customer == null) {
            setVisible(false);
        } else {
            setVisible(true);
            firstName.focus();
        }
    }

    private void save() {
        Customer customer = binder.getBean();
        customerService.save(customer);
        mainView.updateList(null);
        setCustomer(null);
    }

    private void delete() {
        Customer customer = binder.getBean();
        customerService.delete(customer);
        mainView.updateList(null);
        setCustomer(null);
    }
}
