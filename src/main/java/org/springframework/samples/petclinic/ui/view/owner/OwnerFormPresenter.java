package org.springframework.samples.petclinic.ui.view.owner;

import org.springframework.samples.petclinic.backend.owner.Owner;
import org.springframework.samples.petclinic.backend.owner.OwnerRepository;

public abstract class OwnerFormPresenter<V extends OwnerFormView> {

    protected final OwnerRepository ownerRepository;

    protected V view;

    protected Owner model;

    OwnerFormPresenter(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public abstract void initModel(Integer ownerId);

    public void setView(V view) {
        this.view = view;
    }

    public void save() {
        ownerRepository.save(model);
        view.navigateToOwnerDetails(model.getId());
    }

    public Owner getModel() {
        return model;
    }

}
