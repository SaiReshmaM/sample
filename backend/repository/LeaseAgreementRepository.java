package com.hackathon.leasemanagement.repository;

import com.hackathon.leasemanagement.entity.LeaseAgreement;
import com.hackathon.leasemanagement.entity.Property;
import com.hackathon.leasemanagement.entity.User;
import com.hackathon.leasemanagement.enums.LeaseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaseAgreementRepository extends JpaRepository<LeaseAgreement, Long> {
    List<LeaseAgreement> findByTenant(User tenant);
    List<LeaseAgreement> findByProperty(Property property);
    boolean existsByPropertyAndLeaseStatus(Property property, LeaseStatus status);
}
