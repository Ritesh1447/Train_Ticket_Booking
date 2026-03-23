package com.nt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nt.entity.Train;
import com.nt.repository.TrainRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class TrainServiceImpl implements ITrainService {

	@Autowired
	private TrainRepository trainRepo;
	@Override
	public Train addTrain(Train train, HttpServletRequest req) {
		String role=(String) req.getAttribute("role");
		if(!"ADMIN".equals(role)) {
			throw new RuntimeException("Access Denied");
		}
		return trainRepo.save(train);
	}

	@Override
	public List<Train> getAllTrains() {
		
		return trainRepo.findAll();
	}

	@Override
	public Train getTrainById(int id) {
	return  trainRepo.findById(id).
			orElseThrow(()->new RuntimeException("Train not found"));
	
	}

	@Override
	public String deleteTrain(int id, HttpServletRequest req) {
		String role=(String) req.getAttribute("role");
		if(!"ADMIN".equals(role)) {
			throw new RuntimeException("Access Denied");
		}
		trainRepo.deleteById(id);
		return "Train deleted Successfully";
	}

}
