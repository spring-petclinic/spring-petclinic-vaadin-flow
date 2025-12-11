/**
 * Presenter for the veterinarians view in the PetClinic application.
 * Handles business logic related to displaying and managing veterinary staff.
 * Acts as a mediator between the data layer and the Vaadin UI.
 * <p>
 * Business context: Coordinates the retrieval and presentation of veterinarian data, ensuring only active staff are shown to clients.
 */
public class VetsPresenter {
    private final VetRepository vetRepository;
    private final VetsView vetsView;

    /**
     * Constructs a VetsPresenter with required dependencies.
     *
     * @param vetRepository repository for accessing veterinarian data
     * @param vetsView the view component to present veterinarian information
     */
    public VetsPresenter(VetRepository vetRepository, VetsView vetsView) {
        this.vetRepository = vetRepository;
        this.vetsView = vetsView;
    }

    /**
     * Loads all veterinarians and updates the view.
     * Applies business rules for displaying veterinary staff.
     * Only active veterinarians are shown to clients.
     */
    public void loadVets() {
        List<Vet> vets = vetRepository.findAll();
        // Business rule: Only display active veterinarians
        List<Vet> activeVets = vets.stream()
            .filter(Vet::isActive)
            .collect(Collectors.toList());
        vetsView.displayVets(activeVets);
    }
}
// ...existing code...
