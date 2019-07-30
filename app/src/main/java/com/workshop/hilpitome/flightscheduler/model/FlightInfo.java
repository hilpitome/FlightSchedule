package com.workshop.hilpitome.flightscheduler.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FlightInfo {

    @SerializedName("TotalJourney")
    @Expose
    private TotalJourney totalJourney;
    @SerializedName("Flight")
    @Expose
    private Flight flight;

    public FlightInfo() {
    }

    public TotalJourney getTotalJourney() {
        return totalJourney;
    }

    public void setTotalJourney(TotalJourney totalJourney) {
        this.totalJourney = totalJourney;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }
}
