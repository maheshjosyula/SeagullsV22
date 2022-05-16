package com.hackathon.research.service;

import com.hackathon.research.model.CompanyProfile;
import com.hackathon.research.model.Owner;

import java.util.List;

public interface DnbService {
    CompanyProfile getOwners(String dunsNum);
}
