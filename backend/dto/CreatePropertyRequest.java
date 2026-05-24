package com.hackathon.leasemanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreatePropertyRequest {

    @NotBlank(message = "Property name cannot be blank")
    private String propertyName;

    @NotBlank(message = "Location cannot be blank")
    private String location;

    private String propertyType;

    @NotNull(message = "Monthly rent amount is required")
    private BigDecimal monthlyRentAmount;

    @NotNull(message = "Owner ID is required")
    private Long ownerId;
}
