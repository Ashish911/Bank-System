package com.ashish.accounts.service.client;

import com.ashish.accounts.dto.LoansDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoansFallback implements LoansFeignClient {
    @Override
    public ResponseEntity<LoansDto> fetchLoansDetails(String correlationId, String mobileNumber) {
        return null;
    }
}
