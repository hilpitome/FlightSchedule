package com.workshop.hilpitome.flightscheduler.model;

import android.print.PrinterId;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * actual information about an airport
 */
public class AirportInfo {
    @SerializedName("AirportCode")
    @Expose
    private String airportCode;
    @SerializedName("Position")
    @Expose
    private Position position;
    @SerializedName("CityCode")
    @Expose
    private String cityCode;
    @SerializedName("CountryCode")
    @Expose
    private String countryCode;
    @SerializedName("Names")
    @Expose
    private Names names;
    @SerializedName("UtcOffset")
    @Expose
    private String utcOffset;
    @SerializedName("TimeZoneId")
    @Expose
    private String timezoneId;

    public AirportInfo(){}

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Names getNames() {
        return names;
    }

    public void setNames(Names names) {
        this.names = names;
    }

    public String getUtcOffset() {
        return utcOffset;
    }

    public void setUtcOffset(String utcOffset) {
        this.utcOffset = utcOffset;
    }

    public String getTimezoneId() {
        return timezoneId;
    }

    public void setTimezoneId(String timezoneId) {
        this.timezoneId = timezoneId;
    }
}
