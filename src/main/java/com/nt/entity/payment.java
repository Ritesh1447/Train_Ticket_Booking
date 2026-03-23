package com.nt.entity;

import java.time.LocalDateTime;

import com.nt.dto.PaymentStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter

@Setter
public class payment {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Integer id;
	
	private double amount;
	
	private PaymentStatus status;
	
	private LocalDateTime paymentTime;
	
	@OneToOne
	private Booking booking;
	
}
