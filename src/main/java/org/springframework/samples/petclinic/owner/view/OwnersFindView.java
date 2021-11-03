package org.springframework.samples.petclinic.owner.view;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.samples.petclinic.base.view.MainContentLayout;
import org.springframework.samples.petclinic.owner.domain.Owner;
import org.springframework.samples.petclinic.owner.domain.Pet;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteParameters;
import com.vaadin.flow.router.RouterLink;

@Route(value = "owners/find", layout = MainContentLayout.class)
public class OwnersFindView extends VerticalLayout {

    private final Grid<Owner> ownersGrid;

    private final TextField lastNameTextField;

    OwnersFindView(OwnersFindPresenter presenter) {
        presenter.setView(this);

        setSizeFull();

        H2 title = new H2(getTranslation("findOwners"));

        lastNameTextField = new TextField(getTranslation("lastName"));
        FormLayout form = new FormLayout(lastNameTextField);

        Button findOwnerButton = new Button(getTranslation("findOwner"));

        Button addOwnerButton = new Button(getTranslation("addOwner"));
        addOwnerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addOwnerButton.addClickListener(e -> UI.getCurrent().navigate(OwnerCreateView.class));

        ownersGrid = new Grid<>();
        ownersGrid.addColumn(new ComponentRenderer<>(owner -> new RouterLink(owner.getFirstName() + " " + owner.getLastName(), OwnerDetailsView.class,
                new RouteParameters("ownerId", owner.getId().toString())))).setHeader(getTranslation("name"));
        ownersGrid.addColumn(Owner::getAddress).setHeader(getTranslation("address"));
        ownersGrid.addColumn(Owner::getCity).setHeader(getTranslation("city"));
        ownersGrid.addColumn(Owner::getTelephone).setHeader(getTranslation(("telephone")));
        ownersGrid.addColumn(owner -> owner.getPets().stream().map(Pet::toString).collect(Collectors.joining(", "))).setHeader(getTranslation("pets"));
        ownersGrid.setItems(new ArrayList<>());

        findOwnerButton.addClickListener(
                e -> ownersGrid.setItems(query -> presenter.find(query.getPage(), query.getPageSize()),
                        query -> presenter.getCount(query.getPage(), query.getPageSize())));

        VerticalLayout formContainer = new VerticalLayout(form, new HorizontalLayout(findOwnerButton, addOwnerButton));
        formContainer.setPadding(false);

        add(title, formContainer, ownersGrid);
    }

    public void showMessage(String messageKey) {
        Notification.show(getTranslation(messageKey));
    }

    public String getLastName() {
        return lastNameTextField.getValue();
    }

}
