package org.launchcode.taskcrusher.models.data;

import org.launchcode.taskcrusher.models.Kid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

public interface KidRepository extends CrudRepository<Kid,Integer> {

    List<Kid> findByParentId(Long id);

    Optional<Kid> findByUsername(String username);

    boolean existsByUsername(String username);

}
