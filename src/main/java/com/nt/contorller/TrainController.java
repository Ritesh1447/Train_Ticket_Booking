package com.nt.contorller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nt.entity.Train;
import com.nt.service.ITrainService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/train")
public class TrainController {

	@Autowired
	private ITrainService tservice;
	
	@PostMapping("/add")
	@PreAuthorize("hasRole('ADMIN')")
	public Train addTrain(@RequestBody Train train,HttpServletRequest req)
	{
		return tservice.addTrain(train, req);
		}
	
	@GetMapping("/get")
	public List<Train> getAllTrains(){
		return tservice.getAllTrains();
	}
	
	@GetMapping("/get/{id}")
	public Train getByid(@PathVariable int id)
	{
		return tservice.getTrainById(id);
	}
	
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public String deleteTrain(@PathVariable int id,HttpServletRequest req) {
		return tservice.deleteTrain(id, req);
	}
	
}
