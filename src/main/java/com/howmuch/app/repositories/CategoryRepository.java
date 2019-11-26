package com.howmuch.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.howmuch.app.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}