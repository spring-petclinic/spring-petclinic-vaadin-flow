/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.springframework.samples.petclinic.backend.owner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.core.style.ToStringCreator;
import org.springframework.samples.petclinic.backend.model.Person;

/**
 * Represents a pet owner in the PetClinic system.
 * Owners are responsible for their pets and are the primary clients of the veterinary clinic.
 * This entity is mapped to the database and contains personal and contact information.
 * <p>
 * Business context: Owners register their pets, provide contact details, and interact with veterinary staff for appointments and treatments.
 */
@Entity
@Table(name = "owners")
public class Owner extends Person {
    /**
     * Owner's address.
     */
    @Column(name = "address")
    @NotEmpty
    private String address;

    /**
     * Owner's city of residence.
     */
    @Column(name = "city")
    @NotEmpty
    private String city;

    /**
     * Owner's telephone number.
     */
    @Column(name = "telephone")
    @NotEmpty
    @Digits(fraction = 0, integer = 10)
    private String telephone;

    /**
     * List of pets owned by this owner.
     * The relationship is one-to-many; an owner can have multiple pets.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER)
    private Set<Pet> pets;

    /**
     * Returns the owner's address.
     * @return address of the owner
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * Sets the owner's address.
     * @param address owner's address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns the owner's city.
     * @return city of residence
     */
    public String getCity() {
        return this.city;
    }

    /**
     * Sets the owner's city.
     * @param city owner's city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Returns the owner's telephone number.
     * @return telephone number
     */
    public String getTelephone() {
        return this.telephone;
    }

    /**
     * Sets the owner's telephone number.
     * @param telephone owner's telephone number
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * Internal accessor for the set of pets.
     * Initializes the set if it is null.
     * @return set of pets
     */
    protected Set<Pet> getPetsInternal() {
        if (this.pets == null) {
            this.pets = new HashSet<>();
        }
        return this.pets;
    }

    /**
     * Internal mutator for the set of pets.
     * @param pets set of pets to assign
     */
    protected void setPetsInternal(Set<Pet> pets) {
        this.pets = pets;
    }

    /**
     * Returns a sorted, unmodifiable list of the owner's pets.
     * Pets are sorted by name for consistent display in the UI.
     * @return sorted list of pets
     */
    public List<Pet> getPets() {
        List<Pet> sortedPets = new ArrayList<>(getPetsInternal());
        PropertyComparator.sort(sortedPets, new MutableSortDefinition("name", true, true));
        return Collections.unmodifiableList(sortedPets);
    }

    /**
     * Adds a pet to the owner's list of pets.
     * Ensures bidirectional relationship is maintained.
     * @param pet the pet to add
     */
    public void addPet(Pet pet) {
        if (pet.isNew()) {
            getPetsInternal().add(pet);
        }
        pet.setOwner(this); // Maintain bidirectional association
    }

    /**
     * Finds a pet by name.
     * @param name the name of the pet to find
     * @return the pet with the given name, or null if not found
     */
    public Pet getPet(String name) {
        return getPet(name, false);
    }

    /**
     * Finds a pet by name, optionally ignoring new pets.
     * @param name the name of the pet to find
     * @param ignoreNew whether to ignore new pets
     * @return the pet with the given name, or null if not found
     */
    public Pet getPet(String name, boolean ignoreNew) {
        name = name.toLowerCase();
        for (Pet pet : getPetsInternal()) {
            if (!ignoreNew || !pet.isNew()) {
                String compName = pet.getName();
                compName = compName.toLowerCase();
                if (compName.equals(name)) {
                    return pet;
                }
            }
        }
        return null;
    }

    /**
     * Returns a string representation of the owner for debugging and logging.
     * @return string with owner details
     */
    @Override
    public String toString() {
        return new ToStringCreator(this)
                .append("id", this.getId()).append("new", this.isNew())
                .append("lastName", this.getLastName())
                .append("firstName", this.getFirstName()).append("address", this.address)
                .append("city", this.city)
                .append("telephone", this.telephone).toString();
    }
}
