package com.realestate.spabuyer.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.realestate.spabuyer.entities.Buyer;
import com.realestate.spabuyer.entities.Property;

@Repository
public interface BuyerRepository extends JpaRepository<Buyer, Long> {
	
	@Query(/*appropriate select query*/)
	List<Property> findPropertiesByBuyerId(long id);

}
