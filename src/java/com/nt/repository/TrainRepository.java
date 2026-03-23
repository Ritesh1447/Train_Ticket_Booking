package com.nt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nt.entity.Train;

public interface TrainRepository extends JpaRepository<Train, Integer> {

}
