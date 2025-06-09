package com.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecom.entity.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

	public List<User> findByRole(String role);

	public User findUserByName(String name);

}
