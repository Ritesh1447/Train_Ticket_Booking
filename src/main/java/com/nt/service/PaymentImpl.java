package com.nt.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nt.dto.BookingStatus;
import com.nt.dto.PaymentStatus;
import com.nt.entity.Booking;
import com.nt.entity.Schedule;
import com.nt.entity.payment;
import com.nt.repository.BookingRepository;
import com.nt.repository.PaymentRepository;

import jakarta.transaction.Transactional;

@Service
public class PaymentImpl implements IpaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private IBookingService bookingService;

    @Autowired
    private com.nt.repository.scheduleRepository scheduleRepository;
    
    @Autowired
    private EmailService emailService;

    @Transactional
    public payment makePayment(int bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        Schedule schedule = booking.getSchedule();

        //  Calculate total amount
        double basePrice = schedule.getTrain().getBaseprice();
        int seats= booking.getSeatCount();
        double totalAmount = basePrice * seats;

        //  Simulate payment success/failure
        boolean isSuccess = Math.random() > 0.3;

        payment payment = new payment();
        payment.setBooking(booking);
        payment.setAmount(totalAmount);
        payment.setPaymentTime(LocalDateTime.now());

        if (isSuccess) {

            payment.setStatus(PaymentStatus.SUCCESS);

            booking.setStatus(BookingStatus.CONFIRMED);

            schedule.setLockedSeats(schedule.getLockedSeats() - seats);

            String email = booking.getUser().getEmail();

            String htmlTicket = "<html>"
                    + "<body style='font-family:Arial;'>"
                    + "<h2 style='color:green;'>🎫 Train Ticket Confirmed</h2>"
                    + "<hr>"
                    + "<p><b>Booking ID:</b> " + booking.getId() + "</p>"
                    + "<p><b>Train:</b> " + schedule.getTrain().getTrainName() + "</p>"
                    + "<p><b>From:</b> " + schedule.getTrain().getSource() + "</p>"
                    + "<p><b>To:</b> " + schedule.getTrain().getDestination() + "</p>"
                    + "<p><b>Travel Date:</b> " + schedule.getTravelDateTime() + "</p>"
                    + "<p><b>Seats:</b> " + seats + "</p>"
                    + "<p><b>Total Price:</b> ₹" + totalAmount + "</p>"
                    + "<p><b>Status:</b> CONFIRMED</p>"
                    + "<hr>"
                    + "<p style='color:gray;'>Thank you for booking with us 🚆</p>"
                    + "</body>"
                    + "</html>";

            emailService.sendHtmlTicket(email, "Your Train Ticket", htmlTicket);
        } else {

            payment.setStatus(PaymentStatus.FAILED);

            // ❌ Cancel booking
            booking.setStatus(BookingStatus.CANCELLED);

            // 🔓 Release seats
            schedule.setAvailableSeats(schedule.getAvailableSeats() + seats);
            int currentLocked = schedule.getLockedSeats();
            schedule.setLockedSeats(Math.max(0, currentLocked - seats));
            
         
        }

        scheduleRepository.save(schedule);
        bookingRepository.save(booking);

        return paymentRepository.save(payment);
    }
    
    
}
