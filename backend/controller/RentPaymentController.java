package com.hackathon.leasemanagement.controller;

import com.hackathon.leasemanagement.dto.RentPaymentRequest;
import com.hackathon.leasemanagement.entity.RentPayment;
import com.hackathon.leasemanagement.service.RentPaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Tag(name = "Rent Payment Management", description = "APIs for processing and retrieving rent payments")
public class RentPaymentController {

    private final RentPaymentService rentPaymentService;

    @PostMapping
    @PreAuthorize("hasRole('TENANT')")
    @Operation(summary = "Process a rent payment")
    public ResponseEntity<RentPayment> processPayment(@Valid @RequestBody RentPaymentRequest request) {
        return new ResponseEntity<>(rentPaymentService.processPayment(request), HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('TENANT', 'ADMIN')")
    @Operation(summary = "Get all payments or filter by leaseId")
    public ResponseEntity<List<RentPayment>> getPayments(@RequestParam(required = false) Long leaseId) {
        if (leaseId != null) {
            return ResponseEntity.ok(rentPaymentService.getPaymentsByLeaseId(leaseId));
        }
        return ResponseEntity.ok(rentPaymentService.getAllPayments());
    }
}
