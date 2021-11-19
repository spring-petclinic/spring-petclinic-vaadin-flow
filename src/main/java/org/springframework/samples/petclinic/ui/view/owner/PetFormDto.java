package org.springframework.samples.petclinic.ui.view.owner;

import java.time.LocalDate;
import org.springframework.samples.petclinic.backend.owner.PetType;

public class PetFormDto {

    private final Integer ownerId;

    private final String ownerName;

    private Integer petId;

    private String petName;

    private LocalDate petBirthDate;

    private PetType petType;

    public PetFormDto(Integer ownerId, String ownerName) {
        this.ownerId = ownerId;
        this.ownerName = ownerName;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public LocalDate getPetBirthDate() {
        return petBirthDate;
    }

    public void setPetBirthDate(LocalDate petBirthDate) {
        this.petBirthDate = petBirthDate;
    }

    public PetType getPetType() {
        return petType;
    }

    public void setPetType(PetType petType) {
        this.petType = petType;
    }

}
