package com.workshop.hilpitome.flightscheduler.model;

import com.google.gson.JsonElement;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;

import java.util.List;

public class ScheduleResource {
    @SerializedName("Schedule")
    @Expose
    private JsonElement schedule;

    public ScheduleResource() {
    }

    public JsonElement getSchedule() {
        return schedule;
    }

    public void setSchedule(JsonElement schedule) {
        this.schedule = schedule;
    }
}
