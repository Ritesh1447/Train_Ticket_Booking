package com.nt.service;

import com.nt.entity.Booking;
import com.nt.entity.Schedule;

public interface IBookingService {
 Booking bookTicket(int uid,int schid,int seats);
	public String cancelTicket(int id);
 public void promoteWaitingList(Schedule sch);
}
