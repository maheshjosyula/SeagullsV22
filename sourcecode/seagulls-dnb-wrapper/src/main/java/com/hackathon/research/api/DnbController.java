package com.hackathon.research.api;

import com.dnb.model.OrderProductResponse;
import com.hackathon.research.model.CompanyProfile;
import com.hackathon.research.service.DnbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DnbController {
    @Autowired
    DnbService dnbService;
    @GetMapping("/api/ping")
    public String ping(){
        return "hello";
    }
    @GetMapping("/api/companyprofile/{dunsNum}")
    public CompanyProfile getCompanyProfile(@PathVariable String dunsNum){
        return dnbService.getOwners(dunsNum);
    }
}
