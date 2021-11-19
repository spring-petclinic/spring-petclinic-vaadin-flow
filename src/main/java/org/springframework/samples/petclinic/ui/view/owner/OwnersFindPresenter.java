package org.springframework.samples.petclinic.ui.view.owner;

import java.util.stream.Stream;
import com.vaadin.flow.spring.annotation.RouteScope;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.petclinic.backend.owner.Owner;
import org.springframework.samples.petclinic.backend.owner.OwnerRepository;

@SpringComponent
@RouteScope
public class OwnersFindPresenter {

    private final OwnerRepository ownerRepository;

    private OwnersFindView findOwnersView;

    OwnersFindPresenter(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public void setView(OwnersFindView view) {
        this.findOwnersView = view;
    }

    public Stream<Owner> find(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return ownerRepository.findByLastName(findOwnersView.getLastName(), pageable).stream();
    }

    public int getCount() {
        return ownerRepository.countByLastName(findOwnersView.getLastName());
    }

}
