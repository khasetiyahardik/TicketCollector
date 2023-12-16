package com.example.TicketCollector.controller;

import com.example.TicketCollector.dto.ResponseDTO;
import com.example.TicketCollector.dto.TravellingModeDTO;
import com.example.TicketCollector.dto.TravellingSDDTO;
import com.example.TicketCollector.entity.TravellingModeEntity;
import com.example.TicketCollector.enums.StatusCodeEnum;
import com.example.TicketCollector.service.TravellingModeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tm")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class TravellingModeController {
    private final TravellingModeService travellingModeService;

    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_Admin')")
    public ResponseEntity<ResponseDTO> addTm(@Valid @RequestBody TravellingModeDTO travellingModeDTO, Errors errors) {
        log.info("TravellingModeController :: add() :: invoked ::");
        if (errors.hasErrors()) {
            return new ResponseEntity<>(new ResponseDTO(errors.getAllErrors().get(0).getDefaultMessage(), String.valueOf(HttpStatus.BAD_REQUEST), null), HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(travellingModeService.addTm(travellingModeDTO), HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_Admin')")
    public ResponseEntity<ResponseDTO> delete(@PathVariable Long id) {
        log.info("TravellingModeController :: add() :: invoked ::");
        return new ResponseEntity<>(travellingModeService.delete(id), HttpStatus.OK);
    }

    @PostMapping("/addSourceDestination/{tmId}")
    @PreAuthorize("hasRole('ROLE_Admin')")
    public ResponseEntity<ResponseDTO> addSD(@PathVariable Long tmId, @RequestBody TravellingSDDTO travellingSDDTO) {
        log.info("TravellingModeController :: add() :: invoked ::");
        return new ResponseEntity<>(travellingModeService.addSD(tmId, travellingSDDTO), HttpStatus.OK);
    }

    @PutMapping("/editSourceDestination/{tmId}")
    @PreAuthorize("hasRole('ROLE_Admin')")
    public ResponseEntity<ResponseDTO> editSD(@PathVariable Long tmId, @RequestBody TravellingSDDTO travellingSDDTO) {
        log.info("TravellingModeController :: editSD() :: invoked ::");
        return new ResponseEntity<>(travellingModeService.editSD(tmId, travellingSDDTO), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<TravellingModeEntity>> searchName(@RequestParam("name") String name) throws JsonProcessingException {
        log.info("TravellingModeController : searchName");
        return new ResponseEntity<>(travellingModeService.searchName(name), HttpStatus.OK);
    }
}
