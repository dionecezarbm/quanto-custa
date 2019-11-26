package com.howmuch.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.howmuch.app.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}