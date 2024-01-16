package org.launchcode.taskcrusher.models.data;

import org.launchcode.taskcrusher.models.Chore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChoreRepository extends JpaRepository<Chore, Integer> {

}
