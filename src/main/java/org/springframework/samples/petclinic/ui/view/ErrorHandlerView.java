package org.springframework.samples.petclinic.ui.view;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.LoggerFactory;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.html.Pre;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.Style.Overflow;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.HasErrorParameter;
import com.vaadin.flow.router.Route;

import jakarta.servlet.http.HttpServletResponse;

@Route(layout = MainContentLayout.class)
public class ErrorHandlerView extends VerticalLayout implements HasErrorParameter<Exception> {

	private final NativeLabel errorLabel;
	private final Pre text;

	public ErrorHandlerView() {
		Image image = new Image("./images/pets.png", getTranslation("pets"));
		H3 message = new H3(getTranslation("somethingHappened"));
		errorLabel = new NativeLabel();
		text = new Pre();
		
		add(image, message, errorLabel, text);

		text.setWidth("80em");
		text.setHeight("400px");
		text.getStyle().set("flex", "1 1 auto");
		text.getStyle().setOverflow(Overflow.SCROLL);
		
		setSizeFull();
		setJustifyContentMode(JustifyContentMode.CENTER);
		setDefaultHorizontalComponentAlignment(Alignment.CENTER);
	}

	@Override
	public int setErrorParameter(BeforeEnterEvent event, ErrorParameter<Exception> parameter) {
		LoggerFactory.getLogger(ErrorHandlerView.class).error(parameter.getCustomMessage(), parameter.getException());
		errorLabel.setText(getTranslation("navigationError", event.getLocation().getPath()));

		// Convert exception stack trace to string
		StringWriter sw = new StringWriter();
		parameter.getException().printStackTrace(new PrintWriter(sw));
		text.setText(sw.toString());

		return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
	}

}
