package org.springframework.samples.petclinic.ui.view.owner;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.RouteParameters;
import org.springframework.samples.petclinic.backend.owner.PetType;
import org.springframework.samples.petclinic.ui.util.FormUtil;

public abstract class PetFormView extends VerticalLayout {

    protected final Binder<PetFormDto> binder = new Binder<>();

    PetFormView(PetFormPresenter<? extends PetFormView> presenter) {
        setWidthFull();

        TextField ownerTextField = new TextField();
        binder.forField(ownerTextField).bind(PetFormDto::getOwnerName, null);

        TextField nameTextField = new TextField();
        binder.forField(nameTextField).asRequired().bind(PetFormDto::getPetName,
                PetFormDto::setPetName);

        DatePicker birthDatePicker = new DatePicker();
        binder.forField(birthDatePicker).asRequired().bind(PetFormDto::getPetBirthDate,
                PetFormDto::setPetBirthDate);

        ComboBox<PetType> typeComboBox = new ComboBox<>();
        typeComboBox.setItems(presenter.findPetTypes());
        binder.forField(typeComboBox).asRequired().bind(PetFormDto::getPetType,
                PetFormDto::setPetType);

        FormLayout petForm = new FormLayout();
        petForm.setWidthFull();
        FormUtil.singleColumn(petForm);
        petForm.addFormItem(ownerTextField, getTranslation("owner"));
        petForm.addFormItem(nameTextField, getTranslation("name"));
        petForm.addFormItem(birthDatePicker, getTranslation("birthDate"));
        petForm.addFormItem(typeComboBox, getTranslation("type"));

        Button actionButton = new Button(actionButtonLabel(), this::actionButtonListener);

        H2 formTitle = new H2(formTitle());

        add(formTitle, petForm, actionButton);

        nameTextField.focus();
    }

    protected abstract String formTitle();

    protected abstract String actionButtonLabel();

    protected abstract void actionButtonListener(ClickEvent<Button> event);

    public void navigateToOwnerDetails(Integer ownerId) {
        UI.getCurrent().navigate(OwnerDetailsView.class,
                new RouteParameters("ownerId", ownerId.toString()));
    }

    public void show(PetFormDto pet) {
        binder.readBean(pet);
    }

}
