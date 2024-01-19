package org.launchcode.taskcrusher.models.data;

import org.launchcode.taskcrusher.models.Chore;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChoreRepository extends CrudRepository<Chore, Integer> {

}
