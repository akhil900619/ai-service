package com.akhil.aiservice.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.akhil.aiservice.dto.DescriptionRequest;
import com.akhil.aiservice.service.GeminiService;

@RestController
@RequestMapping("/api/ai")
public class DescriptionController {

    private final GeminiService geminiService;

    public DescriptionController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping("/generate-description")
    public Map<String, String> generateDescription(@RequestBody DescriptionRequest request) {
        String description = geminiService.generateProductDescription(
                request.getProductName(),
                request.getCategory(),
                request.getExistingDetails()
        );
        return Map.of("description", description);
    }
}