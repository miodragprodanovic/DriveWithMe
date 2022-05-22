package com.example.DriveWithMe.model;

import javax.persistence.Embeddable;

@Embeddable
public class Price {
    private double worth;
    private String currency;

    public Price(double worth, String currency) {
        this.worth = worth;
        this.currency = currency;
    }

    public Price() {}

    private void Validate() {
        if (this.worth < 0) {
            throw new IllegalStateException("Price worth must be greater or equal to 0!");
        } else if (this.currency.equals("")) {
            throw new IllegalStateException("Currency should not be empty!");
        }
    }

    public double getWorth() {
        return worth;
    }

    public void setWorth(double worth) {
        this.worth = worth;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
