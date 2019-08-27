package com.vaadin.example.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

public class SearchForm extends FormLayout {

    TextField quickSearchValue = new TextField();
    DatePicker quickSearchBirthDate = new DatePicker();

    TextField advEmail = new TextField("Email");
    TextField advLastName = new TextField("Last name");
    TextField advFirstName = new TextField("First name");
    DatePicker advBirthDate = new DatePicker("Birth date");


    private MainView mainView;

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
        searchTypeSelection.setLabel("Search mode: ");
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
        ComboBox quickSearchItem = new ComboBox();
        //
        quickSearchItem.setItems(QuickSearchItem.values());
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

        quickSearchValue.setValueChangeMode(ValueChangeMode.ON_CHANGE);
        quickSearchValue.addValueChangeListener(e -> quickSearch());
        //
        quickSearchButton.addClickListener(e -> quickSearch());
        //
        quickSearchArea.setAlignItems(FlexComponent.Alignment.END);
        quickSearchArea.add(quickSearchItem, quickSearchValue, quickSearchBirthDate, quickSearchButton);
        quickSearchArea.setVisible(false);
    }

    private void initAdvancedSearchArea(HorizontalLayout advancedSearchArea) {
        Button advancedSearchButton = new Button("Advanced search");

        advEmail.setClearButtonVisible(true);
        advLastName.setClearButtonVisible(true);
        advFirstName.setClearButtonVisible(true);
        advBirthDate.setClearButtonVisible(true);
        //
        advancedSearchButton.addClickListener(e -> advancedSearch());
        //
        advancedSearchArea.setAlignItems(FlexComponent.Alignment.END);
        advancedSearchArea.add(advFirstName, advLastName, advEmail, advBirthDate, advancedSearchButton);
        advancedSearchArea.setVisible(false);
    }

    private void quickSearch() {
        mainView.updateList(null);
    }

    private void advancedSearch() {
        mainView.updateList(null);
    }
}
