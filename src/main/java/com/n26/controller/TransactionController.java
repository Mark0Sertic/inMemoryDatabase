package com.n26.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.n26.error.NotSupportedTimeFormat;
import com.n26.error.TransactionIsInFuture;
import com.n26.error.TransactionOutOfInterval;
import com.n26.model.Transaction;
import com.n26.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * Handlers for different exceptions which can occur
     * @return ResponseEntity<Object> which is status code of error
     */

    @ExceptionHandler({ TransactionIsInFuture.class, NotSupportedTimeFormat.class, JsonMappingException.class, IOException.class})
    public ResponseEntity<Object> handleExceptionUnprocesableEntity() {
        return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({TransactionOutOfInterval.class})
    public ResponseEntity<Object> handleExceptionNoContent() {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler({JsonParseException.class})
    public ResponseEntity<Object> handleExceptionBadRequest() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    private boolean isValidJson(ObjectMapper objectMapper, String payload) {

        try {
            JsonNode actualObj = objectMapper.readTree(payload);
            return actualObj.isObject();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * REST point for posting transactions
     * Input param is String only so that we can cacth exceptions of invalid json and unprocessable one
     * To determine if json is invalid we try to par string and test if parsed string is object
     * @param payload
     * @return
     * @throws TransactionIsInFuture
     * @throws IOException
     * @throws TransactionOutOfInterval
     * @throws NotSupportedTimeFormat
     */
    @PostMapping("/transactions")
    public ResponseEntity<?> addTransaction(@RequestBody String payload) throws TransactionIsInFuture, IOException, TransactionOutOfInterval, NotSupportedTimeFormat {
        ObjectMapper objectMapper = new ObjectMapper();

        if(!isValidJson(objectMapper,payload)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Transaction transaction = objectMapper.readValue(payload, Transaction.class);
        this.transactionService.addTransaction(transaction);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    /**
     * REST point for deleting all transactions
     * @return
     */
    @DeleteMapping ("/transactions")
    public ResponseEntity<?> deleteTransactions(){
        this.transactionService.deleteTransactions();
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
