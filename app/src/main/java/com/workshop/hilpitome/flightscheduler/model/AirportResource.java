package com.workshop.hilpitome.flightscheduler.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AirportResource {
    @SerializedName("Airports")
    @Expose
    private Airports airports;

    public AirportResource(){}

    public Airports getAirports() {
        return airports;
    }

    public void setAirports(Airports airports) {
        this.airports = airports;
    }
}
