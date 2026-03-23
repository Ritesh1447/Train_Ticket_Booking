package com.nt.service;

import java.util.List;

import com.nt.entity.Train;

import jakarta.servlet.http.HttpServletRequest;

public interface ITrainService {
public Train addTrain(Train train,HttpServletRequest req);
public List<Train> getAllTrains();
public Train getTrainById(int id);
public String deleteTrain(int id,HttpServletRequest req);
}
