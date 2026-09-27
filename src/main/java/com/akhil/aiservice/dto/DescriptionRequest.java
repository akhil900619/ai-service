package com.akhil.aiservice.dto;

public class DescriptionRequest {
    private String productName;
    private String category;
    private String existingDetails;

    public DescriptionRequest() {}

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getExistingDetails() { return existingDetails; }
    public void setExistingDetails(String existingDetails) { this.existingDetails = existingDetails; }
}