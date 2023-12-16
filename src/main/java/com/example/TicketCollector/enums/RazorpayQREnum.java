package com.example.TicketCollector.enums;

public enum RazorpayQREnum {

    TYPE("type"),
    NAME("name"),
    USAGE("usage"),
    FIXED_AMOUNT("fixed_amount"),
    PAYMENT_AMOUNT("payment_amount"),

    DESCRIPTION("description"),
    CUSTOMER_ID("customer_id"),
    CLOSED_BY("close_by"),
    NOTES_KEY_1("notes_key_1"),
    NOTES("notes"),
    UPI_QR("upi_qr"),
    SINGLE_USE("single_use"),

    NA("NA");


    private String value;

    public String getValue() {
        return value;
    }

    RazorpayQREnum(String value) {
        this.value = value;
    }
}
