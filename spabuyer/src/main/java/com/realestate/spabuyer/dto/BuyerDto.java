package com.realestate.spabuyer.dto;

import com.realestate.spabuyer.entities.Buyer;
import com.realestate.spabuyer.entities.Property;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Builder;

import java.util.List;

@Builder
public class BuyerDto {

    private String buyerName;
    private List<Property> boughtProperties;

    public static BuyerDto convert(Buyer buyer) {
        return BuyerDto.builder()
                .buyerName(buyer.getBuyerName())
                .boughtProperties(buyer.getBoughtProperties())
                .build();
    }
}
