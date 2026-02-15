package com.devsuperior.bds02.services;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.repositories.CityRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CityService {
  private final CityRepository cityRepository;

  public CityService(CityRepository cityRepository) {
    this.cityRepository = cityRepository;
  }

  @Transactional(readOnly = true)
  public List<CityDTO> findAll() {
    List<City> cities = cityRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    return cities.stream().map(CityDTO::new).toList();
  }
}
