package com.workshop.hilpitome.flightscheduler.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Airports {
    @SerializedName("Airport")
    @Expose
    private List<AirportInfo> airport; // airpot array with one or more airports

    public Airports(){}

    public List<AirportInfo> getAirport() {
        return airport;
    }

    public void setAirport(List<AirportInfo> airport) {
        this.airport = airport;
    }
}
