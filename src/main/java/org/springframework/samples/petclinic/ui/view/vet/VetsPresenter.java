package org.springframework.samples.petclinic.ui.view.vet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import com.vaadin.flow.spring.annotation.RouteScope;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.samples.petclinic.backend.vet.Vet;
import org.springframework.samples.petclinic.backend.vet.VetRepository;

/**
 * Presenter for the veterinarians view in the PetClinic application (Vaadin UI layer).
 * Handles business logic related to displaying and managing veterinary staff in the UI.
 * Acts as a mediator between the data layer and the Vaadin view component.
 * <p>
 * Business context: Coordinates the retrieval and presentation of veterinarian data, ensuring clients see a sorted list of available staff for appointments and consultations.
 */
@SpringComponent
@RouteScope
public class VetsPresenter {
    /**
     * Repository for accessing veterinarian data from the backend.
     */
    private final VetRepository vetRepository;

    /**
     * Reference to the associated view for displaying veterinarians.
     */
    private VetsView view;

    /**
     * Constructs a VetsPresenter with the required repository dependency.
     *
     * @param vetRepository repository for accessing veterinarian data
     */
    VetsPresenter(VetRepository vetRepository) {
        this.vetRepository = vetRepository;
    }

    /**
     * Sets the view associated with this presenter.
     *
     * @param view the VetsView instance to associate
     */
    public void setView(VetsView view) {
        this.view = view;
    }

    /**
     * Loads all veterinarians, sorts them by first and last name, and updates the view.
     * Ensures clients see an ordered list of veterinary staff for easier selection.
     */
    public void loadVets() {
        List<Vet> vets = new ArrayList<>(vetRepository.findAll());
        // Sort veterinarians by first name, then last name for consistent UI display
        Collections.sort(vets,
                Comparator.comparing(Vet::getFirstName).thenComparing(Vet::getLastName));
        view.show(vets);
    }
}
