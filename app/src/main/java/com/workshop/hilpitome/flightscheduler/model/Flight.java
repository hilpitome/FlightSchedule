package com.workshop.hilpitome.flightscheduler.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Flight {
    @SerializedName("Departure")
    @Expose
    private Departure departure;
    @SerializedName("Arrival")
    @Expose
    private Arrival arrival;

}
