package com.reet.hotel.rest.service;

import com.reet.hotel.rest.dto.RatingReportDto;
import com.reet.hotel.rest.model.Hotel;

import java.util.List;

public interface RatingService {
    RatingReportDto getRatingAverage(Long cityId);

    RatingReportDto getRatingAverage(List<Hotel> hotels);
}
