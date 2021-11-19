package org.springframework.samples.petclinic.ui.view.owner;

import com.vaadin.flow.spring.annotation.RouteScope;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.samples.petclinic.backend.owner.Owner;
import org.springframework.samples.petclinic.backend.owner.OwnerRepository;

@SpringComponent
@RouteScope
public class OwnerCreatePresenter extends OwnerFormPresenter<OwnerCreateView> {

    OwnerCreatePresenter(OwnerRepository ownerRepository) {
        super(ownerRepository);
    }

    @Override
    public void initModel(Integer ownerId) {
        model = new Owner();
        view.init(model);
    }

}
