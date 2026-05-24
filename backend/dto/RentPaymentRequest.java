package com.hackathon.leasemanagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RentPaymentRequest {

    @NotNull(message = "Lease agreement ID is required")
    @Schema(example = "1")
    private Long leaseAgreementId;

    @NotBlank(message = "Payment month is required")
    @Schema(example = "June-2026")
    private String paymentMonth;

    @NotNull(message = "Payment date is required")
    @Schema(example = "2026-06-12")
    private LocalDate paymentDate;
}
