package com.workshop.hilpitome.flightscheduler.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ScheduleResource {
    @SerializedName("Schedule")
    @Expose
    private List<FlightInfo> schedule;

    public ScheduleResource() {
    }

    public List<FlightInfo> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<FlightInfo> schedule) {
        this.schedule = schedule;
    }
}
