package org.springframework.samples.petclinic.ui.view;

import java.util.Objects;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.router.ParentLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import org.springframework.samples.petclinic.ui.MainLayout;

@ParentLayout(MainLayout.class)
@Route
public class MainContentLayout extends Div implements RouterLayout {

    private final Div content = new Div();

    public MainContentLayout() {
        final Div footer = new Div(new Image("./images/spring-pivotal-logo.png", "logo"));
        footer.addClassName("footer");

        setSizeFull();
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
