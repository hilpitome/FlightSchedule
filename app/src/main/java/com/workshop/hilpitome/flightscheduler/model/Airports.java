package com.workshop.hilpitome.flightscheduler.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Airports {
    @SerializedName("Airport")
    @Expose
    private Airport airport; // airpot array with one or more airports

    public Airports(){}

    public Airport getAirport() {
        return airport;
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
    }
}
