package com.reet.hotel.rest.service;

import com.reet.hotel.rest.model.Hotel;

import java.util.List;

public interface HotelService {
    List<Hotel> getAllHotels();

    Hotel getHotelById(Long hotelId);

    List<Hotel> getHotelsByCity(Long cityId);

    Hotel createNewHotel(Hotel hotel);

    void deleteHotelById(Long hotelId);

    List<Hotel> find(Long cityId, double distance);

}
