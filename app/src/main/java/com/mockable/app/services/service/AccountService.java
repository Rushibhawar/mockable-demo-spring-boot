package com.mockable.app.services.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mockable.app.payload.response.LoanAccountResponse;

public interface AccountService {

    public LoanAccountResponse fetchAndSaveLoanAccountData(String loanNumber) throws JsonProcessingException;

    public  LoanAccountResponse parseLoanAccountResponse(String responseData) throws JsonProcessingException;

}
