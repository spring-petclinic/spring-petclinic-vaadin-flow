package org.springframework.samples.petclinic.ui.view.visit;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.samples.petclinic.backend.visit.Visit;

public class VisitCreateDto {

    private final Integer ownerId;

    private final Integer petId;

    private String petName;

    private LocalDate petBirthDate;

    private String petType;

    private String petOwner;

    private LocalDate visitDate;

    private String description;

    private List<Visit> previousVisits = new ArrayList<>();

    public VisitCreateDto(Integer ownerId, Integer petId) {
        this.ownerId = ownerId;
        this.petId = petId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public Integer getPetId() {
        return petId;
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

    public String getPetType() {
        return petType;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }

    public String getPetOwner() {
        return petOwner;
    }

    public void setPetOwner(String petOwner) {
        this.petOwner = petOwner;
    }

    public LocalDate getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDate visitDate) {
        this.visitDate = visitDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Visit> getPreviousVisits() {
        return previousVisits;
    }

    public void setPreviousVisits(List<Visit> previousVisits) {
        this.previousVisits = previousVisits;
    }

}
