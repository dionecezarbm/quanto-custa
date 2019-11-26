package com.howmuch.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.howmuch.app.entities.Order;
import com.howmuch.app.entities.User;

public interface OrderRepository extends JpaRepository<Order, Long> {

	List<Order> findByClient(User client);
	
}