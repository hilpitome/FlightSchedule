package com.workshop.hilpitome.flightscheduler;
import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;;

import com.jakewharton.threetenabp.AndroidThreeTen;
import com.workshop.hilpitome.flightscheduler.model.AuthResponse;
import com.workshop.hilpitome.flightscheduler.utils.Constants;
import com.workshop.hilpitome.flightscheduler.utils.LufthansaServiceGenerator;
import com.workshop.hilpitome.flightscheduler.utils.PrefUtils;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.io.IOException;

import retrofit2.Response;

public class MainApplication extends Application {
    private LufthansaServiceGenerator lufthansaServiceGenerator;
    private PrefUtils prefUtils;
    @Override
    public void onCreate() {
        super.onCreate();
        AndroidThreeTen.init(this);
        lufthansaServiceGenerator = new LufthansaServiceGenerator();
        prefUtils = new PrefUtils(this);
        // check if app is not logged in and get API tokens
        if(!prefUtils.isLoggedIn()){
            authenticateApp();
        }


    }

    public LufthansaServiceGenerator getLuftNetworkService(){
        return  lufthansaServiceGenerator;
    }

    public void authenticateApp(){
      new AuthenticateTask().execute();
    }

    public class AuthenticateTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            String clientId = getString(R.string.lufthansa_app_id);
            String secret = getString(R.string.lufthansa_app_secret);
            String grantType = "client_credentials";
            try {
                Response<AuthResponse> authresponse =lufthansaServiceGenerator.getAPI()
                        .login(secret,grantType,clientId).execute();
                AuthResponse response = authresponse.body();

                prefUtils.setKeyTokenType(response.getTokenType());
                prefUtils.setKeyAccessToken(response.getAccessToken());
                int expiryPeriod = response.getExpiresIn();

                LocalDateTime now = LocalDateTime.now();
                Log.e("expiry", String.valueOf(now));
                LocalDateTime actualExpiry = now.plusSeconds((long)expiryPeriod);
                Log.e("expiry date", String.valueOf(actualExpiry));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
                String expiersAt = actualExpiry.format(formatter);
                prefUtils.setKeyExpiryTime(expiersAt);
                if(!prefUtils.isLoggedIn())
                    prefUtils.setKeyIsLoggedIn(true); //

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
