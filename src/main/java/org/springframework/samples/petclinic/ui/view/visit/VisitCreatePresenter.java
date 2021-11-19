package org.springframework.samples.petclinic.ui.view.visit;

import java.util.Comparator;
import java.util.List;
import com.vaadin.flow.spring.annotation.RouteScope;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.samples.petclinic.backend.owner.Owner;
import org.springframework.samples.petclinic.backend.owner.OwnerRepository;
import org.springframework.samples.petclinic.backend.owner.Pet;
import org.springframework.samples.petclinic.backend.owner.PetRepository;
import org.springframework.samples.petclinic.backend.visit.Visit;
import org.springframework.samples.petclinic.backend.visit.VisitRepository;

@SpringComponent
@RouteScope
public class VisitCreatePresenter {

    private final OwnerRepository ownerRepository;

    private final PetRepository petRepository;

    private final VisitRepository visitRepository;

    private VisitCreateView view;

    private VisitCreateDto model;

    VisitCreatePresenter(OwnerRepository ownerRepository,
            PetRepository petRepository,
            VisitRepository visitRepository) {
        this.ownerRepository = ownerRepository;
        this.petRepository = petRepository;
        this.visitRepository = visitRepository;
    }

    public void setView(VisitCreateView view) {
        this.view = view;
    }

    public VisitCreateDto getModel() {
        return model;
    }

    public void save() {
        final Visit visit = new Visit();
        visit.setDate(model.getVisitDate());
        visit.setDescription(model.getDescription());
        visit.setPetId(model.getPetId());
        visitRepository.save(visit);

        view.navigateToOwnerDetails(model.getOwnerId());
    }

    public void initModel(Integer ownerId, Integer petId) {
        model = new VisitCreateDto(ownerId, petId);

        final Owner owner = ownerRepository.findById(ownerId);
        model.setPetOwner(owner.getFirstName() + " " + owner.getLastName());

        final Pet pet = petRepository.findById(petId);
        model.setPetName(pet.getName());
        model.setPetBirthDate(pet.getBirthDate());
        model.setPetType(pet.getType().getName());

        final List<Visit> previousVisits = visitRepository.findByPetId(petId);
        previousVisits.sort(Comparator.comparing(Visit::getDate).reversed());
        model.setPreviousVisits(previousVisits);

        view.show(model);
    }

}
