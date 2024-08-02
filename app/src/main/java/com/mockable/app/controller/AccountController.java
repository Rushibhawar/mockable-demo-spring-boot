package com.mockable.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mockable.app.payload.response.APIResponse;
import com.mockable.app.payload.response.LoanAccountResponse;
import com.mockable.app.services.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/accounts")
public class AccountController {

    @Autowired
    AccountService accountService;

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);


    @GetMapping("")
    public ResponseEntity<APIResponse<Object>> getLoanData(@RequestParam(defaultValue = "1", name = "number") String number) throws JsonProcessingException {
        LoanAccountResponse loanAccountResponse = accountService.fetchAndSaveLoanAccountData(number);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new APIResponse<Object>(HttpStatus.OK, true,loanAccountResponse,"Success" ));
    }
}
