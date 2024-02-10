package com.realestate.spabuyer.entities;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Buyer {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false)
	private long buyerId;

	@Column(name = "buyer_name", nullable = false)
	private String buyerName;
	
	@OneToMany(mappedBy = "buyer", fetch = FetchType.LAZY)
	private List<Property> boughtProperties;
}
