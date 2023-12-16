package com.example.TicketCollector.serviceImpl;

import com.example.TicketCollector.constants.Constants;
import com.example.TicketCollector.dto.EsBookingTypeInfo;
import com.example.TicketCollector.dto.elastic.EsQueryBuilderDTO;
import com.example.TicketCollector.dto.elastic.HitsDTO;
import com.example.TicketCollector.entity.TravellingModeEntity;
import com.example.TicketCollector.service.EsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Map;

@Service
public class EsServiceImpl implements EsService {

    public static final Logger logger = LoggerFactory.getLogger(EsServiceImpl.class);
    @Autowired
    RestTemplate restTemplate;

    @Override
    public void saveTestToElastic(EsBookingTypeInfo esBookingTypeInfo) {
        logger.info("EsServiceImpl : saveTestToElastic");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", String.valueOf(MediaType.APPLICATION_JSON));
        HttpEntity<EsBookingTypeInfo> httpEntity = new HttpEntity<>(esBookingTypeInfo, headers);
        restTemplate.exchange(Constants.SAVE_TO_BOOKING_TYPE_ES, HttpMethod.POST, httpEntity, Map.class);
    }

    @Override
    public ArrayList<EsBookingTypeInfo> searchByBookingTypeName(String name) throws JsonProcessingException {
        logger.info("EsServiceImpl : searchByBookingTypeName");
        ObjectMapper objectMapper = new ObjectMapper();
        EsQueryBuilderDTO esQueryBuilder = new EsQueryBuilderDTO();
        String query = esQueryBuilder.searchByBookingTypeName(name);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", String.valueOf(MediaType.APPLICATION_JSON));
        HttpEntity<String> httpEntity = new HttpEntity<>(query, headers);
        Map data = (Map) restTemplate.exchange(Constants.BOOKING_TYPE_SEARCH, HttpMethod.POST, httpEntity, Map.class).getBody().get("hits");
        String dataTestString = objectMapper.writeValueAsString(data);
        HitsDTO hitsDTO = objectMapper.readValue(dataTestString, HitsDTO.class);
        ArrayList<EsBookingTypeInfo> bookingTypeInfos = new ArrayList<>();
        hitsDTO.getHits().stream().forEach(p -> bookingTypeInfos.add(p.get_source()));
        return bookingTypeInfos;
    }

    @Override
    public void saveTravellingModeToElastic(TravellingModeEntity travellingModeEntity) {
        logger.info("EsServiceImpl : saveTravellingModeToElastic");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", String.valueOf(MediaType.APPLICATION_JSON));
        HttpEntity<TravellingModeEntity> httpEntity = new HttpEntity<>(travellingModeEntity, headers);
        restTemplate.exchange(Constants.SAVE_TO_TRAVELLING_MODE_ES, HttpMethod.POST, httpEntity, Map.class);
    }

    @Override
    public ArrayList<TravellingModeEntity> searchByTravellingModeName(String name) throws JsonProcessingException {
        logger.info("EsServiceImpl : searchByTravellingModeName");
        ArrayList<TravellingModeEntity> travellingModeEntities = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        EsQueryBuilderDTO esQueryBuilder = new EsQueryBuilderDTO();
        String query = esQueryBuilder.searchByTravellModeName(name);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", String.valueOf(MediaType.APPLICATION_JSON));
        HttpEntity<String> httpEntity = new HttpEntity<>(query, headers);
        Map data = (Map) restTemplate.exchange(Constants.TRAVELLING_MODE_INDEX_SEARCH, HttpMethod.POST, httpEntity, Map.class).getBody().get("hits");
        ArrayList<Map> hitsArray = (ArrayList<Map>) data.get("hits");
        for (Map m:hitsArray){
            String dataString = objectMapper.writeValueAsString(m.get("_source"));
            TravellingModeEntity travellingModeEntity = objectMapper.readValue(dataString, TravellingModeEntity.class);
            travellingModeEntities.add(travellingModeEntity);
        }
        return travellingModeEntities;
    }
}
