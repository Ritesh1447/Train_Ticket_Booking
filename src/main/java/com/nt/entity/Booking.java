package com.nt.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.nt.dto.BookingStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity
@Getter
@Setter
public class Booking {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Integer id;
	
	private int seatCount;
	
	private BookingStatus status;
	
	private LocalDateTime bookingTime;
	
	@ManyToOne
	private User user;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="schedule_id")
	private Schedule schedule;

	@OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<payment> payments;
}
