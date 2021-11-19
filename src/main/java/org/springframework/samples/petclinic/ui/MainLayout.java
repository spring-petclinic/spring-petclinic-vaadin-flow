package org.springframework.samples.petclinic.ui;

import java.util.Optional;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouterLink;
import org.springframework.samples.petclinic.ui.view.ErrorView;
import org.springframework.samples.petclinic.ui.view.WelcomeView;
import org.springframework.samples.petclinic.ui.view.owner.OwnersFindView;
import org.springframework.samples.petclinic.ui.view.vet.VetsView;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

	private final Tabs menu;

	public MainLayout() {
		setPrimarySection(Section.DRAWER);
		addToNavbar(true, createHeaderContent());
		menu = createMenu();
		addToDrawer(createDrawerContent(menu));
	}

	private Component createHeaderContent() {
		final HorizontalLayout layout = new HorizontalLayout();
		layout.setPadding(true);
		layout.setId("header");
		layout.setWidthFull();
		layout.setSpacing(true);
		layout.setAlignItems(FlexComponent.Alignment.CENTER);

		layout.add(new DrawerToggle());

		final Span brand = new Span();
		final Anchor brandLink = new Anchor("/", brand);
		brandLink.addClassName("navbar-brand");
		layout.add(brandLink);

		return layout;
	}

	private Component createDrawerContent(Tabs menu) {
		final VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		layout.setPadding(false);
		layout.setSpacing(false);
		layout.getThemeList().set("spacing-s", true);
		layout.setAlignItems(FlexComponent.Alignment.STRETCH);

		layout.add(menu);

		return layout;
	}

	private Tabs createMenu() {
		final Tabs tabs = new Tabs();
		tabs.setOrientation(Tabs.Orientation.VERTICAL);
		tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
		tabs.setId("tabs");
		tabs.add(createMenuItems());
		return tabs;
	}

	private Component[] createMenuItems() {
		return new Tab[] {
				new Tab(createRouterLink("home", VaadinIcon.HOME, WelcomeView.class)),
				new Tab(createRouterLink("findOwners", VaadinIcon.SEARCH, OwnersFindView.class)),
				new Tab(createRouterLink("veterinarians", VaadinIcon.LIST, VetsView.class)),
				new Tab(createRouterLink("error", VaadinIcon.WARNING, ErrorView.class))
		};
	}

	private RouterLink createRouterLink(String translationKey, VaadinIcon viewIcon,
			Class<? extends Component> navigationTarget) {
		final RouterLink routerLink =
				new RouterLink(getTranslation(translationKey), navigationTarget);
		routerLink.addComponentAsFirst(viewIcon.create());
		return routerLink;
	}

	@Override
	protected void afterNavigation() {
		super.afterNavigation();
		updateChrome();
	}

	private void updateChrome() {
		getTabWithCurrentRoute().ifPresent(menu::setSelectedTab);
	}

	private Optional<Tab> getTabWithCurrentRoute() {
		final String currentRoute = RouteConfiguration.forSessionScope()
				.getUrl(getContent().getClass());
		return menu.getChildren().filter(tab -> hasLink(tab, currentRoute))
				.findFirst().map(Tab.class::cast);
	}

	private boolean hasLink(Component tab, String currentRoute) {
		return tab.getChildren().filter(RouterLink.class::isInstance)
				.map(RouterLink.class::cast).map(RouterLink::getHref)
				.anyMatch(currentRoute::equals);
	}

}
