package com.ecom.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecom.entity.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

	public List<User> findByRole(String role);

	public User findUserByName(String name);

	Optional<User> findByEmailAndPassword(String email, String password);

	Optional<User> findByNameAndPassword(String name, String password);

	// public User findByNameAndEmail(String name,String email);
}
