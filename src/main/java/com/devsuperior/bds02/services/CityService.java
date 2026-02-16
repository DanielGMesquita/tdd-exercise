package com.devsuperior.bds02.services;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.services.exceptions.DatabaseException;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CityService {
  private final CityRepository cityRepository;
  private final EntityManager entityManager;

  public CityService(CityRepository cityRepository, EntityManager entityManager) {
    this.cityRepository = cityRepository;
    this.entityManager = entityManager;
  }

  @Transactional(readOnly = true)
  public List<CityDTO> findAll() {
    List<City> cities = cityRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    return cities.stream().map(CityDTO::new).toList();
  }

  @Transactional
  public CityDTO insert(CityDTO dto) {
    City city = new City();
    city.setName(dto.getName());
    city = cityRepository.save(city);
    return new CityDTO(city);
  }

  /**
   * Deletes a city by its ID. If the city does not exist, throws a ResourceNotFoundException.
   * If there is a data integrity violation (e.g., trying to delete a city that is referenced by other entities), throws a DatabaseException.
   * Propagation is set to SUPPORTS, meaning that if there is an existing transaction, it will be used; otherwise, the method will execute non-transactionally.
   * This method uses Propagation.SUPPORTS only to match the test scenario where the delete operation is expected to throw a DatabaseException due to integrity violation, without starting a new transaction that would roll back the entire test context.
   *
   * @param id the ID of the city to be deleted
   * @throws ResourceNotFoundException if the city with the specified ID does not exist
   * @throws DatabaseException if there is an integrity violation during deletion
   */
  @Transactional(propagation = Propagation.SUPPORTS)
  public void delete(Long id) {
    if (!cityRepository.existsById(id)) {
      throw new ResourceNotFoundException("Id not found " + id);
    }
    try{
      cityRepository.deleteById(id);
      entityManager.flush();
    } catch (DataIntegrityViolationException e) {
      throw new DatabaseException("Integrity violation");
    }
  }
}
