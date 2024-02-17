package com.realestate.spabuyer.services;

import java.util.List;

import com.realestate.spabuyer.dto.BuyerDto;
import com.realestate.spabuyer.repo.BuyerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realestate.spabuyer.dto.PropertyDto;
import com.realestate.spabuyer.entities.Buyer;
import com.realestate.spabuyer.entities.Property;
import com.realestate.spabuyer.types.PropertyType;

@Service
public class BuyerService {

	private final PropertyService propertyService;
	private final BuyerRepository buyerRepository;
	
	@Autowired
	public BuyerService(PropertyService propertyService, BuyerRepository buyerRepository) {
		this.propertyService = propertyService;
		this.buyerRepository = buyerRepository;
	}
	
	// the buyer need to be taken from the context of the session
	// as after login we should have this information
	// i still don't know how to do it, so let's leave it for the next steps.
	public List<PropertyDto> getMyBoughtProperties(Buyer buyer) {
		return propertyService.getPropertiesByByerId(buyer);
	}	
	
	public List<PropertyDto> getPropertiesByType(PropertyType propertyType) {
		return propertyService.getPropertiesByType(propertyType); 
	}
	
	// the buyer need to be taken from the context of the session
	// as after login we should have this information
	// i still don't know how to do it, so let's leave it for the next steps.
	public void buyProperty(Property property, Buyer buyer) {
		propertyService.buyProperty(property, buyer);
	}

	List<Buyer> getAllBuyers() {
		return buyerRepository.findAll();
	}
}
