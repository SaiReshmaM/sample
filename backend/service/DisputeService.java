package com.hackathon.leasemanagement.service;

import com.hackathon.leasemanagement.dto.CreateDisputeRequest;
import com.hackathon.leasemanagement.entity.Dispute;
import com.hackathon.leasemanagement.entity.LeaseAgreement;
import com.hackathon.leasemanagement.entity.User;
import com.hackathon.leasemanagement.enums.DisputeStatus;
import com.hackathon.leasemanagement.enums.Role;
import com.hackathon.leasemanagement.exception.BadRequestException;
import com.hackathon.leasemanagement.exception.ResourceNotFoundException;
import com.hackathon.leasemanagement.repository.DisputeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DisputeService {

    private final DisputeRepository disputeRepository;
    private final LeaseService leaseService;
    private final UserService userService;

    public Dispute raiseDispute(CreateDisputeRequest request) {
        LeaseAgreement lease = leaseService.getLeaseById(request.getLeaseAgreementId());
        User raisedBy = userService.getUserById(request.getRaisedById());

        if (raisedBy.getRole() != Role.TENANT && raisedBy.getRole() != Role.PROPERTY_OWNER) {
            throw new BadRequestException("Only TENANT or PROPERTY_OWNER can raise a dispute");
        }

        if (request.getDisputeReason() == null || request.getDisputeReason().trim().isEmpty()) {
            throw new BadRequestException("Dispute reason cannot be empty");
        }

        Dispute dispute = Dispute.builder()
                .leaseAgreement(lease)
                .raisedBy(raisedBy)
                .disputeReason(request.getDisputeReason())
                .disputeStatus(DisputeStatus.OPEN)
                .build();

        return disputeRepository.save(dispute);
    }

    public Dispute resolveDispute(Long disputeId, Long resolverId, String resolutionRemark) {
        Dispute dispute = disputeRepository.findById(disputeId)
                .orElseThrow(() -> new ResourceNotFoundException("Dispute not found: " + disputeId));

        User resolver = userService.getUserById(resolverId);
        if (resolver.getRole() != Role.DISPUTE_MANAGER) {
            throw new BadRequestException("Only DISPUTE_MANAGER can resolve a dispute");
        }

        dispute.setDisputeStatus(DisputeStatus.RESOLVED);
        dispute.setResolvedBy(resolver);
        dispute.setResolutionRemark(resolutionRemark);
        dispute.setResolvedAt(LocalDateTime.now());

        return disputeRepository.save(dispute);
    }

    public java.util.List<Dispute> getAllDisputes() {
        return disputeRepository.findAll();
    }
}
