package com.vaadin.example.vaadin;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.button.Button;

import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

import java.util.List;

@Route("")
@PWA(name = "Project Base for Vaadin Flow", shortName = "Project Base")
public class MainView extends VerticalLayout {

    private CustomerService customerService = CustomerService.getInstance();
    private Grid<Customer> grid = new Grid<>(Customer.class);
    private SearchForm searchForm = new SearchForm(this);
    private CustomerForm customerForm = new CustomerForm(this);
    private Button addNew = new Button("New");

    public MainView() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();
        verticalLayout.add(addNew);
        verticalLayout.setHorizontalComponentAlignment(Alignment.END, addNew);
        HorizontalLayout addNewAndSearchMode = new HorizontalLayout();
        addNewAndSearchMode.add(addNew, searchForm);
        HorizontalLayout formAndGrid = new HorizontalLayout();
        formAndGrid.add(grid, customerForm);
        addNew.setVisible(true);
        addNew.setIcon(VaadinIcon.PLUS.create());
        addNew.addClickListener(e -> {
            grid.asSingleSelect().clear();
            customerForm.setCustomer(new Customer());
        });
        grid.setColumns("firstName", "lastName","status", "phoneNumber", "email", "birthDate");
        grid.setColumnReorderingAllowed(true);
        grid.setVisible(true);
        grid.asSingleSelect().addValueChangeListener(event ->
                customerForm.setCustomer(grid.asSingleSelect().getValue()));
        VerticalLayout mainContent = new VerticalLayout(customerForm, grid);
        mainContent.setSizeFull();
        grid.setSizeFull();

        add(addNew, searchForm, mainContent);
        setSizeFull();
        updateList(null);
        customerForm.setCustomer(null);
    }

    public List<Customer> updateList(List<Customer> list) {
        if (list == null)
            grid.setItems(customerService.findAll(null));
        else
            grid.setItems(list);
        return list;
    }

}
