package com.nt.contorller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nt.entity.payment;
import com.nt.service.IpaymentService;

@RestController
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	private IpaymentService payService;
	
	@PostMapping("/status/{bkid}")
	public ResponseEntity<payment> makePayment(@PathVariable int bkid){
		return ResponseEntity.ok(payService.makePayment(bkid));
	}
}
