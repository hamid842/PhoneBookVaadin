package com.vaadin.example.vaadin;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchForm extends FormLayout {

    public static TextField quickSearchValue = new TextField();
    public static DatePicker quickSearchBirthDate = new DatePicker();
    public static ComboBox quickSearchItem = new ComboBox();
    private EmailField advEmail = new EmailField("Email");
    private TextField advLastName = new TextField("Last Name");
    private TextField advFirstName = new TextField("First Name");
    private DatePicker advBirthDate = new DatePicker("Birth Date");
    public MainView mainView;
    private Customer customer = new Customer();
    private CustomerService customerService = CustomerService.getInstance();

    public SearchForm(MainView mainView) {
        this.mainView = mainView;
        //
        RadioButtonGroup<String> searchTypeSelection = new RadioButtonGroup<>();
        HorizontalLayout quickSearchArea = new HorizontalLayout();
        HorizontalLayout advancedSearchArea = new HorizontalLayout();
        //
        initQuickSearchArea(quickSearchArea);
        initAdvancedSearchArea(advancedSearchArea);
        //
        searchTypeSelection.setLabel("SEARCH MODE : ");
        searchTypeSelection.setItems("Quick Search", "Advanced Search");
        searchTypeSelection.addValueChangeListener(e -> {
            if ("Quick Search".equals(e.getValue())) {
                quickSearchArea.setVisible(true);
                advancedSearchArea.setVisible(false);
            } else {
                quickSearchArea.setVisible(false);
                advancedSearchArea.setVisible(true);
            }

        });
        //
        add(searchTypeSelection, quickSearchArea, advancedSearchArea);
    }

    private void initQuickSearchArea(HorizontalLayout quickSearchArea) {
        Button quickSearchButton = new Button("Quick search");
        Button refreshBtn = new Button("Refresh");
        //
        quickSearchButton.setIcon(VaadinIcon.SEARCH.create());
        quickSearchButton.addClickShortcut(Key.ENTER);
        quickSearchButton.addClickListener(event -> quickSearch());
        //
        refreshBtn.setIcon(VaadinIcon.REFRESH.create());
        refreshBtn.addClickListener(event -> mainView.updateList(null));
        //
        quickSearchItem.setItems(QuickSearchItem.values());
        quickSearchItem.setPlaceholder("Search Items ...");
        quickSearchBirthDate.setVisible(false);
        quickSearchItem.addValueChangeListener(e -> {
            if (QuickSearchItem.BirthDate.equals(e.getValue())) {
                quickSearchValue.setVisible(false);
                quickSearchBirthDate.setVisible(true);
            } else {
                quickSearchValue.setVisible(true);
                quickSearchBirthDate.setVisible(false);
            }
        });
        quickSearchValue.setPlaceholder("Filter By ...");
        quickSearchBirthDate.setPlaceholder("Filter By ...");

        quickSearchValue.setClearButtonVisible(true);
        quickSearchBirthDate.setClearButtonVisible(true);

        //
        quickSearchArea.setSizeFull();
        quickSearchArea.setAlignItems(FlexComponent.Alignment.END);
        quickSearchArea.add(quickSearchItem, quickSearchValue, quickSearchBirthDate, quickSearchButton , refreshBtn);
        quickSearchArea.setVisible(false);
    }

    private void initAdvancedSearchArea(HorizontalLayout advancedSearchArea) {
        Button advancedSearchButton = new Button("Advanced search");
        Button refreshBtn = new Button("Refresh");
        refreshBtn.setIcon(VaadinIcon.REFRESH.create());
        refreshBtn.addClickListener(event -> mainView.updateList(null));
        advancedSearchButton.setIcon(VaadinIcon.SEARCH.create());
        advancedSearchArea.addClickShortcut(Key.ENTER);

        advEmail.setClearButtonVisible(true);
        advEmail.setErrorMessage("Please enter a valid email address");
        advLastName.setClearButtonVisible(true);
        advFirstName.setClearButtonVisible(true);
        advBirthDate.setClearButtonVisible(true);
        //
        advancedSearchButton.addClickListener(e -> advancedSearch());
        //
        advancedSearchArea.setSizeFull();
        advancedSearchArea.setAlignItems(FlexComponent.Alignment.END);
        advancedSearchArea.add(advFirstName, advLastName, advEmail, advBirthDate, advancedSearchButton , refreshBtn);
        advancedSearchArea.setVisible(false);
    }

    public void quickSearch() {
        Object currentSearchItem = quickSearchItem.getValue();
        List<Customer> filterList = null;
        if (QuickSearchItem.FirstName.equals(currentSearchItem) || QuickSearchItem.LastName.equals(currentSearchItem)) {
            filterList = customerService.quickSearch(currentSearchItem, quickSearchValue.getValue());
        } else if (QuickSearchItem.BirthDate.equals(currentSearchItem)) {
            filterList = customerService.quickSearch(currentSearchItem, quickSearchBirthDate.getValue());
        }
        mainView.updateList(filterList);
    }

    public void advancedSearch() {
        Map<String, Object> filter = new HashMap<>();

        filter.put("FirstName",advFirstName.getValue());
        filter.put("LastName" , advLastName.getValue());
        filter.put("Email" , advEmail.getValue());
        filter.put("BirthDate" , advBirthDate.getValue());

        List<Customer> filterList = customerService.advancedSearch(filter);
        mainView.updateList(filterList);
    }
}