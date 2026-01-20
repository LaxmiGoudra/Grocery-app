package com.ls.grocery_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ls.grocery_app.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}
