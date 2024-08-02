package com.mockable.app.services.serviceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mockable.app.controller.AccountController;
import com.mockable.app.model.Account;
import com.mockable.app.payload.response.LoanAccountResponse;
import com.mockable.app.repository.AccountRespository;
import com.mockable.app.services.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    AccountRespository accountRespository;

    @Value("${mockable.api.baseUrl}")
    private  String baseUrl;

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);


    @Override
    public LoanAccountResponse fetchAndSaveLoanAccountData(String loanNumber) throws JsonProcessingException {
        String apiUrl = baseUrl + "/loanaccount/" + loanNumber;

        URI uri = URI.create(apiUrl);

        logger.info("uri : "+ uri);
        RequestEntity<Void> requestEntity = new RequestEntity<>(HttpMethod.GET, uri);
        String responseData = restTemplate.exchange(requestEntity, String.class).getBody();

        logger.info("responseData : " + responseData);
        LoanAccountResponse loanAccountResponse = parseLoanAccountResponse(responseData);

        // Convert LoanAccountResponse to JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String responseDataJson = objectMapper.writeValueAsString(loanAccountResponse);


        Account account = Account.builder().
                accountId(UUID.randomUUID().toString())
                .responseData(responseDataJson)
                .build();

        accountRespository.save(account);

        return loanAccountResponse;

    }

    @Override
    public LoanAccountResponse parseLoanAccountResponse(String responseData) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        // Parse the JSON response
        JsonNode loanAccountNode = objectMapper.readTree(responseData);

        // Extract the values directly
        String loanAccountNumber = loanAccountNode.path("loanAccountNumber").asText();
        JsonNode emiDetail = loanAccountNode.path("emiDetails").elements().next();

        // Extract dueDate and emiAmount
        String dueDate = emiDetail.path("dueDate").asText();
        String emiAmount = emiDetail.path("emiAmount").asText();

        return LoanAccountResponse.builder()
                .loanAccountNumber(loanAccountNumber)
                .dueDate(dueDate)
                .emiAmount(emiAmount)
                .build();
    }
}
