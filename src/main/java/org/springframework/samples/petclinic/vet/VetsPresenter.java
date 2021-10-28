package org.springframework.samples.petclinic.vet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.vaadin.flow.spring.annotation.RouteScope;
import com.vaadin.flow.spring.annotation.SpringComponent;

@SpringComponent
@RouteScope
public class VetsPresenter {

    private final VetRepository vetRepository;

    private VetsView view;

    VetsPresenter(VetRepository vetRepository) {
        this.vetRepository = vetRepository;
    }

    public void setView(VetsView view) {
        this.view = view;
    }

    public void loadVets() {
        List<Vet> vets = new ArrayList<>(vetRepository.findAll());

        Collections.sort(vets, Comparator.comparing(Vet::getFirstName).thenComparing(Vet::getLastName));

        view.show(vets);
    }

}
