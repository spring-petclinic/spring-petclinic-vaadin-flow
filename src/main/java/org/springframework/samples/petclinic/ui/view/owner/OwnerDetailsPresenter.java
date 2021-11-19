package org.springframework.samples.petclinic.ui.view.owner;

import com.vaadin.flow.spring.annotation.RouteScope;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.samples.petclinic.backend.owner.Owner;
import org.springframework.samples.petclinic.backend.owner.OwnerRepository;
import org.springframework.samples.petclinic.backend.owner.Pet;
import org.springframework.samples.petclinic.backend.visit.VisitRepository;

@SpringComponent
@RouteScope
public class OwnerDetailsPresenter {

    private OwnerDetailsView view;

    private final OwnerRepository ownerRepository;

    private final VisitRepository visitRepository;

    private Owner owner;

    OwnerDetailsPresenter(OwnerRepository ownerRepository,
            VisitRepository visitRepository) {
        this.ownerRepository = ownerRepository;
        this.visitRepository = visitRepository;
    }

    public void setView(OwnerDetailsView view) {
        this.view = view;
    }

    public void findOwner(Integer ownerId) {
        if (ownerId == null) {
            view.show(new Owner());
            return;
        }

        owner = ownerRepository.findById(ownerId);
        for (Pet pet : owner.getPets()) {
            visitRepository.findByPetId(pet.getId()).forEach(pet::addVisit);
        }

        view.show(owner);
    }

    public Owner getOwner() {
        return owner;
    }

}
