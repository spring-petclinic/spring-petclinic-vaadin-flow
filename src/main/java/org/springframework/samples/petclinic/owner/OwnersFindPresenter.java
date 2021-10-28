package org.springframework.samples.petclinic.owner;

import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.vaadin.flow.spring.annotation.RouteScope;
import com.vaadin.flow.spring.annotation.SpringComponent;

@SpringComponent
@RouteScope
public class OwnersFindPresenter {

    private final OwnerRepository ownerRepository;

    private OwnersFindView findOwnersView;

    private Page<Owner> latestSearchResult;

    OwnersFindPresenter(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public void setView(OwnersFindView view) {
        this.findOwnersView = view;
    }

    public Stream<Owner> find(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        if (latestSearchResult == null) {
            latestSearchResult = ownerRepository.findByLastName(findOwnersView.getLastName(), pageable);
        }
        return latestSearchResult.get();
    }

    public int getCount(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        latestSearchResult = ownerRepository.findByLastName(findOwnersView.getLastName(), pageable);
        if (latestSearchResult.getTotalElements() == 0) {
            findOwnersView.showMessage("notFound");
        }
        return (int) latestSearchResult.getTotalElements();
    }

}
