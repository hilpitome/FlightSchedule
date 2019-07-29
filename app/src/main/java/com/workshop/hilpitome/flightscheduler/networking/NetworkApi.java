package com.workshop.hilpitome.flightscheduler.networking;

import com.workshop.hilpitome.flightscheduler.model.AuthResponse;
import com.workshop.hilpitome.flightscheduler.model.LoginData;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

public interface NetworkApi {

    @Headers("Content-Type: application/json")
    @POST("auth/login")
    Observable<Response<AuthResponse>> login(@Body LoginData data);

}
