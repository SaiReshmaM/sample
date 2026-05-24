package com.hackathon.leasemanagement.repository;

import com.hackathon.leasemanagement.entity.Dispute;
import com.hackathon.leasemanagement.entity.LeaseAgreement;
import com.hackathon.leasemanagement.enums.DisputeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisputeRepository extends JpaRepository<Dispute, Long> {
    List<Dispute> findByLeaseAgreement(LeaseAgreement leaseAgreement);
    List<Dispute> findByDisputeStatus(DisputeStatus status);
}
