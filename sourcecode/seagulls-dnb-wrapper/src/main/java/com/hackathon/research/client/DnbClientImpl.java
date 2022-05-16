package com.hackathon.research.client;

import com.dnb.model.OrderProductResponse;
import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

@Service
public class DnbClientImpl implements DnbClient{

    @Override
    public OrderProductResponse getCompanyProfile(String dunsNum) {
        return getMockResponse();
    }

    public OrderProductResponse getMockResponse() {
        return null;
    }

}
