package com.workshop.hilpitome.flightscheduler;

import android.util.Log;

import com.google.gson.Gson;
import com.workshop.hilpitome.flightscheduler.model.AirportResource;
import com.workshop.hilpitome.flightscheduler.utils.CommonView;
import com.workshop.hilpitome.flightscheduler.utils.LufthansaServiceGenerator;
import com.workshop.hilpitome.flightscheduler.model.AuthResponse;

import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivityPresenter {

    private final LufthansaServiceGenerator service;
    private final CommonView commonView;
    private final MainActivityView mainActivityView;
    private CompositeSubscription subscriptions;

    MainActivityPresenter(LufthansaServiceGenerator service, CommonView commonView, MainActivityView mainActivityView){
        this.service = service;
        this.commonView = commonView;
        this.mainActivityView = mainActivityView;
        subscriptions = new CompositeSubscription();
    }

    public void authenticateApplication(String clientId,String client_secret){
        commonView.showWait();
        String grantType = "client_credentials";
        Observable<Response<AuthResponse>> observable = service.getAPI().login(client_secret,
                grantType, clientId);
        Subscription subscription = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response <AuthResponse>>() {
                    @Override
                    public void onCompleted() {

                    }

                    //On login failed
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        commonView.removeWait();
                        if (e.getMessage().contains("Failed to connect to")) {
                            commonView.onFailure(503, "Service Unavailable", "Check your internet connection then try again.");
                        }
                        if (e.getMessage().contains("Unable to resolve host")) {
                            commonView.onFailure(503, "Service Unavailable", "Check your internet connection then try again.");
                        }

                        System.out.println("Something wrong here " + e.getMessage());
                    }

                    //On authentication successful
                    @Override
                    public void onNext(Response<AuthResponse> response) {
                        commonView.removeWait();
                        if (response.isSuccessful()) {
                            mainActivityView.setLoginResponse(response.body());
                            System.out.println("login completed successfully");
                        }
                    }
                });

        subscriptions.add(subscription);

    }

    public void fetchAirports(String accessToken){
        commonView.showWait();
        Observable<Response<AirportResource>> observable = service.getAPI().fetchAirports(accessToken);
        Subscription subscription = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response <AirportResource>>() {
                    @Override
                    public void onCompleted() {

                    }

                    //On login failed
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        commonView.removeWait();
                        if (e.getMessage().contains("Failed to connect to")) {
                            commonView.onFailure(503, "Service Unavailable", "Check your internet connection then try again.");
                        }
                        if (e.getMessage().contains("Unable to resolve host")) {
                            commonView.onFailure(503, "Service Unavailable", "Check your internet connection then try again.");
                        }

                        System.out.println("Something wrong here " + e.getMessage());
                    }

                    //On authentication successful
                    @Override
                    public void onNext(Response<AirportResource> response) {
                        Gson gson = new Gson();
                        Log.e("airports", gson.toJson(response));
                        commonView.removeWait();
                        if (response.isSuccessful()) {
                            System.out.println("airports fetched successfully");
                        }
                    }
                });

        subscriptions.add(subscription);

    }

}
