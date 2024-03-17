package com.example.newMock.Controller;

import com.example.newMock.Model.RequestDTO;
import com.example.newMock.Model.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Random;

@RestController
public class MainController {

    private Logger log = LoggerFactory.getLogger(MainController.class);

    ObjectMapper mapper = new ObjectMapper();

    @PostMapping(
        value = "/info/postBalances",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Object postBalances(@RequestBody RequestDTO requestDTO){
        try {
            String clientId = requestDTO.getClientId();
            String rqUID = requestDTO.getReqUID();

            char firstDigit = clientId.charAt(0);

            BigDecimal maxLimit;
            Random random = new Random();
            int randomBalance;
            String currency;


            if(firstDigit == '8'){
                randomBalance = random.nextInt(2000);
                currency = "US";
                maxLimit = new BigDecimal(2000);
            }else if(firstDigit == '9'){
                randomBalance = random.nextInt(1000);
                currency = "EU";
                maxLimit = new BigDecimal(1000);
            }else{
                randomBalance = random.nextInt(10000);
                currency = "RUB";
                maxLimit = new BigDecimal(10000);
            }

            ResponseDTO responseDTO = new ResponseDTO();

            responseDTO.setRqUID(rqUID);
            responseDTO.setClientId(clientId);
            responseDTO.setAccount(requestDTO.getAccount());
            responseDTO.setCurrency(currency);
            responseDTO.setBalance(new BigDecimal(randomBalance));
            responseDTO.setMaxLimit(maxLimit);

            log.error("\n************* RequestDTO *************\n" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDTO));
            log.error("\n************* ResponseDTO *************\n" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseDTO));

            return responseDTO;

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
