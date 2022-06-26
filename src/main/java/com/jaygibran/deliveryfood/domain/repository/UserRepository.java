package com.jaygibran.deliveryfood.domain.repository;

import com.jaygibran.deliveryfood.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
