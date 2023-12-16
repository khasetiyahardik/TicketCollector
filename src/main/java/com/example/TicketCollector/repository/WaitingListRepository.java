package com.example.TicketCollector.repository;

import com.example.TicketCollector.entity.WaitingListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaitingListRepository extends JpaRepository<WaitingListEntity, Long> {

}
