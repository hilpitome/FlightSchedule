package com.workshop.hilpitome.flightscheduler.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AirportsResponse {
    @SerializedName("AirportResource")
    @Expose
    private AirportResource airportResource;

    public AirportsResponse(){}

    public AirportResource getAirportResource() {
        return airportResource;
    }

    public void setAirportResource(AirportResource airportResource) {
        this.airportResource = airportResource;
    }
}
