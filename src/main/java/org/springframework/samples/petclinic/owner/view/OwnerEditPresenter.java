package org.springframework.samples.petclinic.owner.view;

import org.springframework.samples.petclinic.owner.domain.OwnerRepository;

import com.vaadin.flow.spring.annotation.RouteScope;
import com.vaadin.flow.spring.annotation.SpringComponent;

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
