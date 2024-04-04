package com.passin.passin.services;

import java.text.Normalizer;
import java.util.List;

import org.springframework.stereotype.Service;

import com.passin.passin.domain.attendee.Attendee;
import com.passin.passin.domain.event.Event;
import com.passin.passin.dto.event.EventIdDTO;
import com.passin.passin.dto.event.EventRequestDTO;
import com.passin.passin.dto.event.EventResponseDTO;
import com.passin.passin.repositories.AttendeeRepository;
import com.passin.passin.repositories.EventRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventServices {
    private final EventRepository eventRepository;
    private final AttendeeRepository attendeeRepository;

    public EventResponseDTO getEventDetail(String eventId){
        Event event = this.eventRepository.findById(eventId).orElseThrow(()-> new RuntimeException("Event not found with ID: " + eventId));
        List<Attendee> attendeeList = this.attendeeRepository.findByEventId(eventId);
        return new EventResponseDTO(event, attendeeList.size());
    }

    public EventIdDTO createEvent(EventRequestDTO eventDTO){
        Event newEvent = new Event();
        newEvent.setTitle(eventDTO.title());
        newEvent.setDetails(eventDTO.details());
        newEvent.setMaximumAttendees(eventDTO.maximumAttendees());
        newEvent.setSlug(createSlug(eventDTO.title()));
        this.eventRepository.save(newEvent);
        return new EventIdDTO(newEvent.getId());
    }

    private String createSlug(String text){
        String normalized = Normalizer.normalize(text,Normalizer.Form.NFD);

        return normalized.replaceAll("[\\p{InCOMBINING_DIACRITICAL_MARKS}]","") //Remove os acentos das palavras (Ex: São Paulo! -> Sao Paulo!)
                         .replaceAll("[^\\w\\s]", "") // Remove tudo que não for letra e numero (Ex: Sao paulo! -> Sao Paulo)
                         .replaceAll("[\\s+]","-") //substitui os espaços por ífen "-" (Ex: Sao Paulo -> Sao-Paulo)
                         .toLowerCase(); // passa tudo para minusculo (Ex: Sao-Paulo -> sao-paulo)
    }
}
