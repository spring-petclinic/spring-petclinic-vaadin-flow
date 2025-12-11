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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Repository interface for Owner entities.
 * Provides data access operations for pet owners in the PetClinic system.
 * Extends Spring Data JPA's CrudRepository for standard CRUD functionality.
 * <p>
 * Business context: Enables searching, retrieving, and managing owner records for veterinary appointments and pet management.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 */
public interface OwnerRepository extends Repository<Owner, Integer> {

    /**
     * Retrieve {@link Owner}s from the data store by last name, returning all owners whose last
     * name <i>starts</i> with the given name.
     *
     * @param lastName Value to search for
     * @return a Collection of matching {@link Owner}s (or an empty Collection if none found)
     */

    @Query("SELECT DISTINCT owner FROM Owner owner left join  owner.pets WHERE owner.lastName LIKE :lastName% ")
    @Transactional(readOnly = true)
    Page<Owner> findByLastName(@Param("lastName") String lastName, Pageable pageable);

    /**
     * Retrieve {@link Owner}s from the data store by last name, returning total count of owners
     * whose last name <i>starts</i> with the given name.
     *
     * @param lastName Value to search for
     * @return count of matching {@link Owner}s
     */

    @Query("SELECT COUNT(owner) FROM Owner owner WHERE owner.lastName LIKE :lastName% ")
    @Transactional(readOnly = true)
    int countByLastName(@Param("lastName") String lastName);

    /**
     * Retrieve an {@link Owner} from the data store by id.
     *
     * @param id the id to search for
     * @return the {@link Owner} if found
     */
    @Query("SELECT owner FROM Owner owner left join fetch owner.pets WHERE owner.id =:id")
    @Transactional(readOnly = true)
    Owner findById(@Param("id") Integer id);

    /**
     * Save an {@link Owner} to the data store, either inserting or updating it.
     *
     * @param owner the {@link Owner} to save
     */
    void save(Owner owner);

    /**
     * Returnes all the owners from data store
     **/
    @Query("SELECT owner FROM Owner owner")
    @Transactional(readOnly = true)
    Page<Owner> findAll(Pageable pageable);

    /**
     * Finds owners by last name.
     * Used for searching clients in the veterinary clinic.
     *
     * @param lastName the last name to search for
     * @return a list of owners matching the last name
     */
    List<Owner> findByLastName(String lastName);

    /**
     * Retrieves all owners in the system.
     *
     * @return a list of all owners
     */
    List<Owner> findAll();
}
