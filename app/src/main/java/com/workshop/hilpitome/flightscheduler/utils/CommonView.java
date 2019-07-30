package com.workshop.hilpitome.flightscheduler.utils;

public interface CommonView {

    void showWait();

    void removeWait();

    void onFailure(int code, String message, String description);

}