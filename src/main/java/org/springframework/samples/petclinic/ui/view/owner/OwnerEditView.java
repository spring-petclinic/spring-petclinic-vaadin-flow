package org.springframework.samples.petclinic.ui.view.owner;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import org.springframework.samples.petclinic.ui.view.MainContentLayout;

@Route(value = "owners/:ownerId([0-9]+)/edit", layout = MainContentLayout.class)
public class OwnerEditView extends OwnerFormView implements BeforeEnterObserver {

    private final OwnerEditPresenter presenter;

    OwnerEditView(OwnerEditPresenter presenter) {
        super(presenter);
        this.presenter = presenter;

        presenter.setView(this);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Integer ownerId = event.getRouteParameters().getInteger("ownerId").orElse(null);
        presenter.initModel(ownerId);
    }

    @Override
    protected String actionButtonLabel() {
        return getTranslation("updateOwner");
    }

    @Override
    protected void actionButtonListener(ClickEvent<Button> event) {
        try {
            binder.writeBean(presenter.getModel());
            presenter.save();
        } catch (ValidationException ex) {
        }
    }

}
