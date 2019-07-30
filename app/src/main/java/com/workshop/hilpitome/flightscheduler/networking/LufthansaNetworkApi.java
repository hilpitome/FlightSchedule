package com.workshop.hilpitome.flightscheduler.networking;

import com.workshop.hilpitome.flightscheduler.model.AirportsResponse;
import com.workshop.hilpitome.flightscheduler.model.AuthResponse;
import com.workshop.hilpitome.flightscheduler.model.LoginData;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

public interface LufthansaNetworkApi {

    @FormUrlEncoded
    @POST("oauth/token")
    Observable<Response<AuthResponse>> login(@Field("client_secret") String secret,
                                             @Field("grant_type") String grantType,
                                             @Field("client_id") String clientId);


    /**
     * @return Response<AirportResource> of airports
     */
    @Headers("Accept: application/json")
    @GET("mds-references/airports?lang=EN")
    Observable<Response<AirportsResponse>> fetchAirports(@Header("Authorization") String accessToken);


//    @Headers("Accept: application/json")
//    @GET("operations/schedules/{origin}/{destination}/{fromDateTime}?directFlights=true]")
}

