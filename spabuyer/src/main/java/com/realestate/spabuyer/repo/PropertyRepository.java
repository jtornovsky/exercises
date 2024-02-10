package com.realestate.spabuyer.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.realestate.spabuyer.entities.Property;
import com.realestate.spabuyer.types.PropertyType;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

	List<Property> findByBuyer_buyerId(long id);
	List<Property> findPropertyByPropertyType(PropertyType propertyType);

	Property findPropertyByPropertyId(long id);

	@Modifying
	@Query("UPDATE Property p SET p.buyer.id = :buyerId WHERE p.id = :propertyId")
	void updateProperty(@Param("propertyId") long propertyId, @Param("buyerId") long buyerId);
}

