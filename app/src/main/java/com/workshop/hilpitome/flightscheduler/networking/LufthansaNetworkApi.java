package com.workshop.hilpitome.flightscheduler.networking;

import com.workshop.hilpitome.flightscheduler.model.AirportsResponse;
import com.workshop.hilpitome.flightscheduler.model.AuthResponse;
import com.workshop.hilpitome.flightscheduler.model.LoginData;
import com.workshop.hilpitome.flightscheduler.model.SchedulesResponse;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface LufthansaNetworkApi {

    @FormUrlEncoded
    @POST("oauth/token")
    Call<AuthResponse> login(@Field("client_secret") String secret,
                             @Field("grant_type") String grantType,
                             @Field("client_id") String clientId);


    /**
     * @return Response<AirportResource> of airports
     */
    @Headers("Accept: application/json")
    @GET("mds-references/airports?lang=EN&limit=100&LHoperated=true")
    Observable<Response<AirportsResponse>> fetchAirports(@Header("Authorization") String accessToken);


    @Headers("Accept: application/json")
    @GET("operations/schedules/{origin}/{destination}/{fromDateTime}")
    Observable<Response<SchedulesResponse>> fetchSchedules(@Header("Authorization") String accessToken,
                                                           @Path("origin") String origin,
                                                           @Path("destination") String destination,
                                                           @Path("fromDateTime") String fromDateTime);
}

