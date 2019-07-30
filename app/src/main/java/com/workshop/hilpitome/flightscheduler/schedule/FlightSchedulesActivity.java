package com.workshop.hilpitome.flightscheduler.schedule;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.workshop.hilpitome.flightscheduler.MainActivityPresenter;
import com.workshop.hilpitome.flightscheduler.MainApplication;
import com.workshop.hilpitome.flightscheduler.R;
import com.workshop.hilpitome.flightscheduler.model.FlightInfo;
import com.workshop.hilpitome.flightscheduler.model.SchedulesResponse;
import com.workshop.hilpitome.flightscheduler.utils.CommonView;
import com.workshop.hilpitome.flightscheduler.utils.LufthansaServiceGenerator;
import com.workshop.hilpitome.flightscheduler.utils.PrefUtils;

public class FlightSchedulesActivity extends AppCompatActivity implements CommonView,
    SchedulesView, SchedulesListAdapter.OnItemClickListener{
    private ProgressBar progressBar;
    private PrefUtils prefUtils;
    private FlightSchedulesPresenter presenter;
    private RecyclerView schedulesRv;
    private SchedulesListAdapter schedulesListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_schedules);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initViews();
        setUpRecyclerView();
        Bundle bundle = getIntent().getExtras();
        String origin = bundle.getString("from");
        String destination = bundle.getString("to");
        prefUtils = new PrefUtils(this);
        LufthansaServiceGenerator lufthansaServiceGenerator = ((MainApplication) getApplication()).getLuftNetworkService();
        presenter = new FlightSchedulesPresenter(lufthansaServiceGenerator, this, this);
        presenter.fetchSchedules("Bearer "+prefUtils.getKeyAccessToken(), origin, destination);
    }

    private void initViews() {
        progressBar = findViewById(R.id.progressBar);

    }

    @Override
    public void showWait() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void removeWait() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onFailure(int code, String message, String description) {
        Toast.makeText(this, description, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setSchedulesResponse(SchedulesResponse response) {
        Gson gson = new Gson();
        Log.e("schedules-response",gson.toJson(response));
        JsonArray scheduleList = null;
        if(response.getScheduleResource().getSchedule().isJsonArray()){
            scheduleList = response.getScheduleResource().getSchedule().getAsJsonArray();
        } else {
            scheduleList.add(response.getScheduleResource().getSchedule());
        }
        for (JsonElement el:
             scheduleList) {


        }
    }

    private void setUpRecyclerView() {
        schedulesRv = findViewById(R.id.flights_rv);
        // Create a layout manager for recycler view.
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        schedulesRv.setLayoutManager(layoutManager);

        // Create adapter that will power this recycler view.
        schedulesListAdapter = new SchedulesListAdapter(this, this);

        schedulesRv.setAdapter(schedulesListAdapter);
    }

    @Override
    public void onItemClick(FlightInfo flightInfo) {
        //show maps
    }
}
