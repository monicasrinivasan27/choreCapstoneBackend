package org.launchcode.taskcrusher.repositories;

import org.launchcode.taskcrusher.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLogin(String username);
}
