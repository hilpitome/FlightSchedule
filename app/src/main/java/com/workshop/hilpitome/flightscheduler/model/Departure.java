package com.workshop.hilpitome.flightscheduler.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Departure {
    @SerializedName("AirportCode")
    @Expose
    private String airportCode;

    @SerializedName("ScheduledTimeLocal")
    @Expose
    private ScheduledTimeLocal scheduledTimeLocal;

    public Departure() {
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public ScheduledTimeLocal getScheduledTimeLocal() {
        return scheduledTimeLocal;
    }

    public void setScheduledTimeLocal(ScheduledTimeLocal scheduledTimeLocal) {
        this.scheduledTimeLocal = scheduledTimeLocal;
    }
}
