package com.example.DriveWithMe.model;

import javax.persistence.Embeddable;

@Embeddable
public class Location {
    private String country;
    private String city;
    private String address;

    public Location() {}

    public Location(String country, String city, String address) {
        this.country = country;
        this.city = city;
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean isSameLocation(Location location) {
        return this.getCountry().equals(location.getCountry()) && this.getCity().equals(location.getCity()) && this.getAddress().equals(location.getAddress());
    }
}
