package org.launchcode.taskcrusher.models.data;

import org.launchcode.taskcrusher.models.Kid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface KidRepository extends JpaRepository<Kid,Integer> {

    Optional<Kid> findByKidUsername(String username);
}
