package com.hackathon.leasemanagement.controller;

import com.hackathon.leasemanagement.dto.LeaseRequestDto;
import com.hackathon.leasemanagement.entity.LeaseAgreement;
import com.hackathon.leasemanagement.service.LeaseService;
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
@RequestMapping("/api/leases")
@RequiredArgsConstructor
@Tag(name = "Lease Management", description = "APIs for requesting and approving lease agreements")
public class LeaseController {

    private final LeaseService leaseService;

    @PostMapping("/request")
    @PreAuthorize("hasRole('TENANT')")
    @Operation(summary = "Request a lease (Only for TENANT)")
    public ResponseEntity<LeaseAgreement> requestLease(@Valid @RequestBody LeaseRequestDto request) {
        return new ResponseEntity<>(leaseService.requestLease(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/approve")
    @PreAuthorize("hasRole('LEASE_MANAGER')")
    @Operation(summary = "Approve a lease request (Only for LEASE_MANAGER)")
    public ResponseEntity<LeaseAgreement> approveLease(@PathVariable Long id, @RequestParam Long approverId) {
        return ResponseEntity.ok(leaseService.approveLease(id, approverId));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('LEASE_MANAGER', 'TENANT', 'ADMIN')")
    @Operation(summary = "Get all leases")
    public ResponseEntity<List<LeaseAgreement>> getAllLeases() {
        return ResponseEntity.ok(leaseService.getAllLeases());
    }
}
