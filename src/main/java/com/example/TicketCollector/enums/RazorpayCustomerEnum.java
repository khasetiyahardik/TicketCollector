package com.example.TicketCollector.enums;

public enum RazorpayCustomerEnum {

    NAME("name"),
    CONTACT("contact"),
    EMAIL("email"),
    FAILED_EXISTING("fail_existing"),
    NOTES_KEY_1("notes_key_1"),
    NOTES("notes"), NA("NA");

    private String value;

    public String getValue() {
        return value;
    }

    RazorpayCustomerEnum(String value) {
        this.value = value;
    }
}
