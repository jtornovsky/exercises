package com.realestate.spabuyer.entities;


import com.realestate.spabuyer.types.PropertyType;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Property {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false)
	private long propertyId;

	@Column(name = "property_type", nullable = false)
	private PropertyType propertyType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "buyerId")
	private Buyer buyer;
}
