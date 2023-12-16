package com.example.TicketCollector.repository;

import com.example.TicketCollector.entity.TravellingModeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TravellingModeRepository extends JpaRepository<TravellingModeEntity,Long> {
    @Query("select tm from TravellingModeEntity tm where tm.travelModeNumber =:travelModeNumber")
    Optional<TravellingModeEntity> findByTravellingModeNumber(String travelModeNumber);

    @Query(value = "SELECT * FROM travelling_mode_entity WHERE booking_typeid=:bookingTypeId", nativeQuery = true)
    List<TravellingModeEntity> findByBookingTypeId(Long bookingTypeId);

    @Query(value = "SELECT * FROM travelling_mode_entity WHERE id=:tmId AND is_active is true", nativeQuery = true)
    TravellingModeEntity findByTravellingModeId(Long tmId);

    @Query(value = "SELECT * FROM travelling_mode_entity WHERE id=:travellingModeId AND is_active='false'",nativeQuery = true)
    TravellingModeEntity findByIsActive(Long travellingModeId);
}
