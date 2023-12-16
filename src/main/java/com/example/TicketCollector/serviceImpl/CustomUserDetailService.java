package com.example.TicketCollector.serviceImpl;

import com.example.TicketCollector.entity.UserEntity;
import com.example.TicketCollector.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service("customUserService")
@Slf4j
public class CustomUserDetailService implements UserDetailsService {
    public static final Logger logger = LoggerFactory.getLogger(CustomUserDetailService.class);

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("inside loadUserByUsername method");
        Optional<UserEntity> entity = userRepository.findOneByEmailIgnoreCase(username);
        UserEntity userEntity = null;
        if (entity.isPresent()) {
            userEntity = entity.get();
        }
        if (userEntity == null) {
            throw new UsernameNotFoundException("", new Throwable("Invalid Creds"));
        }
        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for(String role:userEntity.getRoles()){
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role));
        }
        return new org.springframework.security.core.userdetails.User(userEntity.getEmail(), userEntity.getPassword(), authorities);
    }
}


