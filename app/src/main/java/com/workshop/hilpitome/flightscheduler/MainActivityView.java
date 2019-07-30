package com.workshop.hilpitome.flightscheduler;

import com.workshop.hilpitome.flightscheduler.model.AirportsResponse;
import com.workshop.hilpitome.flightscheduler.model.AuthResponse;

public interface MainActivityView {
    void setLoginResponse(AuthResponse response);
    void setFetchAirportsResponse(AirportsResponse response);
}
