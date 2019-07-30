package com.workshop.hilpitome.flightscheduler.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Airport {
   private List<AirportInfo> data;

    public Airport() {
    }

    public List<AirportInfo> getData() {
        return data;
    }

    public void setData(List<AirportInfo> data) {
        this.data = data;
    }
}
