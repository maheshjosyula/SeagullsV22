package com.hackathon.research.model;

import java.util.ArrayList;
import java.util.List;

public class CompanyProfile {
    String companyName;

    Boolean minorityOwnedIndicator;

    Boolean femaleOwnedIndicator;

    String OwnershipEthnicity;

    public Boolean getMinorityOwnedIndicator() {
        return minorityOwnedIndicator;
    }

    public void setMinorityOwnedIndicator(Boolean minorityOwnedIndicator) {
        this.minorityOwnedIndicator = minorityOwnedIndicator;
    }

    public Boolean getFemaleOwnedIndicator() {
        return femaleOwnedIndicator;
    }

    public void setFemaleOwnedIndicator(Boolean femaleOwnedIndicator) {
        this.femaleOwnedIndicator = femaleOwnedIndicator;
    }

    public String getOwnershipEthnicity() {
        return OwnershipEthnicity;
    }

    public void setOwnershipEthnicity(String ownershipEthnicity) {
        OwnershipEthnicity = ownershipEthnicity;
    }

    List<Owner> Owners=new ArrayList<>();

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<Owner> getOwners() {
        return Owners;
    }

    public void setOwners(List<Owner> owners) {
        Owners = owners;
    }
}
