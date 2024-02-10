package com.realestate.spabuyer.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.realestate.spabuyer.dto.PropertyDto;
import com.realestate.spabuyer.entities.Buyer;
import com.realestate.spabuyer.entities.Property;
import com.realestate.spabuyer.repo.PropertyRepository;
import com.realestate.spabuyer.types.PropertyType;

@Service
public class PropertyService {
	
	private final PropertyRepository propertyRepository;
	
	@Autowired
	public PropertyService(PropertyRepository propertyRepository) {
		this.propertyRepository = propertyRepository;
	}
	
	public List<PropertyDto> getPropertiesByByerId(Buyer buyer) {
		List<Property> properties = propertyRepository.findByBuyer_buyerId(buyer.getBuyerId());
		return convertList(properties);
	}	
	
	public List<PropertyDto> getPropertiesByType(PropertyType propertyType) {
		List<Property> properties = propertyRepository.findPropertyByPropertyType(propertyType);
		return convertList(properties);
	}
	
	public boolean isPropertyBought(long propertyId) {
		Property property = propertyRepository.findPropertyByPropertyId(propertyId);
		return property.getBuyer() != null;
	}
	
	@Transactional
	public void buyProperty(Property property, Buyer buyer) {
		if (isPropertyBought(property.getPropertyId())) {
			return;
		}
		propertyRepository.updateProperty(property.getPropertyId(), buyer.getBuyerId());
	}
	
    private List<PropertyDto> convertList(List<Property> properties) {
        return properties.stream()
                .map(PropertyDto::convert)
                .collect(Collectors.toList());
    }	
}
