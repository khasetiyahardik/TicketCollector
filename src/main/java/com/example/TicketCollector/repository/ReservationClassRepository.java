package com.example.TicketCollector.repository;

import com.example.TicketCollector.entity.ReservationClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationClassRepository extends JpaRepository<ReservationClassEntity, Long> {

    @Query("select rc from ReservationClassEntity rc where rc.isFlight = true")
    List<ReservationClassEntity> findPlaneClass();

    @Query("select rc from ReservationClassEntity rc where rc.isTrain = true")
    List<ReservationClassEntity> findTrainClass();

    @Query("select rc from ReservationClassEntity rc where rc.tmId = :id")
    List<ReservationClassEntity> findByTmID(Long id);
    @Query("select rc from ReservationClassEntity rc where rc.tmId = :tmId and rc.className=:ticketClass")
    Optional<ReservationClassEntity> finByClassName(String ticketClass, Long tmId);
}
