package com.nt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nt.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

}
