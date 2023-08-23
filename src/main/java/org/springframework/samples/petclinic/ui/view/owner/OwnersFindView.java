package org.springframework.samples.petclinic.ui.view.owner;

import java.util.stream.Collectors;

import org.springframework.samples.petclinic.backend.owner.Owner;
import org.springframework.samples.petclinic.backend.owner.Pet;
import org.springframework.samples.petclinic.ui.view.MainContentLayout;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridLazyDataView;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteParameters;
import com.vaadin.flow.router.RouterLink;

@Route(value = "owners/find", layout = MainContentLayout.class)
public class OwnersFindView extends VerticalLayout {

	private final Grid<Owner> ownersGrid;

	private final TextField lastNameTextField;

	OwnersFindView(OwnersFindPresenter presenter) {
		presenter.setView(this);

		setSizeFull();

		H2 title = new H2(getTranslation("findOwners"));

		lastNameTextField = new TextField(getTranslation("lastName"));
		FormLayout form = new FormLayout(lastNameTextField);

		Button findOwnerButton = new Button(getTranslation("findOwner"));
		findOwnerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

		Button addOwnerButton = new Button(getTranslation("addOwner"));
		addOwnerButton.addClickListener(
				e -> UI.getCurrent().navigate(OwnerCreateView.class));

		ownersGrid = new Grid<>();
		ownersGrid
				.addColumn(new ComponentRenderer<>(owner -> new RouterLink(
						owner.getFirstName() + " " + owner.getLastName(),
						OwnerDetailsView.class,
						new RouteParameters("ownerId",
								owner.getId().toString()))))
				.setHeader(getTranslation("name"));
		ownersGrid.addColumn(Owner::getAddress).setHeader(getTranslation("address"));
		ownersGrid.addColumn(Owner::getCity).setHeader(getTranslation("city"));
		ownersGrid.addColumn(Owner::getTelephone).setHeader(getTranslation(("telephone")));
		ownersGrid.addColumn(owner -> owner.getPets().stream().map(Pet::toString)
				.collect(Collectors.joining(", ")))
				.setHeader(getTranslation("pets"));
		
		updateGrid(presenter);
		
		lastNameTextField.setValueChangeMode(ValueChangeMode.EAGER);
		lastNameTextField.addValueChangeListener(e -> updateGrid(presenter));
		findOwnerButton.addClickListener(e -> updateGrid(presenter));

		HorizontalLayout formContainer =
				new HorizontalLayout(form, findOwnerButton, addOwnerButton);
		formContainer.setAlignItems(Alignment.END);
		formContainer.setPadding(false);

		add(title, formContainer, ownersGrid);
	}

	public void showMessage(String messageKey) {
		Notification.show(getTranslation(messageKey));
	}

	public String getLastName() {
		return lastNameTextField.getValue();
	}

	private GridLazyDataView<Owner> updateGrid(OwnersFindPresenter presenter) {
		return ownersGrid.setItems(
			query -> presenter.find(query.getPage(),
				query.getPageSize()),
			query -> presenter.getCount());
	}

}
