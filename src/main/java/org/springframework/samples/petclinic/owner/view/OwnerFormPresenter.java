package org.springframework.samples.petclinic.owner.view;

import org.springframework.samples.petclinic.owner.domain.Owner;
import org.springframework.samples.petclinic.owner.domain.OwnerRepository;

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
