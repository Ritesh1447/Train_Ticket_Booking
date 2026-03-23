package com.nt.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nt.dto.BookingStatus;
import com.nt.entity.Booking;
import com.nt.entity.Schedule;
import com.nt.entity.User;
import com.nt.repository.BookingRepository;
import com.nt.repository.UserRepository;
import com.nt.repository.scheduleRepository;

import jakarta.transaction.Transactional;

@Service
public class BookingImpl implements IBookingService {

	@Autowired
	private BookingRepository bookRepo;
	
	@Autowired
	private scheduleRepository schRepo;
	
    @Autowired
    private EmailService emailService;
	
	@Autowired
	private UserRepository userRepo;
	
	@Transactional
	@Override
	public Booking bookTicket(int uid, int schid, int seats) {

	    Schedule sch = schRepo.findById(schid)
	            .orElseThrow(() -> new RuntimeException("Schedule not found"));

	    User user = userRepo.findById(uid)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    Booking booking = new Booking();
	    booking.setUser(user);
	    booking.setSchedule(sch);
	    booking.setSeatCount(seats);
	    booking.setBookingTime(LocalDateTime.now());

	    //  Seats Available
	    if (sch.getAvailableSeats() >= seats) {

	        // Lock seats
	        sch.setAvailableSeats(sch.getAvailableSeats() - seats);
	        sch.setLockedSeats(sch.getLockedSeats() + seats);

	        // Set expiry
	        sch.setLockExpiryTime(LocalDateTime.now().plusMinutes(10));

	        booking.setStatus(BookingStatus.PENDING);

	        schRepo.save(sch);

	    } else {

	        //WAITING LIST
	        booking.setStatus(BookingStatus.WAITING);
	    }

	    return bookRepo.save(booking);
	}
	
	
	
	@Transactional // Highly recommended for data consistency
	public String cancelTicket(int id) {
	    Booking booking = bookRepo.findById(id)
	            .orElseThrow(() -> new RuntimeException("Booking not found"));

	    if (booking.getStatus() == BookingStatus.CONFIRMED) {
	        Schedule sch = booking.getSchedule();
	        int seats = booking.getSeatCount();
	        int cancelledSeats = booking.getSeatCount();

	        // 1. Process the Cancellation and Refund details
	        double totalAmount = sch.getTrain().getBaseprice() * cancelledSeats;
	        String email = booking.getUser().getEmail();

	        // 2. FIFO Logic: Look for the oldest Waiting users
	        // Assuming your repository has: findByScheduleAndStatusOrderByBookingTimeAsc
	        List<Booking> waitingList = bookRepo.findByScheduleAndStatusOrderByBookingTimeAsc(sch, BookingStatus.WAITING);

	        int seatsToRedistribute = cancelledSeats;

	        for (Booking waitingUser : waitingList) {
	            if (seatsToRedistribute <= 0) break;

	            if (waitingUser.getSeatCount() <= seatsToRedistribute) {
	                // Promote this user to CONFIRMED
	                waitingUser.setStatus(BookingStatus.CONFIRMED);
	                seatsToRedistribute -= waitingUser.getSeatCount();
	                bookRepo.save(waitingUser);
	                
	                // Optional: Send "Waitlist Cleared" email to this user
	                emailService.sendHtmlTicket(waitingUser.getUser().getEmail(), "Ticket Confirmed!", "Your waitlisted ticket is now confirmed!");
	            }
	        }

	        // 3. Update Schedule: Any leftover seats go back to Available
	        if (seatsToRedistribute > 0) {
	            sch.setAvailableSeats(sch.getAvailableSeats() + seatsToRedistribute);
	        }
	        
	        // Correcting locked seats logic
	        if (sch.getLockedSeats() >= cancelledSeats) {
	        	int currentLocked = sch.getLockedSeats();
	        	sch.setLockedSeats(Math.max(0, currentLocked - seats));
	        
	        }
	        schRepo.save(sch);

	        
	        String htmlCancelTicket = "<html>"
	                + "<body style='font-family:Arial;'>"
	                + "<h2 style='color:#d9534f;'>❌ Seat Cancellation Confirmed</h2>"
	                + "<hr>"
	                + "<p><b>Booking ID:</b> " + booking.getId() + "</p>"
	                + "<p><b>Train:</b> " + sch.getTrain().getTrainName() + "</p>"
	                + "<p><b>From:</b> " + sch.getTrain().getSource() + "</p>"
	                + "<p><b>To:</b> " + sch.getTrain().getDestination() + "</p>"
	                + "<p><b>Travel Date:</b> " + sch.getTravelDateTime() + "</p>"
	                + "<p><b>Cancelled Seats:</b> " + seats + "</p>"
	                + "<hr>"
	                + "<div style='background-color:#fff3cd; padding:15px; border-left:6px solid #ffc107;'>"
	                + "<h3>💰 Refund Information</h3>"
	                + "<p>Your payment of <b>₹" + totalAmount + "</b> has been processed for refund.</p>"
	                + "<p><b>Note:</b> Your money will be credited to your original payment method within <b>24 hours</b>.</p>"
	                + "</div>"
	                + "<hr>"
	                + "<p style='color:gray;'>We hope to see you travel with us again soon 🚆</p>"
	                + "</body>"
	                + "</html>";
	        
	        

            emailService.sendHtmlTicket(email, "Your Train Ticket", htmlCancelTicket);
          

	          
	        bookRepo.delete(booking);

	        return "Ticket Cancelled and Waiting List updated Successfully";
	    }
	    return "Only confirmed tickets can be cancelled.";
	}
		 
	
	        
	

	@Override
	@Transactional
	public void promoteWaitingList(Schedule sch) {

	  
	    List<Booking> waitingList =
	            bookRepo.findWaitingList(BookingStatus.WAITING, sch.getId());

	    for (Booking waiting : waitingList) {

	        int seats = waiting.getSeatCount();

	        if (sch.getAvailableSeats() >= seats) {

	            sch.setAvailableSeats(sch.getAvailableSeats() - seats);
	            sch.setLockedSeats(sch.getLockedSeats() + seats);

	            waiting.setBookingTime(LocalDateTime.now());

	            waiting.setStatus(BookingStatus.PENDING);

	            bookRepo.save(waiting);

	        } else {
	            break; // no more seats
	        }
	    }

	    schRepo.save(sch);
	}
	
}