package com.nt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nt.entity.payment;

public interface PaymentRepository extends JpaRepository<payment, Integer> {

}
