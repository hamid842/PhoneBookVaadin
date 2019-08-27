package com.vaadin.example.vaadin;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.button.Button;

import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

import javax.swing.table.TableRowSorter;
import java.awt.*;

@Route("")
@PWA(name = "Project Base for Vaadin Flow", shortName = "Project Base")
public class MainView extends VerticalLayout {

    private CustomerService customerService = CustomerService.getInstance();
    private Grid<Customer> grid = new Grid<>(Customer.class);

    private SearchForm searchForm = new SearchForm(this);
    private CustomerForm detailForm = new CustomerForm(this);

    public MainView() {

        grid.setColumns("firstName", "lastName", "birthDate", "email");

        HorizontalLayout mainContent = new HorizontalLayout(grid, detailForm);
        mainContent.setSizeFull();
        grid.setSizeFull();

        add(searchForm, mainContent);

        updateList(null);
    }

    public void updateList(String filter) {
        grid.setItems(customerService.findAll(filter));
    }

}
