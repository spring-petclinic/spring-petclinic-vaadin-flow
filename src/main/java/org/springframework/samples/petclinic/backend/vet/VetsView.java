/**
 * Vaadin view component for displaying veterinarians in the PetClinic application.
 * Presents a list of veterinary staff and their specialties to clinic clients.
 * <p>
 * Business context: Enables clients to view available veterinarians and their areas of expertise for scheduling appointments and consultations.
 */
@Route("vets")
public class VetsView extends VerticalLayout {
    private Grid<Vet> vetGrid;

    /**
     * Initializes the veterinarians view.
     * Sets up the UI components for displaying veterinary staff.
     */
    public VetsView() {
        vetGrid = new Grid<>(Vet.class);
        vetGrid.setColumns("firstName", "lastName", "specialties");
        add(vetGrid);
    }

    /**
     * Displays a list of veterinarians in the grid.
     *
     * @param vets the list of veterinarians to display
     */
    public void displayVets(List<Vet> vets) {
        vetGrid.setItems(vets);
    }
}
// ...existing code...
