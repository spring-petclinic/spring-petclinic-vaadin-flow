package org.springframework.samples.petclinic.vet;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.samples.petclinic.system.MainContentLayout;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "vets", layout = MainContentLayout.class)
public class VetsView extends VerticalLayout {

    private final VetsPresenter presenter;

    private final Grid<Vet> vetsGrid;

    VetsView(VetsPresenter presenter) {
        this.presenter = presenter;

        presenter.setView(this);

        setSizeFull();

        H3 title = new H3(getTranslation("veterinarians"));

        vetsGrid = new Grid<>();
        vetsGrid.addColumn(vet -> vet.getFirstName() + " " + vet.getLastName()).setHeader(getTranslation("name"));
        vetsGrid.addColumn(vet -> vet.getSpecialties().isEmpty()
                ? getTranslation("none") : vet.getSpecialties().stream().map(Specialty::getName).collect(Collectors.joining(", ")))
                .setHeader(getTranslation("specialties"));

        add(title, vetsGrid);
    }

    public void show(List<Vet> vets) {
        vetsGrid.setItems(vets);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        presenter.loadVets();
    }

}
