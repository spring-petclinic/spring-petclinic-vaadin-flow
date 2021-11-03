package org.springframework.samples.petclinic.owner.view;

import org.springframework.samples.petclinic.base.view.MainContentLayout;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@Route(value = "owners/new", layout = MainContentLayout.class)
public class OwnerCreateView extends OwnerFormView implements BeforeEnterObserver {

    private final OwnerCreatePresenter presenter;

    OwnerCreateView(OwnerCreatePresenter presenter) {
        super(presenter);
        this.presenter = presenter;

        presenter.setView(this);
    }

    @Override
    protected String actionButtonLabel() {
        return getTranslation("addOwner");
    }

    @Override
    protected void actionButtonListener(ClickEvent<Button> event) {
        try {
            binder.writeBean(presenter.getModel());
            presenter.save();
        } catch (final ValidationException ex) {
        }
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        presenter.initModel(null);
    }

}
