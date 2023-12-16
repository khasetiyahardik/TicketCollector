package com.example.TicketCollector.controller;


import com.example.TicketCollector.dto.AddAdminDTO;
import com.example.TicketCollector.dto.ResponseDTO;
import com.example.TicketCollector.enums.StatusCodeEnum;
import com.example.TicketCollector.service.AdminService;

import com.example.TicketCollector.service.TravellingModeService;
import com.example.TicketCollector.serviceImpl.AdminServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.text.ParseException;
import javax.validation.Valid;


@RestController
@RequestMapping("/admin")
public class AdminController {

    public static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    AdminService adminService;
    @Autowired
    TravellingModeService travellingModeService;
    @PostMapping("/addAdmin")
    public ResponseDTO addAdmin(@Valid @RequestBody AddAdminDTO addAdminDTO, Errors errors) throws MessagingException {
        logger.info("AdminController : addAdmin");
        if (errors.hasErrors()){
            return new ResponseDTO(StatusCodeEnum.BAD_REQUEST.getStatusCode(), errors.getAllErrors().get(0).getDefaultMessage(),null);
        }else{
            return adminService.addAdmin(addAdminDTO);
        }
    }

    @PreAuthorize("hasRole('ROLE_Admin')")
    @GetMapping("/getAdmin/{id}")
    public ResponseDTO getAdmin(@PathVariable("id") Long id) {
        logger.info("AdminController : getAdmin");
        return adminService.getAdmin(id);
    }
    @PreAuthorize("hasRole('ROLE_Admin')")
    @PostMapping("/import")
    public ResponseDTO importFromExcel(@RequestParam("file") MultipartFile reapExcelDataFile) throws IOException, ParseException {
        return travellingModeService.importData(reapExcelDataFile);
    }

}
