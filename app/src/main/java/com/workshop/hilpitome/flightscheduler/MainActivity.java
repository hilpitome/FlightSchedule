package com.workshop.hilpitome.flightscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.workshop.hilpitome.flightscheduler.adapters.FromSpinnerAdapter;
import com.workshop.hilpitome.flightscheduler.adapters.ToSpinnerAdapter;
import com.workshop.hilpitome.flightscheduler.model.AirportInfo;
import com.workshop.hilpitome.flightscheduler.model.AirportsResponse;
import com.workshop.hilpitome.flightscheduler.model.AuthResponse;
import com.workshop.hilpitome.flightscheduler.model.Name;
import com.workshop.hilpitome.flightscheduler.model.Names;
import com.workshop.hilpitome.flightscheduler.schedule.FlightSchedulesActivity;
import com.workshop.hilpitome.flightscheduler.utils.CommonView;
import com.workshop.hilpitome.flightscheduler.utils.LufthansaServiceGenerator;
import com.workshop.hilpitome.flightscheduler.utils.PrefUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity implements CommonView,
        MainActivityView, View.OnClickListener {
    private final String TAG = MainActivity.class.getSimpleName();
    private MainActivityPresenter presenter;
    private PrefUtils prefUtils;
    private ProgressBar progressBar;
    private List<AirportInfo> airportInfoList;
    private FromSpinnerAdapter fromSpinnerAdapter;
    private ToSpinnerAdapter toSpinnerAdapter;
    private AppCompatSpinner fromSpinner;
    private AppCompatSpinner toSpinner;
    private Button fetchSchedulesBtn;
    private String mFromAirportCode;
    private String mToAirportCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();
        prefUtils = new PrefUtils(this);
        LufthansaServiceGenerator lufthansaServiceGenerator = ((MainApplication) getApplication()).getLuftNetworkService();
        presenter = new MainActivityPresenter(lufthansaServiceGenerator, this, this);
        String clientId = getString(R.string.lufthansa_app_id);
        String secret = getString(R.string.lufthansa_app_secret);
        setSpinners();
        presenter.authenticateApplication(clientId, secret);

    }
    // connect the html to their java equivalents
    public void initViews(){
        progressBar = findViewById(R.id.progress);
        fromSpinner = findViewById(R.id.from_spinner);
        toSpinner = findViewById(R.id.to_spinner);
        fetchSchedulesBtn = findViewById(R.id.fetch_schedules_btn);
        fetchSchedulesBtn.setOnClickListener(this);
    }

    private void setSpinners() {
        fromSpinnerAdapter = new FromSpinnerAdapter(getApplicationContext());
        fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setmFromAirportCode(fromSpinnerAdapter.getItem(i).getAirportCode());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        toSpinnerAdapter = new ToSpinnerAdapter(getApplicationContext());
        toSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setmToAirportCode(toSpinnerAdapter.getItem(i).getAirportCode());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    // show loading when data is being fetched
    @Override
    public void showWait() {
        progressBar.setVisibility(View.VISIBLE);
    }
    // remove loading after the data has been fetched
    @Override
    public void removeWait() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onFailure(int code, String message, String description) {
        Toast.makeText(this, description, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setLoginResponse(AuthResponse response) {
        prefUtils.setKeyTokenType(response.getTokenType());
        prefUtils.setKeyAccessToken(response.getAccessToken());
        prefUtils.setKeyIsLoggedIn();
        presenter.fetchAirports("Bearer "+response.getAccessToken());
    }

    @Override
    public void setFetchAirportsResponse(AirportsResponse response) {
        this.airportInfoList = response.getAirportResource().getAirports().getAirport();
        // set FromSpinner
        fromSpinnerAdapter.clear();
        AirportInfo promptFrom = new AirportInfo();
        promptFrom.setAirportCode("N/A");
        Name name = new Name();
        name.set$("Select Departure");
        Names names = new Names();
        names.setName(name);
        promptFrom.setNames(names);
        fromSpinnerAdapter.addPrompt(promptFrom);
        fromSpinnerAdapter.addItems(airportInfoList);
        fromSpinner.setAdapter(fromSpinnerAdapter);
        // set toSpinner
        toSpinnerAdapter.clear();
        AirportInfo promptTo = new AirportInfo();
        promptTo.setAirportCode("N/A");
        Name nameTo = new Name();
        nameTo.set$("Select Destination");
        Names namesTo = new Names();
        namesTo.setName(nameTo);
        promptTo.setNames(namesTo);
        toSpinnerAdapter.addPrompt(promptTo);
        toSpinnerAdapter.addItems(airportInfoList);
        toSpinner.setAdapter(toSpinnerAdapter);

    }

    private void setmFromAirportCode(String code){
        Log.e("from", code);
        this.mFromAirportCode = code;
    }

    private void setmToAirportCode(String code){
        Log.e("to", code);
        this.mToAirportCode = code;
    }

    private void fetchSchedules(){
        if(this.mFromAirportCode.equals("N/A") || this.mToAirportCode.equals("N/A")){
            Toast.makeText(this, "Please select departure and destination", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(MainActivity.this, FlightSchedulesActivity.class);
            intent.putExtra("from", this.mFromAirportCode);
            intent.putExtra("to", this.mToAirportCode);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.fetch_schedules_btn:
                fetchSchedules();
                break;
        }
    }
}
