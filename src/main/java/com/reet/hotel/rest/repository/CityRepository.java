package com.reet.hotel.rest.repository;

import com.reet.hotel.rest.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
}
