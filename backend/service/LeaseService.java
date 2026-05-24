package com.hackathon.leasemanagement.service;

import com.hackathon.leasemanagement.dto.LeaseRequestDto;
import com.hackathon.leasemanagement.entity.LeaseAgreement;
import com.hackathon.leasemanagement.entity.Property;
import com.hackathon.leasemanagement.entity.User;
import com.hackathon.leasemanagement.enums.LeaseStatus;
import com.hackathon.leasemanagement.enums.PropertyStatus;
import com.hackathon.leasemanagement.enums.Role;
import com.hackathon.leasemanagement.exception.BadRequestException;
import com.hackathon.leasemanagement.exception.ResourceNotFoundException;
import com.hackathon.leasemanagement.repository.LeaseAgreementRepository;
import com.hackathon.leasemanagement.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaseService {

    private final LeaseAgreementRepository leaseAgreementRepository;
    private final PropertyService propertyService;
    private final UserService userService;
    private final PropertyRepository propertyRepository;

    public LeaseAgreement requestLease(LeaseRequestDto request) {
        User tenant = userService.getUserById(request.getTenantId());
        if (tenant.getRole() != Role.TENANT) {
            throw new BadRequestException("Only TENANT can request a lease");
        }

        Property property = propertyService.getPropertyById(request.getPropertyId());
        if (property.getAvailabilityStatus() != PropertyStatus.AVAILABLE) {
            throw new BadRequestException("Property must be AVAILABLE to request a lease");
        }

        boolean hasActiveLease = leaseAgreementRepository.existsByPropertyAndLeaseStatus(property, LeaseStatus.ACTIVE);
        if (hasActiveLease) {
            throw new BadRequestException("Property cannot have multiple ACTIVE leases");
        }

        LeaseAgreement lease = LeaseAgreement.builder()
                .property(property)
                .tenant(tenant)
                .leaseStartDate(request.getLeaseStartDate())
                .leaseEndDate(request.getLeaseEndDate())
                .monthlyRentAmount(property.getMonthlyRentAmount())
                .securityDeposit(request.getSecurityDeposit())
                .leaseStatus(LeaseStatus.REQUESTED)
                .build();

        return leaseAgreementRepository.save(lease);
    }

    public LeaseAgreement approveLease(Long leaseId, Long approverId) {
        LeaseAgreement lease = leaseAgreementRepository.findById(leaseId)
                .orElseThrow(() -> new ResourceNotFoundException("Lease Agreement not found: " + leaseId));

        if (lease.getLeaseStatus() == LeaseStatus.COMPLETED) {
            throw new BadRequestException("Completed lease cannot be modified");
        }

        User approver = userService.getUserById(approverId);
        if (approver.getRole() != Role.LEASE_MANAGER) {
            throw new BadRequestException("Only LEASE_MANAGER can approve an agreement");
        }

        lease.setLeaseStatus(LeaseStatus.ACTIVE);
        lease.setApprovedBy(approver);
        
        Property property = lease.getProperty();
        property.setAvailabilityStatus(PropertyStatus.LEASED);
        propertyRepository.save(property);

        return leaseAgreementRepository.save(lease);
    }

    public List<LeaseAgreement> getAllLeases() {
        return leaseAgreementRepository.findAll();
    }
    
    public LeaseAgreement getLeaseById(Long leaseId) {
        return leaseAgreementRepository.findById(leaseId)
                .orElseThrow(() -> new ResourceNotFoundException("Lease not found"));
    }
}
