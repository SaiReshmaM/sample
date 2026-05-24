package com.hackathon.leasemanagement.service;

import com.hackathon.leasemanagement.dto.CreatePropertyRequest;
import com.hackathon.leasemanagement.entity.Property;
import com.hackathon.leasemanagement.entity.User;
import com.hackathon.leasemanagement.enums.PropertyStatus;
import com.hackathon.leasemanagement.enums.Role;
import com.hackathon.leasemanagement.exception.BadRequestException;
import com.hackathon.leasemanagement.exception.ResourceNotFoundException;
import com.hackathon.leasemanagement.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final UserService userService;

    public Property registerProperty(CreatePropertyRequest request) {
        User owner = userService.getUserById(request.getOwnerId());
        
        if (owner.getRole() != Role.PROPERTY_OWNER) {
            throw new BadRequestException("Only PROPERTY_OWNER can register a property");
        }

        Property property = Property.builder()
                .propertyName(request.getPropertyName())
                .location(request.getLocation())
                .propertyType(request.getPropertyType())
                .monthlyRentAmount(request.getMonthlyRentAmount())
                .owner(owner)
                .availabilityStatus(PropertyStatus.AVAILABLE)
                .build();
                
        return propertyRepository.save(property);
    }

    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    public Property getPropertyById(Long id) {
        return propertyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + id));
    }

    public List<Property> getAvailableProperties() {
        return propertyRepository.findByAvailabilityStatus(PropertyStatus.AVAILABLE);
    }
}
