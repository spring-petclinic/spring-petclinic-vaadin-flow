package org.springframework.samples.petclinic.ui.view.vet;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.samples.petclinic.backend.vet.Specialty;
import org.springframework.samples.petclinic.backend.vet.Vet;
import org.springframework.samples.petclinic.ui.view.MainContentLayout;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

/**
 * Vaadin view component for displaying veterinarians in the PetClinic application (UI layer).
 * Presents a list of veterinary staff and their specialties to clinic clients.
 * <p>
 * Business context: Enables clients to view available veterinarians and their areas of expertise for scheduling appointments and consultations.
 */
@Route(value = "vets", layout = MainContentLayout.class)
public class VetsView extends VerticalLayout {

    private final VetsPresenter presenter;

    /**
     * Grid component for displaying veterinarian data in a tabular format.
     */
    private final Grid<Vet> vetsGrid;

    /**
     * Initializes the veterinarians view.
     * Sets up the UI components for displaying veterinary staff.
     */
    VetsView(VetsPresenter presenter) {
        this.presenter = presenter;

        presenter.setView(this);

        setSizeFull();

        H2 title = new H2(getTranslation("veterinarians"));

        vetsGrid = new Grid<>();
        vetsGrid.addColumn(vet -> vet.getFirstName() + " " + vet.getLastName())
                .setHeader(getTranslation("name"));
        vetsGrid.addColumn(vet -> vet.getSpecialties().isEmpty()
                ? getTranslation("none")
                : vet.getSpecialties().stream().map(Specialty::getName)
                        .collect(Collectors.joining(", ")))
                .setHeader(getTranslation("specialties"));

        add(title, vetsGrid);
    }

    /**
     * Displays a list of veterinarians in the grid.
     *
     * @param vets the list of veterinarians to display
     */
    public void show(List<Vet> vets) {
        vetsGrid.setItems(vets);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        presenter.loadVets();
    }

}
