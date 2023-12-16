package com.example.TicketCollector.repository;

import com.example.TicketCollector.entity.RazorpayPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RazorpayPaymentRepository extends JpaRepository<RazorpayPaymentEntity,Long> {
}
