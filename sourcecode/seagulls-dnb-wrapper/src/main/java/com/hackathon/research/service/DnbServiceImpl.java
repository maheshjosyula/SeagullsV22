package com.hackathon.research.service;

import com.dnb.model.CurrentPrincipal;
import com.dnb.model.OrderProductResponse;
import com.dnb.model.SocioEconomicIdentification;
import com.hackathon.research.client.DnbClient;
import com.hackathon.research.model.CompanyProfile;
import com.hackathon.research.model.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class DnbServiceImpl implements DnbService{
    @Autowired
    DnbClient dnbClient;
    @Override
    public CompanyProfile getOwners(String dunsNum) {
        OrderProductResponse orderProductResponse=dnbClient.getCompanyProfile(dunsNum);
        CompanyProfile companyProfile=new CompanyProfile();
        if(orderProductResponse==null){
            companyProfile=getMockResponse();
        }else{
            String companyName=orderProductResponse.getOrderProductResponse().getOrderProductResponseDetail().getProduct().getOrganization().getOrganizationName().getOrganizationPrimaryName().get(0).getOrganizationName().get$();
            companyProfile.setCompanyName(companyName);
            SocioEconomicIdentification socioEconomicIdentification=orderProductResponse.getOrderProductResponse().getOrderProductResponseDetail().getProduct().getOrganization().getSocioEconomicIdentification();
            if(socioEconomicIdentification!=null){
                companyProfile.setFemaleOwnedIndicator(socioEconomicIdentification.getFemaleOwnedIndicator());
                companyProfile.setMinorityOwnedIndicator(socioEconomicIdentification.getMinorityOwnedIndicator());
                companyProfile.setOwnershipEthnicity(socioEconomicIdentification.getOwnershipEthnicity().get(0).getEthnicityTypeText().get$());
            }
            List<Owner> Owners =mapOwnershipDetails(orderProductResponse);
            companyProfile.setOwners(Owners);
        }
        return companyProfile;
    }

    private CompanyProfile getMockResponse() {
        CompanyProfile companyProfile=new CompanyProfile();
        companyProfile.setCompanyName("Gorman Manufacturing Company, Inc.");
        companyProfile.setMinorityOwnedIndicator(true);
        companyProfile.setFemaleOwnedIndicator(false);
        companyProfile.setOwnershipEthnicity("Hispanic");
        List<Owner> owners =new ArrayList<>();
        Owner owner1=new Owner();
        owner1.setOwnerName("Kevin J Hunt");
        owner1.setGender("male");
        owner1.setDesignation("Secretary");
        Owner owner2=new Owner();
        owner2.setOwnerName("Leslie Smith");
        owner2.setGender("male");
        owner2.setDesignation("President");
        owners.add(owner1);
        owners.add(owner2);
        companyProfile.setOwners(owners);
        return companyProfile;
    }

    private List<Owner> mapOwnershipDetails(OrderProductResponse orderProductResponse) {
        List<Owner> owners =new ArrayList<>();
        List<CurrentPrincipal> currentPrincipals=orderProductResponse.getOrderProductResponse().getOrderProductResponseDetail().getProduct().getOrganization().getPrincipalsAndManagement().getCurrentPrincipal();
        currentPrincipals.forEach(currentPrincipal -> {
            Owner owner =new Owner();
            owner.setOwnerName(currentPrincipal.getPrincipalName().getFullName());
            owner.setDesignation(currentPrincipal.getJobTitle().get(0).getJobTitleText().get$());
            if("Mr".equalsIgnoreCase(currentPrincipal.getPrincipalName().getNamePrefix().getNamePrefixText())){
                owner.setGender("male");
            }else{
                owner.setGender("female");
            }
            owners.add(owner);
        });
        return owners;
    }
}
