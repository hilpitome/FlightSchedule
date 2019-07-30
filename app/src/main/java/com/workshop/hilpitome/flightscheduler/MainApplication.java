package com.workshop.hilpitome.flightscheduler;
import android.app.Application;;

import com.workshop.hilpitome.flightscheduler.utils.LufthansaServiceGenerator;

public class MainApplication extends Application {
    private LufthansaServiceGenerator lufthansaServiceGenerator;
    @Override
    public void onCreate() {
        super.onCreate();
        lufthansaServiceGenerator = new LufthansaServiceGenerator();

    }

    public LufthansaServiceGenerator getLuftNetworkService(){
        return  lufthansaServiceGenerator;
    }
}
