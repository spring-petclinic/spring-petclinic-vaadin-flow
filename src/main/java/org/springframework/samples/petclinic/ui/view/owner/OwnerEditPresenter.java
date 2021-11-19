package org.springframework.samples.petclinic.ui.view.owner;

import com.vaadin.flow.spring.annotation.RouteScope;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.samples.petclinic.backend.owner.OwnerRepository;

@RouteScope
@SpringComponent
public class OwnerEditPresenter extends OwnerFormPresenter<OwnerEditView> {

    OwnerEditPresenter(OwnerRepository ownerRepository) {
        super(ownerRepository);
    }

    @Override
    public void initModel(Integer ownerId) {
        model = ownerRepository.findById(ownerId);
        view.init(model);
    }

}
