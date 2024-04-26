package com.techelevator.service;

import com.techelevator.exception.DaoException;
import com.techelevator.model.TaxResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
@Component
public class TaxService {
    public static final String API_BASE_URL = "https://teapi.netlify.app/api/statetax?state=";
    private RestTemplate restTemplate = new RestTemplate();

    public BigDecimal getTaxRate(String stateCode){

        String api = API_BASE_URL + stateCode.toUpperCase();


        try {
            TaxResponseDto taxResponseDto = restTemplate.getForObject(api, TaxResponseDto.class);
            return taxResponseDto.getSaleTax().divide(new BigDecimal("100"));

        } catch (HttpClientErrorException e){

            if(e.getRawStatusCode() == 404){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tax rate not found");
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

        }


    }

}
