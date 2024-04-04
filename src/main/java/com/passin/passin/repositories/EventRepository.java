package com.passin.passin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.passin.passin.domain.event.Event;

public interface EventRepository extends JpaRepository<Event, String>{
    
}
