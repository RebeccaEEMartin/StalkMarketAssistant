package com.example.sam.models;

import java.time.DayOfWeek;

public class BuyingPrice {

    private DayOfWeek day;
    private TimeOfDay time;
    private int price;

    public BuyingPrice(DayOfWeek day, TimeOfDay time, int price) {
        this.day = day;
        this.time = time;
        this.price = price;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public TimeOfDay getTime() {
        return time;
    }

    public int getPrice() {
        return price;
    }
}