package com.devsuperior.bds02.controllers;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.services.CityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/cities")
public class CityController {
  private final CityService service;

  public CityController(CityService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<List<CityDTO>> findAll() {
    List<CityDTO> dtos = service.findAll();
    return ResponseEntity.ok().body(dtos);
  }
}
