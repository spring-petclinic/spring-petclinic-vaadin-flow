package org.springframework.samples.petclinic.ui.view.owner;

import java.util.Collection;
import org.springframework.samples.petclinic.backend.owner.OwnerRepository;
import org.springframework.samples.petclinic.backend.owner.PetRepository;
import org.springframework.samples.petclinic.backend.owner.PetType;

public abstract class PetFormPresenter<V extends PetFormView> {

    protected final OwnerRepository ownerRepository;

    protected final PetRepository petRepository;

    protected V view;

    protected PetFormDto model;

    PetFormPresenter(OwnerRepository ownerRepository,
            PetRepository petRepository) {
        this.ownerRepository = ownerRepository;
        this.petRepository = petRepository;
    }

    public abstract void initModel(Integer ownerId, Integer petId);

    public abstract void save();

    public PetFormDto getModel() {
        return model;
    }

    public void setView(V view) {
        this.view = view;
    }

    public Collection<PetType> findPetTypes() {
        return petRepository.findPetTypes();
    }

}
