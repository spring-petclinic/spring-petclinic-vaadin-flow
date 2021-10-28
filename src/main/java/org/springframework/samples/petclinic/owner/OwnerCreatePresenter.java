package org.springframework.samples.petclinic.owner;

import com.vaadin.flow.spring.annotation.RouteScope;
import com.vaadin.flow.spring.annotation.SpringComponent;

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
