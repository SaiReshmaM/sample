package com.hackathon.leasemanagement.repository;

import com.hackathon.leasemanagement.entity.Property;
import com.hackathon.leasemanagement.entity.User;
import com.hackathon.leasemanagement.enums.PropertyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findByOwner(User owner);
    List<Property> findByAvailabilityStatus(PropertyStatus status);
}
