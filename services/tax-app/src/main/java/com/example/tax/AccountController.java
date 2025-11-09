package com.example.tax;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    // fake in-memory balances
    private final Map<String, Integer> balances = new HashMap<>();

    public AccountController() {
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
