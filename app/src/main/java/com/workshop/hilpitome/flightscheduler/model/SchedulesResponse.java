package com.workshop.hilpitome.flightscheduler.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SchedulesResponse {
    @SerializedName("ScheduleResource")
    @Expose
    private ScheduleResource scheduleResource;

    public SchedulesResponse(){}

    public ScheduleResource getScheduleResource() {
        return scheduleResource;
    }

    public void setScheduleResource(ScheduleResource scheduleResource) {
        this.scheduleResource = scheduleResource;
    }
}
