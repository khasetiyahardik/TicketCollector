package com.example.TicketCollector.repository;

import com.example.TicketCollector.entity.SDEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface SdRepository extends JpaRepository<SDEntity, Long> {

    @Query("select sd from SDEntity sd where sd.sd_id=:id and sd.destination=:destination")
    Optional<SDEntity> findByDestination(Long id, String destination);

    @Query("select sd from SDEntity sd where sd.tmId=:id")
    List<SDEntity> findByTmId(Long id);

    @Query("select sd from SDEntity sd where sd.destination=:destination and sd.tmId=:tmId and sd.Price=:price and sd.arrivalTime=:arrivalTime and sd.departureTime=:departureTime")
    Optional<SDEntity> findByDestinationAndTravellingMode(String destination, Long tmId, Double price, Date arrivalTime, Date departureTime);
}
