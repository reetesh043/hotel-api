package com.reet.hotel.rest.repository;

import com.reet.hotel.rest.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
