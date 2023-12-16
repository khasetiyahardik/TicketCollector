package com.example.TicketCollector.serviceImpl;


import com.example.TicketCollector.constants.Constants;

import com.example.TicketCollector.dto.AddAdminDTO;
import com.example.TicketCollector.dto.ResponseDTO;
import com.example.TicketCollector.entity.UserEntity;
import com.example.TicketCollector.enums.StatusCodeEnum;
import com.example.TicketCollector.exception.AdminNotFoundException;
import com.example.TicketCollector.exception.EmailOrNoAlreadyExistException;
import com.example.TicketCollector.exception.InvalidUserRoleException;
import com.example.TicketCollector.repository.UserRepository;
import com.example.TicketCollector.service.AdminService;
import com.example.TicketCollector.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service
public class AdminServiceImpl implements AdminService {

    public static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public ResponseDTO addAdmin(AddAdminDTO addAdminDTO) throws MessagingException {
        logger.info("AdminServiceImpl : addAdmin");
        if (addAdminDTO.getRole().equalsIgnoreCase("Admin")) {
            validateAdminEmailNumberAndName(addAdminDTO);
            return new ResponseDTO(StatusCodeEnum.OK.getStatusCode(), Constants.ADMIN_ADDED_SUCCESSFULLY, null);
        } else {
            throw new InvalidUserRoleException(Constants.INVALID_USER_ROLE);
        }
    }

    @Override
    public ResponseDTO getAdmin(Long id) {
        logger.info("AdminServiceImpl : getAdmin");
        Optional<UserEntity> user = userRepository.findByAdminId(id);
        if (user.isPresent()) {
            return new ResponseDTO(StatusCodeEnum.OK.getStatusCode(), Constants.DATA_FETCHED_SUCCESSFULLY, user);
        } else {
            throw new AdminNotFoundException(Constants.ADMIN_NOT_FOUND);
        }
    }

    private void validateAdminEmailNumberAndName(AddAdminDTO addAdminDTO) throws MessagingException {
        logger.info("AdminServiceImpl : validateAdminEmailNumberAndName");
        Long existingData = userRepository.findByNoAndEmail(addAdminDTO.getContactNumber(), addAdminDTO.getEmail(), addAdminDTO.getName());
        if (existingData == 0) {
            UserEntity userEntity = UserEntity.builder().build();
            BeanUtils.copyProperties(addAdminDTO, userEntity);
            Set<String> roleEntities = new HashSet<>();
            roleEntities.add(addAdminDTO.getRole());
            userEntity.setRoles(roleEntities);
            userEntity.setPassword(passwordEncoder.encode(addAdminDTO.getPassword()));
            userEntity.setCreatedOn(new Date());
            userEntity.setIsVerified(false);
            userEntity.setIsActive(true);
            userEntity.setUpdatedOn(null);
            userRepository.save(userEntity);
            emailService.sendVerifyEmail(userEntity);
        } else {
            throw new EmailOrNoAlreadyExistException(Constants.ALREADY_EXIST);
        }
    }
}
