package org.springframework.samples.petclinic.owner;

import org.springframework.samples.petclinic.visit.VisitRepository;

import com.vaadin.flow.spring.annotation.RouteScope;
import com.vaadin.flow.spring.annotation.SpringComponent;

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
            pet.setVisitsInternal(visitRepository.findByPetId(pet.getId()));
        }

        view.show(owner);
    }

    public Owner getOwner() {
        return owner;
    }

}
