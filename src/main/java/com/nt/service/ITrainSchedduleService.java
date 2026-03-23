package com.nt.service;

import java.util.List;

import com.nt.entity.Schedule;

public interface ITrainSchedduleService {

	Schedule createSchedule(Schedule sch);
	
	Schedule update(int id,Schedule sch);
	
	List<Schedule> getAllSchedules();
	
}
