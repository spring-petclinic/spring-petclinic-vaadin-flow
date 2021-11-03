package org.springframework.samples.petclinic.base.view;

import javax.servlet.http.HttpServletResponse;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.HasErrorParameter;
import com.vaadin.flow.router.Route;

@Route(value = "oups", layout = MainContentLayout.class)
public class ErrorView extends VerticalLayout implements HasErrorParameter<Exception> {

    private final Label errorLabel;

    public ErrorView() {
        Image image = new Image("./images/pets.png", getTranslation("pets"));

        H3 message = new H3(getTranslation("somethingHappened"));

        errorLabel = new Label();

        add(image, message, errorLabel);
    }

    @Override
    public int setErrorParameter(BeforeEnterEvent event, ErrorParameter<Exception> parameter) {
        errorLabel.setText(getTranslation("navigationError", event.getLocation().getPath()));
        return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    }

}
