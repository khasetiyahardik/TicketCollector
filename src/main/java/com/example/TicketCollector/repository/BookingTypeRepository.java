package com.example.TicketCollector.repository;

import com.example.TicketCollector.entity.BookingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BookingTypeRepository extends JpaRepository<BookingType, Long> {
    @Query("select bt from BookingType bt where bt.name =:name")
    Optional<BookingType> findByName(String name);

    @Query(value = "SELECT * FROM booking_type WHERE id=:bookingTypeId", nativeQuery = true)
    BookingType findByBookingTypeId(Long bookingTypeId);
}
