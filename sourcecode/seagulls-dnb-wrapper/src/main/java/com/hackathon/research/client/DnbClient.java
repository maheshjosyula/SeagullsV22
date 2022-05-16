package com.hackathon.research.client;

import com.dnb.model.OrderProductResponse;

public interface DnbClient {
    OrderProductResponse getCompanyProfile(String dunsNum);
}
