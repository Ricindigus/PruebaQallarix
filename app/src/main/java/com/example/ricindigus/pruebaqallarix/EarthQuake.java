package com.example.ricindigus.pruebaqallarix;

public class EarthQuake {
    private Double magnitude;
    private String location;
    private Long timeInMilliseconds;
    private String url;


    public EarthQuake(Double magnitude, String location, Long timeInMilliseconds, String url) {
        this.magnitude = magnitude;
        this.location = location;
        this.timeInMilliseconds = timeInMilliseconds;
        this.url = url;
    }

    public Double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(Double magnitude) {
        this.magnitude = magnitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getTimeInMilliseconds() {
        return timeInMilliseconds;
    }

    public void setTimeInMilliseconds(Long timeInMilliseconds) {
        this.timeInMilliseconds = timeInMilliseconds;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
