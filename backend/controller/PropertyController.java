package com.hackathon.leasemanagement.controller;

import com.hackathon.leasemanagement.dto.CreatePropertyRequest;
import com.hackathon.leasemanagement.entity.Property;
import com.hackathon.leasemanagement.service.PropertyService;
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
@RequestMapping("/api/properties")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@Tag(name = "Property Management", description = "APIs for managing properties")
public class PropertyController {

    private final PropertyService propertyService;

    @PostMapping
    @PreAuthorize("hasRole('PROPERTY_OWNER')")
    @Operation(summary = "Register a new property (Only for PROPERTY_OWNER)")
    public ResponseEntity<Property> registerProperty(@Valid @RequestBody CreatePropertyRequest request) {
        return new ResponseEntity<>(propertyService.registerProperty(request), HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PROPERTY_OWNER')")
    @Operation(summary = "Get all properties")
    public ResponseEntity<List<Property>> getAllProperties() {
        return ResponseEntity.ok(propertyService.getAllProperties());
    }

    @GetMapping("/available")
    @PreAuthorize("hasAnyRole('TENANT', 'ADMIN')")
    @Operation(summary = "Get all available properties")
    public ResponseEntity<List<Property>> getAvailableProperties() {
        return ResponseEntity.ok(propertyService.getAvailableProperties());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROPERTY_OWNER', 'TENANT')")
    @Operation(summary = "Get a property by ID")
    public ResponseEntity<Property> getPropertyById(@PathVariable Long id) {
        return ResponseEntity.ok(propertyService.getPropertyById(id));
    }
}
