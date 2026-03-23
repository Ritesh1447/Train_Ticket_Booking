package com.nt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nt.entity.Schedule;
import com.nt.entity.Train;
import com.nt.repository.scheduleRepository;

@Service
public class ScheduleImpl implements ITrainSchedduleService {

	@Autowired
	private scheduleRepository schRepo;

	@Override
	@Transactional
	public Schedule createSchedule(Schedule sch) {
	
		return schRepo.save(sch);
	}

	@Override
	@Transactional
	public Schedule update(int id, Schedule sch) {

		Schedule sch2 = schRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Schedule not found"));


		// update fields
		sch2.setTravelDateTime(sch.getTravelDateTime());
		sch2.setAvailableSeats(sch.getAvailableSeats());
		sch2.setLockedSeats(sch.getLockedSeats());
		sch2.setLockExpiryTime(sch.getLockExpiryTime());
		sch2.setTrain(sch.getTrain());

		  
		// No need to call save() explicitly (managed entity)
		return sch2;
	}

	@Override
	public List<Schedule> getAllSchedules() {
		return schRepo.findAll();
	}
}