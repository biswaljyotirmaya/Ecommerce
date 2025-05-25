package com.ecom.repository;


import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.ecom.entity.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
