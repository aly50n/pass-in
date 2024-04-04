package com.passin.passin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.passin.passin.domain.checkin.CheckIn;

public interface CheckinRepository extends JpaRepository<CheckIn, Integer> {

}
    

