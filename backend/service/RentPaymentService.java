package com.hackathon.leasemanagement.service;

import com.hackathon.leasemanagement.dto.RentPaymentRequest;
import com.hackathon.leasemanagement.entity.LeaseAgreement;
import com.hackathon.leasemanagement.entity.RentPayment;
import com.hackathon.leasemanagement.enums.LeaseStatus;
import com.hackathon.leasemanagement.enums.PaymentStatus;
import com.hackathon.leasemanagement.exception.BadRequestException;
import com.hackathon.leasemanagement.repository.RentPaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RentPaymentService {

    private final RentPaymentRepository rentPaymentRepository;
    private final LeaseService leaseService;

    public RentPayment processPayment(RentPaymentRequest request) {
        LeaseAgreement lease = leaseService.getLeaseById(request.getLeaseAgreementId());
        
        if (lease.getLeaseStatus() != LeaseStatus.ACTIVE) {
            throw new BadRequestException("Lease must be ACTIVE to pay rent");
        }
        
        BigDecimal rentAmount = lease.getMonthlyRentAmount();
        BigDecimal penalty = BigDecimal.ZERO;
        PaymentStatus status = PaymentStatus.PAID;
        
        if (request.getPaymentDate().getDayOfMonth() > 10) {
            penalty = rentAmount.multiply(new BigDecimal("0.05"));
            status = PaymentStatus.OVERDUE;
        }

        BigDecimal totalAmount = rentAmount.add(penalty);

        RentPayment payment = RentPayment.builder()
                .leaseAgreement(lease)
                .amount(totalAmount)
                .paymentMonth(request.getPaymentMonth())
                .paymentDate(request.getPaymentDate())
                .paymentStatus(status)
                .penaltyAmount(penalty)
                .referenceId(UUID.randomUUID().toString())
                .build();

        return rentPaymentRepository.save(payment);
    }

    public List<RentPayment> getAllPayments() {
        return rentPaymentRepository.findAll();
    }

    public List<RentPayment> getPaymentsByLeaseId(Long leaseId) {
        LeaseAgreement lease = leaseService.getLeaseById(leaseId);
        return rentPaymentRepository.findByLeaseAgreement(lease);
    }
}
