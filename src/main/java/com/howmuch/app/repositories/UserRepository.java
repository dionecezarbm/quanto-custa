package com.howmuch.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.howmuch.app.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

}