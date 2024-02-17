package com.realestate.spabuyer.services;

import java.util.List;

import com.realestate.spabuyer.dto.AdminDto;
import com.realestate.spabuyer.dto.BuyerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realestate.spabuyer.dto.PropertyDto;
import com.realestate.spabuyer.entities.Buyer;
import com.realestate.spabuyer.types.PropertyType;

@Service
public class AdminService {

	private final BuyerService buyerService;
	private final PropertyService propertyService;
	
	@Autowired
	public AdminService(BuyerService buyerService, PropertyService propertyService) {
		this.buyerService = buyerService;
		this.propertyService = propertyService;
	}

	public List<BuyerDto> getAllBuyers() {
		List<Buyer> buyers = buyerService.getAllBuyers();
		return buyers.stream().map(BuyerDto::convert).toList();
	}
	
	public List<PropertyDto> getBuyerProperties(Buyer buyer) {
		return propertyService.getPropertiesByByerId(buyer);
	}

	public List<PropertyDto> getPropertiesByType(PropertyType propertyType) {
		return propertyService.getPropertiesByType(propertyType); 
	}

}
