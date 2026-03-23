package com.nt.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ticket {
 private Integer bookingId;
 private String userName;
 private String trainName;
 private String source;
 private String destination;
 private LocalDateTime travelDateTime;
 
 private int seatCount;
 private double pricePerSeat;
 private double totalAmount;
 
 private String paymentStatus;
 
 private LocalDateTime paymentTime;
}
