package com.example.TicketCollector.service;

import com.example.TicketCollector.dto.ResponseDTO;
import com.example.TicketCollector.dto.TravellingModeDTO;
import com.example.TicketCollector.dto.TravellingSDDTO;
import com.example.TicketCollector.entity.TravellingModeEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface TravellingModeService {
    ResponseDTO addTm(TravellingModeDTO travellingModeDTO);

    ResponseDTO delete(Long id);

    ResponseDTO addSD(Long id,TravellingSDDTO travellingSDDTO);

    ResponseDTO editSD(Long tmId, TravellingSDDTO travellingSDDTO);
    ResponseDTO importData(MultipartFile reapExcelDataFile) throws IOException, ParseException;
    List<TravellingModeEntity> searchName(String name) throws JsonProcessingException;
}
