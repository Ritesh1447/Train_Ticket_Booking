package com.nt.contorller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.nt.entity.Schedule;
import com.nt.service.ITrainSchedduleService;

@RestController
@RequestMapping("/schedule")
public class TrainSCheduleController {

	@Autowired
	private ITrainSchedduleService tsch;

	@PostMapping("/create")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Schedule> createSchedule(@RequestBody Schedule sch) {
		return ResponseEntity.ok(tsch.createSchedule(sch));
	}

	@PutMapping("/update/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Schedule> updateSchedule(@PathVariable int id, @RequestBody Schedule sch) {
		return ResponseEntity.ok(tsch.update(id, sch));
	}

	@GetMapping("/all")
	public ResponseEntity<List<Schedule>> getAllSchedule() {
		return ResponseEntity.ok(tsch.getAllSchedules());
	}
}