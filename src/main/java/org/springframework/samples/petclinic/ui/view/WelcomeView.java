package org.springframework.samples.petclinic.ui.view;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainContentLayout.class)
public class WelcomeView extends VerticalLayout implements HasDynamicTitle {

	public WelcomeView() {
		setSizeFull();

		H2 h2 = new H2(getTranslation("welcome"));
		Image image = new Image("./images/pets.png", getTranslation("pets"));

		add(h2, image);
	}

	@Override
	public String getPageTitle() {
		return getTranslation("welcomeTitle");
	}

}
