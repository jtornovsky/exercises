package com.realestate.spabuyer.dto;

import com.realestate.spabuyer.entities.Buyer;
import com.realestate.spabuyer.entities.Property;
import com.realestate.spabuyer.types.PropertyType;

import lombok.Builder;

@Builder
public class PropertyDto {

    private PropertyType propertyType;
    private Buyer buyer;

    public static PropertyDto convert(Property property) {
        return PropertyDto.builder()
                .propertyType(property.getPropertyType())
                .buyer(property.getBuyer())
                .build();
    }
}
