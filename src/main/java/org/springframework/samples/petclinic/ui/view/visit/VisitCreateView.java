package org.springframework.samples.petclinic.ui.view.visit;

import java.time.format.DateTimeFormatter;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep.LabelsPosition;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteParameters;
import org.springframework.samples.petclinic.backend.visit.Visit;
import org.springframework.samples.petclinic.ui.util.FormUtil;
import org.springframework.samples.petclinic.ui.view.MainContentLayout;
import org.springframework.samples.petclinic.ui.view.owner.OwnerDetailsView;

@Route(value = "owners/:ownerId?([0-9]+)/pets/:petId?([0-9]+)/visits/new",
                layout = MainContentLayout.class)
public class VisitCreateView extends VerticalLayout implements BeforeEnterObserver {

        private final VisitCreatePresenter presenter;

        private final Binder<VisitCreateDto> binder;

        private final Grid<Visit> previousVisitsGrid;

        VisitCreateView(VisitCreatePresenter presenter) {
                this.presenter = presenter;

                presenter.setView(this);

                setWidthFull();

                binder = new Binder<>();

                final TextField nameTextField = new TextField(getTranslation("name"));
                binder.forField(nameTextField).bind(VisitCreateDto::getPetName, null);

                final TextField birthDateTextField = new TextField(getTranslation("birthDate"));
                binder.forField(birthDateTextField).bind(
                                v -> DateTimeFormatter.ofPattern("yyy-MM-dd")
                                                .format(v.getPetBirthDate()),
                                null);

                final TextField typeTextField = new TextField(getTranslation("type"));
                binder.forField(typeTextField).bind(VisitCreateDto::getPetType, null);

                final TextField ownerTextField = new TextField(getTranslation("owner"));
                binder.forField(ownerTextField).bind(VisitCreateDto::getPetOwner, null);

                final FormLayout petForm =
                                new FormLayout(nameTextField, birthDateTextField, typeTextField,
                                                ownerTextField);
                petForm.setResponsiveSteps(new ResponsiveStep("0", 1, LabelsPosition.ASIDE),
                                new ResponsiveStep("40em", 4, LabelsPosition.TOP));

                final DatePicker visitDatePicker = new DatePicker();
                binder.forField(visitDatePicker).asRequired().bind(VisitCreateDto::getVisitDate,
                                VisitCreateDto::setVisitDate);

                final TextField descriptionTextField = new TextField();
                descriptionTextField.setWidthFull();
                binder.forField(descriptionTextField).asRequired().bind(
                                VisitCreateDto::getDescription,
                                VisitCreateDto::setDescription);

                final FormLayout visitForm = new FormLayout();
                visitForm.addFormItem(visitDatePicker, getTranslation("date"));
                visitForm.addFormItem(descriptionTextField, getTranslation("description"));
                visitForm.setWidthFull();
                FormUtil.singleColumn(visitForm);

                final Button addVisitButton = new Button(getTranslation("addVisit"));
                addVisitButton.addClickListener(e -> {
                        try {
                                binder.writeBean(presenter.getModel());
                                presenter.save();
                        } catch (final ValidationException ex) {
                        }
                });

                previousVisitsGrid = new Grid<>();
                previousVisitsGrid.addColumn(Visit::getDate).setHeader(getTranslation("date"));
                previousVisitsGrid.addColumn(Visit::getDescription)
                                .setHeader(getTranslation("description"));

                final VerticalLayout previousVisitsContainer =
                                new VerticalLayout(new H3(getTranslation("previousVisits")),
                                                previousVisitsGrid);

                add(new H2(getTranslation("newVisit")), new H3(getTranslation("pet")), petForm,
                                visitForm,
                                addVisitButton, previousVisitsContainer);

                nameTextField.focus();
        }

        @Override
        public void beforeEnter(BeforeEnterEvent event) {
                final Integer ownerId =
                                event.getRouteParameters().getInteger("ownerId").orElse(null);
                final Integer petId = event.getRouteParameters().getInteger("petId").orElse(null);
                presenter.initModel(ownerId, petId);
        }

        public void navigateToOwnerDetails(Integer ownerId) {
                UI.getCurrent().navigate(OwnerDetailsView.class,
                                new RouteParameters("ownerId", ownerId.toString()));
        }

        public void show(VisitCreateDto visit) {
                binder.readBean(visit);
                previousVisitsGrid.setItems(visit.getPreviousVisits());
        }

}
