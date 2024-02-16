package org.launchcode.taskcrusher.models.data;

import org.launchcode.taskcrusher.models.Chore;
import org.launchcode.taskcrusher.models.Kid;
import org.launchcode.taskcrusher.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChoreRepository extends CrudRepository<Chore, Integer> {

    List<Chore> findByKid(Kid kid);

    List<Chore> findByParent(User parent);

}
