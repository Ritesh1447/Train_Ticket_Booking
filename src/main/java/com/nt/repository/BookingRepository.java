package com.nt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nt.dto.BookingStatus;
import com.nt.entity.Booking;
import com.nt.entity.Schedule;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

	List<Booking>  findByStatus(BookingStatus status);
	
	@Query("SELECT b FROM Booking b WHERE b.status = :status AND b.schedule.id = :scheduleId ORDER BY b.bookingTime ASC")
	List<Booking> findWaitingList(@Param("status") BookingStatus status,
	                              @Param("scheduleId") int scheduleId);
	
	List<Booking> findByScheduleAndStatusOrderByBookingTimeAsc(Schedule schedule, BookingStatus status);
	 
}
