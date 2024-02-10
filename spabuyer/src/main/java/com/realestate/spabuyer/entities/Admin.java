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
public class Admin {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false)
	private long adminId;

	@Column(name = "admin_name", nullable = false)
	private String adminName;
	
	@OneToMany(mappedBy = "buyerId", fetch = FetchType.LAZY)
	private List<Buyer> buyers;
}
