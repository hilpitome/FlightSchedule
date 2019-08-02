package com.workshop.hilpitome.flightscheduler.schedule;

import android.util.Log;

import com.google.gson.Gson;
import com.workshop.hilpitome.flightscheduler.MainActivityView;
import com.workshop.hilpitome.flightscheduler.model.AirportsResponse;
import com.workshop.hilpitome.flightscheduler.model.SchedulesResponse;
import com.workshop.hilpitome.flightscheduler.utils.CommonView;
import com.workshop.hilpitome.flightscheduler.utils.LufthansaServiceGenerator;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class FlightSchedulesPresenter {

    private final LufthansaServiceGenerator service;
    private final CommonView commonView;
    private final SchedulesView schedulesView;
    private CompositeSubscription subscriptions;

    public FlightSchedulesPresenter(LufthansaServiceGenerator service,
                                    CommonView commonView, SchedulesView schedulesView){
        this.service = service;
        this.commonView = commonView;
        this.schedulesView = schedulesView;
        this.subscriptions = new CompositeSubscription();
    }

    public void fetchSchedules(String accessToken, String origin, String destination){

        LocalDate tomorrow = LocalDate.now().plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd");
        String fromDateTime = formatter.format(tomorrow);
        commonView.showWait();
        Observable<Response<SchedulesResponse>> observable = service.getAPI()
                .fetchSchedules(accessToken, origin, destination, fromDateTime);
        Subscription subscription = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response <SchedulesResponse>>() {
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
                    public void onNext(Response<SchedulesResponse> response) {
                        Gson gson = new Gson();
                        Log.e("resp-code", String.valueOf(response.code()));
                        Log.e("schedules", gson.toJson(response));
                        commonView.removeWait();
                        if (response.isSuccessful()) {
                            System.out.println("schedules fetched successfully");
                            schedulesView.setSchedulesResponse(response.body());
                        } else {
                            commonView.onFailure(response.code(), "Schedule not available", "Schedule not available");
                        }
                    }
                });

        subscriptions.add(subscription);

    }
}
