package com.hackathon.leasemanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateDisputeRequest {

    @NotNull(message = "Lease agreement ID is required")
    private Long leaseAgreementId;

    @NotNull(message = "Raised by user ID is required")
    private Long raisedById;

    @NotBlank(message = "Dispute reason cannot be empty")
    private String disputeReason;
}
