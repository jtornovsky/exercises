package com.realestate.spabuyer.entities;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "buyer")
//@Data
@Getter
@Setter
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
