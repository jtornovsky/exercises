package com.realestate.spabuyer.controllers;

import com.realestate.spabuyer.dto.BuyerDto;
import com.realestate.spabuyer.entities.Buyer;
import com.realestate.spabuyer.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("admin")
public class AdminController {

    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("all-buyers")
    public List<BuyerDto> getAllBuyers() {
        return adminService.getAllBuyers();
    }
}
