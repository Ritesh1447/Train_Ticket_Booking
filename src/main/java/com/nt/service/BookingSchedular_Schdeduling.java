package com.nt.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.nt.dto.BookingStatus;
import com.nt.entity.Booking;
import com.nt.entity.Schedule;
import com.nt.repository.BookingRepository;
import com.nt.repository.scheduleRepository;

import jakarta.transaction.Transactional;

@Component
public class BookingSchedular_Schdeduling {

	

	    @Autowired
	    private BookingRepository bookingRepository;

	    @Autowired
	    private scheduleRepository scheduleRepository;

	    @Autowired
	    private IBookingService bookingService;

	    @Scheduled(fixedRate = 60000) // runs every 1 minute
	    @Transactional
	    public void checkExpiredBookings() {

	        List<Booking> list =
	                bookingRepository.findByStatus(BookingStatus.PENDING);

	        for (Booking b : list) {

	            // ⏳ Check if 5 minutes passed
	            if (b.getBookingTime()
	                    .plusMinutes(5)
	                    .isBefore(LocalDateTime.now())) {

	                Schedule sch = b.getSchedule();
	                int seats = b.getSeatCount();

	                // ❌ cancel booking
	                b.setStatus(BookingStatus.CANCELLED);

	                // 🔓 release seats
	                sch.setAvailableSeats(sch.getAvailableSeats() + seats);
	                sch.setLockedSeats(sch.getLockedSeats() - seats);

	             // 3. Delete the record entirely from DB
	                bookingRepository.delete(b);

	                // 🔥 promote waiting list
	                bookingService.promoteWaitingList(sch);
	            }
	        }
	    }
	}

