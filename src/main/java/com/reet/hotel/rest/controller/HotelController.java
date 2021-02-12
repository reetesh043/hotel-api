package com.reet.hotel.rest.controller;

import com.reet.hotel.rest.model.Hotel;
import com.reet.hotel.rest.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotel")
public class HotelController {
    private final HotelService hotelService;

    @Autowired
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Hotel> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Hotel createHotel(@RequestBody Hotel hotel) {
        return hotelService.createNewHotel(hotel);
    }

    @GetMapping("/{hotelId}")
    @ResponseStatus(HttpStatus.OK)
    public Hotel getHotelById(@PathVariable Long hotelId) {
        return hotelService.getHotelById(hotelId);
    }

    @PostMapping("/{hotelId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteByHotelId(@PathVariable Long hotelId) {
        hotelService.deleteHotelById(hotelId);
    }

    @GetMapping("/{cityId}/")
    @ResponseStatus(HttpStatus.OK)
    public List<Hotel> findClosestHotel(@PathVariable Long cityId, @RequestParam("distance") double distance) {
        return hotelService.find(cityId, distance);
    }
}
