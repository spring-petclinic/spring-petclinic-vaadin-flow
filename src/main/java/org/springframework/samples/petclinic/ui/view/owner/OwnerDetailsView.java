package org.springframework.samples.petclinic.ui.view.owner;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import com.vaadin.flow.component.AbstractCompositeField;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteParam;
import com.vaadin.flow.router.RouteParameters;
import org.springframework.samples.petclinic.backend.owner.Owner;
import org.springframework.samples.petclinic.backend.owner.Pet;
import org.springframework.samples.petclinic.backend.visit.Visit;
import org.springframework.samples.petclinic.ui.util.FormUtil;
import org.springframework.samples.petclinic.ui.view.MainContentLayout;
import org.springframework.samples.petclinic.ui.view.visit.VisitCreateView;

@Route(value = "owners/:ownerId([0-9]+)", layout = MainContentLayout.class)
public class OwnerDetailsView extends VerticalLayout implements BeforeEnterObserver {

    private static final String OWNER_ID_ROUTE_PARAM = "ownerId";

    private final Binder<Owner> binder = new Binder<>();

    private final OwnerDetailsPresenter presenter;

    OwnerDetailsView(OwnerDetailsPresenter presenter) {
        this.presenter = presenter;

        presenter.setView(this);

        H2 title = new H2(getTranslation("ownerInformation"));

        TextField nameTextField = new TextField();
        binder.bind(nameTextField, owner -> owner.getFirstName() + " " + owner.getLastName(), null);

        TextField addressTextField = new TextField();
        binder.bind(addressTextField, Owner::getAddress, null);

        TextField cityTextField = new TextField();
        binder.bind(cityTextField, Owner::getCity, null);

        TextField telephoneTextField = new TextField();
        binder.bind(telephoneTextField, Owner::getTelephone, null);

        FormLayout ownerForm = new FormLayout();
        FormUtil.singleColumn(ownerForm);
        ownerForm.addFormItem(nameTextField, getTranslation("name"));
        ownerForm.addFormItem(addressTextField, getTranslation("address"));
        ownerForm.addFormItem(cityTextField, getTranslation("city"));
        ownerForm.addFormItem(telephoneTextField, getTranslation("telephone"));

        Button editOwnerButton = new Button(getTranslation("editOwner"));
        editOwnerButton
                .addClickListener(
                        e -> UI.getCurrent().navigate(OwnerEditView.class, new RouteParameters(
                                OWNER_ID_ROUTE_PARAM, presenter.getOwner().getId().toString())));

        Button addNewPetButton = new Button(getTranslation("addNewPet"));
        addNewPetButton
                .addClickListener(
                        e -> UI.getCurrent().navigate(PetCreateView.class, new RouteParameters(
                                OWNER_ID_ROUTE_PARAM, presenter.getOwner().getId().toString())));

        HorizontalLayout buttonBar = new HorizontalLayout(editOwnerButton, addNewPetButton);

        PetsContainer petsComponent = new PetsContainer();
        petsComponent.getContent().addClassName("pet-container");
        petsComponent.getContent().setSizeFull();
        petsComponent.getContent().setPadding(false);
        binder.bind(petsComponent, Owner::getPets, null);

        add(title, ownerForm, buttonBar, petsComponent);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        final Integer ownerId =
                event.getRouteParameters().getInteger(OWNER_ID_ROUTE_PARAM).orElse(null);
        presenter.findOwner(ownerId);
    }

    public void show(Owner owner) {
        binder.readBean(owner);
    }

    class PetsContainer extends AbstractCompositeField<VerticalLayout, PetsContainer, List<Pet>> {

        public PetsContainer() {
            super(new ArrayList<>());
        }

        @Override
        protected void setPresentationValue(List<Pet> newPresentationValue) {
            getContent().removeAll();

            H2 petsAndVisits = new H2(getTranslation("petsAndVisits"));
            getContent().add(petsAndVisits);

            for (Pet pet : newPresentationValue) {
                addPet(pet);
            }
        }

        private void addPet(Pet pet) {
            Binder<Pet> petBinder = new Binder<>();

            TextField petNameTextField = new TextField();
            petBinder.bind(petNameTextField, Pet::getName, null);

            TextField petBirthDateTextField = new TextField();
            petBinder.bind(petBirthDateTextField,
                    p -> DateTimeFormatter.ofPattern("yyyy-MM-dd").format(p.getBirthDate()), null);

            TextField petTypeField = new TextField();
            petBinder.bind(petTypeField, p -> p.getType().getName(), null);

            FormLayout petsForm = new FormLayout();
            petsForm.setSizeUndefined();
            petsForm.addFormItem(petNameTextField, getTranslation("name"));
            petsForm.addFormItem(petBirthDateTextField, getTranslation("birthDate"));
            petsForm.addFormItem(petTypeField, getTranslation("type"));

            Grid<Visit> petVisitsGrid = new Grid<>();

            petVisitsGrid
                    .addColumn(new LocalDateRenderer<>(Visit::getDate,"yyyy-MM-dd"))
                    .setHeader(getTranslation("visitDate"));
            petVisitsGrid.addColumn(Visit::getDescription).setHeader(getTranslation("description"));
            petVisitsGrid.setItems(pet.getVisits());
            petVisitsGrid.setAllRowsVisible(true);
            petVisitsGrid.setSizeUndefined();

            Button editPetButton = new Button(getTranslation("editPet"));
            editPetButton.addClickListener(e -> {
                RouteParam ownerIdParam = new RouteParam(OWNER_ID_ROUTE_PARAM,
                        presenter.getOwner().getId().toString());
                RouteParam petIdParam = new RouteParam("petId", pet.getId().toString());
                UI.getCurrent().navigate(PetEditView.class,
                        new RouteParameters(ownerIdParam, petIdParam));
            });
            Button addVisitButton = new Button(getTranslation("addVisit"));
            addVisitButton.addClickListener(e -> {
                RouteParam ownerIdParam = new RouteParam(OWNER_ID_ROUTE_PARAM,
                        presenter.getOwner().getId().toString());
                RouteParam petIdParam = new RouteParam("petId", pet.getId().toString());
                UI.getCurrent().navigate(VisitCreateView.class,
                        new RouteParameters(ownerIdParam, petIdParam));
            });

            HorizontalLayout petButtons = new HorizontalLayout(editPetButton, addVisitButton);

            VerticalLayout visitsContainer = new VerticalLayout(petVisitsGrid, petButtons);
            visitsContainer.addClassName("visits");

            HorizontalLayout petsAndVisitsLayout = new HorizontalLayout(petsForm, visitsContainer);
            petsAndVisitsLayout.addClassName("pet-row");

            getContent().add(petsAndVisitsLayout);

            petBinder.readBean(pet);
        }

    }

}
