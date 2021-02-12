package com.reet.hotel.rest.service.impl;

import com.reet.hotel.rest.exception.BadRequestException;
import com.reet.hotel.rest.exception.ElementNotFoundException;
import com.reet.hotel.rest.model.Hotel;
import com.reet.hotel.rest.repository.HotelRepository;
import com.reet.hotel.rest.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
class DefaultHotelService implements HotelService {
    private final HotelRepository hotelRepository;

    @Autowired
    DefaultHotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }


    @Override
    public List<Hotel> getHotelsByCity(Long cityId) {
        return hotelRepository.findAll().stream()
                .filter((hotel) -> cityId.equals(hotel.getCity().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public Hotel createNewHotel(Hotel hotel) {
        if (hotel.getId() != null) {
            throw new BadRequestException("The ID must not be provided when creating a new Hotel");
        }

        return hotelRepository.save(hotel);
    }

    @Override
    public Hotel getHotelById(Long hotelId) {
        if (hotelId == null) {
            throw new BadRequestException("The hotel ID must not be null");
        }
        Optional<Hotel> hotel = hotelRepository.findById(hotelId);
        if (hotel.isPresent()) {
            return hotel.get();
        } else {
            throw new ElementNotFoundException("No hotel found with given hotel id");
        }
    }

    @Override
    public void deleteHotelById(Long hotelId) {
        if (hotelId == null) {
            throw new BadRequestException("The hotel ID must not be null");
        }
        Hotel hotel = getHotelById(hotelId);
        // Mark the record as delete and do not perform hard delete.
        hotel.setDeleted(true);
        hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> find(Long cityId, double distance) {
        if (cityId == null) {
            throw new BadRequestException("The city ID must not be null");
        }
        int count = 0;
        List<Hotel> hotels = getHotelsByCity(cityId);
        List<Hotel> nearestHotelList = new ArrayList<>();
        for (Hotel hotel : hotels) {
            double dist = closestDistance(hotel.getLatitude(), hotel.getLongitude(),
                    hotel.getCity().getCityCentreLatitude(), hotel.getCity().getCityCentreLongitude());
            if (Double.compare(dist, distance) < 0) {
                count++;
                nearestHotelList.add(hotel);
                if (count > 3) break;
            }
        }
        return nearestHotelList;
    }

    //Static helper method to find closest distance using haversine formula
    static double closestDistance(double lat1, double lon1,
                                  double lat2, double lon2) {
        // distance between latitudes and longitudes
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        // convert to radians
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // apply formulae
        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.pow(Math.sin(dLon / 2), 2) *
                        Math.cos(lat1) *
                        Math.cos(lat2);
        double rad = 6371;
        double c = 2 * Math.asin(Math.sqrt(a));
        return rad * c;
    }
}
