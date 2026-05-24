package com.hackathon.leasemanagement.controller;

import com.hackathon.leasemanagement.dto.CreateDisputeRequest;
import com.hackathon.leasemanagement.entity.Dispute;
import com.hackathon.leasemanagement.service.DisputeService;
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
@RequestMapping("/api/disputes")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@Tag(name = "Dispute Management", description = "APIs for raising and resolving disputes")
public class DisputeController {

    private final DisputeService disputeService;

    @GetMapping
    @PreAuthorize("hasAnyRole('DISPUTE_MANAGER', 'ADMIN', 'TENANT', 'PROPERTY_OWNER')")
    @Operation(summary = "Get all disputes")
    public ResponseEntity<List<Dispute>> getAllDisputes() {
        return ResponseEntity.ok(disputeService.getAllDisputes());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('TENANT', 'PROPERTY_OWNER')")
    @Operation(summary = "Raise a dispute (Only for TENANT or PROPERTY_OWNER)")
    public ResponseEntity<Dispute> raiseDispute(@Valid @RequestBody CreateDisputeRequest request) {
        return new ResponseEntity<>(disputeService.raiseDispute(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/resolve")
    @PreAuthorize("hasRole('DISPUTE_MANAGER')")
    @Operation(summary = "Resolve a dispute (Only for DISPUTE_MANAGER)")
    public ResponseEntity<Dispute> resolveDispute(
            @PathVariable Long id,
            @RequestParam Long resolverId,
            @RequestParam String resolutionRemark) {
        return ResponseEntity.ok(disputeService.resolveDispute(id, resolverId, resolutionRemark));
    }
}
