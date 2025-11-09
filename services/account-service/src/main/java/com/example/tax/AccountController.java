package com.example.tax;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    // fake in-memory balances
    private final Map<String, Integer> balances = new HashMap<>();
    private final RestTemplate restTemplate;

    // service DNS name inside Kubernetes 
    private static final String TAXPAYER_URL = "http://taxpayer-service:8081/taxpayers/";

    public AccountController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        balances.put("1001", 500);
        balances.put("1002", 1200);
    }

    @GetMapping("/{id}")
    public Map<String, Object> getAccount(@PathVariable String id) {
        return Map.of(
                "id", id,
                "balance", balances.getOrDefault(id, 0)
        );
    }

    @GetMapping("/{id}/details")
    public Map<String, Object> getAccountDetails(@PathVariable String id) {
        Map<String, Object> taxpayer = restTemplate.getForObject(
            TAXPAYER_URL + id,
            Map.class
        );

        int balance = balances.getOrDefault(id, 0);

        return Map.of(
            "id", id,
            "balance", balance,
            "taxpayer", taxpayer
        );
    }

    @PostMapping("/{id}/adjust")
    public Map<String, Object> adjustAccount(@PathVariable String id,
                                             @RequestParam int amount) {
        int current = balances.getOrDefault(id, 0);
        int updated = current + amount;
        balances.put(id, updated);
        return Map.of(
                "id", id,
                "oldBalance", current,
                "newBalance", updated
        );
    }
}
