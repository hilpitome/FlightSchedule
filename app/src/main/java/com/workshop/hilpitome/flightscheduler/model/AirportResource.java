package com.workshop.hilpitome.flightscheduler.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AirportResource {
    @SerializedName("Airports")
    @Expose
    private Airports airports;
}
