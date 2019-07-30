package com.workshop.hilpitome.flightscheduler.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Arrival {
    @SerializedName("AirportCode")
    @Expose
    private String airportCode;

    @SerializedName("ScheduledTimeLocal")
    @Expose
    private ScheduledTimeLocal scheduledTimeLocal;
}