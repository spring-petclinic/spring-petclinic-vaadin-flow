package org.springframework.samples.petclinic.ui.view.owner;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import org.springframework.samples.petclinic.ui.view.MainContentLayout;

@Route(value = "owners/:ownerId?([0-9]+)/pets/new", layout = MainContentLayout.class)
public class PetCreateView extends PetFormView implements BeforeEnterObserver {

    private final PetCreatePresenter presenter;

    PetCreateView(PetCreatePresenter presenter) {
        super(presenter);

        this.presenter = presenter;

        presenter.setView(this);
    }

    @Override
    protected String formTitle() {
        return getTranslation("newPet");
    }

    @Override
    protected String actionButtonLabel() {
        return getTranslation("addPet");
    }

    @Override
    protected void actionButtonListener(ClickEvent<Button> event) {
        try {
            binder.writeBean(presenter.getModel());
            presenter.save();
        } catch (ValidationException ex) {
        }
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Integer ownerId = event.getRouteParameters().getInteger("ownerId").orElse(null);

        presenter.initModel(ownerId, null);
    }

}
