package com.devsuperior.bds02.services;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.repositories.EventRepository;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class EventService {
  private final EventRepository eventRepository;
  private final CityRepository cityRepository;

  public EventService(EventRepository eventRepository, CityRepository cityRepository) {
    this.eventRepository = eventRepository;
    this.cityRepository = cityRepository;
  }

  @Transactional
  public EventDTO update(Long id, EventDTO eventDTO) {
    try {
      Event event = eventRepository.getReferenceById(id);
      Optional<City> city = Optional.of(cityRepository.getReferenceById(eventDTO.getCityId()));
      event.setName(eventDTO.getName());
      event.setDate(eventDTO.getDate());
      event.setUrl(eventDTO.getUrl());
      event.setCity(city.get());
      event = eventRepository.save(event);
      return new EventDTO(event);
    } catch (EntityNotFoundException e) {
      throw new ResourceNotFoundException("Id not found " + id);
    }
  }
}
