package com.example.TicketCollector.enums;

public enum BookingTypeEnums {

    BUS("Bus"),
    PLANE("Plane"),
    TRAIN("Train");

    BookingTypeEnums(String btValue) {
        this.btValue = btValue;
    }

    private String btValue;

    public String getBtValue() {
        return btValue;
    }
}
