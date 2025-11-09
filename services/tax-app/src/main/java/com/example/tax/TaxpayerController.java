package com.example.tax;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/taxpayers")
public class TaxpayerController {

    // pretend this is from a DB
    private final Map<String, String> taxpayers = Map.of(
            "1001", "John Doe",
            "1002", "Jane Smith"
    );

    @GetMapping("/{id}")
    public Map<String, String> getTaxpayer(@PathVariable String id) {
        String name = taxpayers.getOrDefault(id, "Unknown");
        return Map.of(
                "id", id,
                "name", name
        );
    }
}
