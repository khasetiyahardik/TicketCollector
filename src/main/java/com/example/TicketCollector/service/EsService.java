package com.example.TicketCollector.service;

import com.example.TicketCollector.dto.EsBookingTypeInfo;
import com.example.TicketCollector.entity.TravellingModeEntity;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;


public interface EsService {

     void saveTestToElastic(EsBookingTypeInfo esBookingTypeInfo);

     ArrayList<EsBookingTypeInfo> searchByBookingTypeName(String name) throws JsonProcessingException;

     void saveTravellingModeToElastic(TravellingModeEntity travellingModeEntity);

     ArrayList<TravellingModeEntity> searchByTravellingModeName(String name) throws JsonProcessingException;


}
