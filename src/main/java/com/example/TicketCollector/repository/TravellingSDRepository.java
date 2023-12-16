package com.example.TicketCollector.repository;

import com.example.TicketCollector.entity.TravellingSDEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TravellingSDRepository extends JpaRepository<TravellingSDEntity, Long> {
@Query("select tsd from TravellingSDEntity tsd where tsd.tmId =:id")
    List<TravellingSDEntity> findByTmId(Long id);
}
