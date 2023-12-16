package com.example.TicketCollector.repository;

import com.example.TicketCollector.entity.BookTicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookTicketRepository extends JpaRepository<BookTicketEntity, Long> {
    @Query(value = "SELECT * FROM book_ticket_entity WHERE travelling_mode_id=:id AND is_canceled is false",nativeQuery = true)
    List<BookTicketEntity> findByTmId(Long id);
}
