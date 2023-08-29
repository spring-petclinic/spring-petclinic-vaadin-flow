package org.springframework.samples.petclinic.ui.view;

import java.util.Objects;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.ParentLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import org.springframework.samples.petclinic.ui.MainLayout;

@ParentLayout(MainLayout.class)
@Route
public class MainContentLayout extends VerticalLayout implements RouterLayout {

	private final Div content = new Div();

	public MainContentLayout() {
		final HorizontalLayout footer = new HorizontalLayout(
				new Image("./images/vaadin.png", "Vaadin"),
				new Image("./images/spring-logo.svg", "Spring"));
		footer.addClassName("footer");

		setSizeFull();
		setJustifyContentMode(JustifyContentMode.BETWEEN);
		content.setSizeFull();

		add(content, footer);
	}

	@Override
	public void showRouterLayoutContent(HasElement hasElement) {
		Objects.requireNonNull(hasElement);
		Objects.requireNonNull(hasElement.getElement());
		content.removeAll();
		content.getElement().appendChild(hasElement.getElement());
	}

}
