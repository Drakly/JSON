package org._18_json_exercise.data.repository;

import org._18_json_exercise.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    Set<User> findAllBySoldBuyerIsNotNull();
}
