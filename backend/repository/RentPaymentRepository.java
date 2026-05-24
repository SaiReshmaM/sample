package com.hackathon.leasemanagement.repository;

import com.hackathon.leasemanagement.entity.LeaseAgreement;
import com.hackathon.leasemanagement.entity.RentPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentPaymentRepository extends JpaRepository<RentPayment, Long> {
    List<RentPayment> findByLeaseAgreement(LeaseAgreement leaseAgreement);
}
