package com.example.TicketCollector.enums;

public enum RazorpayPaymentEnum {

    AMOUNT("amount"),
    CURRENCY("currency"),
    ACCEPT_PARTIAL("accept_partial"),
    FIRST_MIN_PARTIAL_AMOUNT("first_min_partial_amount"),
    EXPIRE_BY("expire_by"),
    REFERENCE_ID("reference_id"),
    DESCRIPTION("description"),
    NAME("name"),
    CONTACT("contact"),
    EMAIL("email"),
    CUSTOMER("customer"),
    SMS("sms"),

    NOTIFY("notify"),
    REMINDER_ENABLE("reminder_enable"),
    POLICY_NAME("policy_name"),
    TICKET_BOOKING("Ticket booking"),
    NOTES("notes"),
    CALLBACK_URL("callback_url"),
    CALLBACK_METHOD("callback_method");
    private String value;

    public String getValue() {
        return value;
    }

    RazorpayPaymentEnum(String value) {
        this.value = value;
    }
}
