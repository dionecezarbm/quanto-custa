package com.howmuch.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.howmuch.app.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}