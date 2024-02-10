package org.launchcode.taskcrusher.models.data;

import org.launchcode.taskcrusher.models.Kid;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface KidRepository extends CrudRepository<Kid,Integer> {

    List<Kid> findByParentId(Long id);

}
