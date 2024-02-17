package com.realestate.spabuyer.dto;

import com.realestate.spabuyer.entities.Admin;
import com.realestate.spabuyer.entities.Buyer;
import com.realestate.spabuyer.entities.Property;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Builder;

import java.util.List;

@Builder
public class AdminDto {

    private String adminName;
    private List<Buyer> buyers;
    
    public static AdminDto convert(Admin admin) {
        return AdminDto.builder()
                .adminName(admin.getAdminName())
                .buyers(admin.getBuyers())
                .build();
    }
}
