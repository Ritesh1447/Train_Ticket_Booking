package com.nt.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
public class Schedule {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Integer id;
	
	private LocalDateTime travelDateTime;
	
	private int availableSeats;
	
	private int lockedSeats;
	
	private LocalDateTime lockExpiryTime;
	
	@Version
	@JsonIgnore
	private int version;
	
	@ManyToOne
	private Train train;
}
