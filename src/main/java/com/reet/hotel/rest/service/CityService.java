package com.reet.hotel.rest.service;

import com.reet.hotel.rest.model.City;

import java.util.List;

public interface CityService {
    List<City> getAllCities();

    City getCityById(Long id);

    City createCity(City city);
}
