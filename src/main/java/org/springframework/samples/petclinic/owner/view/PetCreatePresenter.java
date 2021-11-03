package org.springframework.samples.petclinic.owner.view;

import org.springframework.samples.petclinic.owner.domain.Owner;
import org.springframework.samples.petclinic.owner.domain.OwnerRepository;
import org.springframework.samples.petclinic.owner.domain.Pet;
import org.springframework.samples.petclinic.owner.domain.PetRepository;

import com.vaadin.flow.spring.annotation.RouteScope;
import com.vaadin.flow.spring.annotation.SpringComponent;

@RouteScope
@SpringComponent
public class PetCreatePresenter extends PetFormPresenter<PetCreateView> {

    PetCreatePresenter(OwnerRepository ownerRepository,
            PetRepository petRepository) {
        super(ownerRepository, petRepository);
    }

    @Override
    public void initModel(Integer ownerId, Integer petId) {
        Owner owner = ownerRepository.findById(ownerId);

        model = new PetFormDto(ownerId, owner.getFirstName() + " " + owner.getLastName());

        view.show(model);
    }

    @Override
    public void save() {
        Pet pet = new Pet();
        pet.setName(model.getPetName());
        pet.setBirthDate(model.getPetBirthDate());
        pet.setType(model.getPetType());

        Owner owner = ownerRepository.findById(model.getOwnerId());
        owner.addPet(pet);

        petRepository.save(pet);

        view.navigateToOwnerDetails(model.getOwnerId());
    }

}
