package org.launchcode.taskcrusher.models.data;

import org.launchcode.taskcrusher.models.Reward;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RewardRepository extends JpaRepository<Reward,Integer> {
}