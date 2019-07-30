package com.workshop.hilpitome.flightscheduler.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;

import java.util.List;

public class ScheduleResource {
    @SerializedName("Schedule")
    @Expose
    private JSONArray schedule;

    public ScheduleResource() {
    }

    public JSONArray getSchedule() {
        return schedule;
    }

    public void setSchedule(JSONArray schedule) {
        this.schedule = schedule;
    }
}
