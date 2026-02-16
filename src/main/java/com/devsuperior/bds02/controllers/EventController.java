package com.devsuperior.bds02.controllers;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.services.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/events")
public class EventController {
  private final EventService service;

  public EventController(EventService service) {
    this.service = service;
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<EventDTO> update(@PathVariable Long id, @RequestBody EventDTO eventDTO) {
    eventDTO = service.update(id, eventDTO);
    return ResponseEntity.ok().body(eventDTO);
  }
}
