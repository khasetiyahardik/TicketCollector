package com.example.TicketCollector.service;


import com.example.TicketCollector.dto.AddAdminDTO;
import com.example.TicketCollector.dto.ResponseDTO;

import javax.mail.MessagingException;

public interface AdminService {
    ResponseDTO addAdmin(AddAdminDTO addAdminDTO) throws MessagingException;

    ResponseDTO getAdmin(Long id);
}
