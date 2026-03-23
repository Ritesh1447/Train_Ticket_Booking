package com.nt.contorller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nt.entity.Booking;
import com.nt.service.IBookingService;

@RestController
@RequestMapping("/book")
public class BookingContoller {
	
	@Autowired
private IBookingService bservice;
	
	@PostMapping("/ticket/{uid}/{sid}/{seats}")
	public ResponseEntity<Booking> bookTicket(@PathVariable int uid,@PathVariable int sid,@PathVariable int seats)
			{
		return ResponseEntity.ok(bservice.bookTicket(uid, sid, seats));
			}
	
	@DeleteMapping("/cancel/{bkid}")
    public ResponseEntity<String> cancelBooking(@PathVariable int bkid) {
      
            return ResponseEntity.ok(bservice.cancelTicket(bkid));
     
	}
}
