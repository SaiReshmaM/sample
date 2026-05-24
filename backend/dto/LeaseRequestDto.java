package com.hackathon.leasemanagement.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class LeaseRequestDto {

    @NotNull(message = "Property ID is required")
    private Long propertyId;

    @NotNull(message = "Tenant ID is required")
    private Long tenantId;

    @NotNull(message = "Lease start date is required")
    private LocalDate leaseStartDate;

    @NotNull(message = "Lease end date is required")
    private LocalDate leaseEndDate;

    private BigDecimal securityDeposit;
}
