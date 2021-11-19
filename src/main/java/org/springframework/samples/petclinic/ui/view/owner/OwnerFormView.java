package org.springframework.samples.petclinic.ui.view.owner;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.RegexpValidator;
import com.vaadin.flow.router.RouteParameters;
import org.springframework.samples.petclinic.backend.owner.Owner;
import org.springframework.samples.petclinic.ui.util.FormUtil;

public abstract class OwnerFormView extends VerticalLayout {

    protected final Binder<Owner> binder = new Binder<>(Owner.class);

    OwnerFormView(OwnerFormPresenter<? extends OwnerFormView> presenter) {
        setWidthFull();

        TextField firstNameTextField = new TextField();
        binder.forField(firstNameTextField).asRequired().bind(Owner::getFirstName,
                Owner::setFirstName);

        TextField lastNameTextField = new TextField();
        binder.forField(lastNameTextField).asRequired().bind(Owner::getLastName,
                Owner::setLastName);

        TextField addressTextField = new TextField();
        binder.forField(addressTextField).asRequired().bind(Owner::getAddress, Owner::setAddress);

        TextField cityTextField = new TextField();
        binder.forField(cityTextField).asRequired().bind(Owner::getCity, Owner::setCity);

        TextField telephoneTextField = new TextField();
        binder.forField(telephoneTextField)
                .asRequired()
                .withValidator(new RegexpValidator(getTranslation("telephoneError"), "\\d{1,10}"))
                .bind(Owner::getTelephone, Owner::setTelephone);

        FormLayout ownerForm = new FormLayout();
        ownerForm.setWidthFull();
        FormUtil.singleColumn(ownerForm);
        ownerForm.addFormItem(firstNameTextField, getTranslation("firstName"));
        ownerForm.addFormItem(lastNameTextField, getTranslation("lastName"));
        ownerForm.addFormItem(addressTextField, getTranslation("address"));
        ownerForm.addFormItem(cityTextField, getTranslation("city"));
        ownerForm.addFormItem(telephoneTextField, getTranslation("telephone"));

        Button updateOwnerButton = new Button(actionButtonLabel());
        updateOwnerButton.addClickListener(this::actionButtonListener);

        add(new H2(getTranslation("owner")), ownerForm, updateOwnerButton);

        firstNameTextField.focus();
    }

    protected abstract String actionButtonLabel();

    protected abstract void actionButtonListener(ClickEvent<Button> event);

    public void init(Owner owner) {
        binder.readBean(owner);
    }

    public void navigateToOwnerDetails(Integer ownerId) {
        UI.getCurrent().navigate(OwnerDetailsView.class,
                new RouteParameters("ownerId", ownerId.toString()));
    }

}
