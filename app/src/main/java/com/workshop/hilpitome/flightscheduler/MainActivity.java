package com.workshop.hilpitome.flightscheduler;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.workshop.hilpitome.flightscheduler.model.AuthResponse;
import com.workshop.hilpitome.flightscheduler.utils.CommonView;
import com.workshop.hilpitome.flightscheduler.utils.LufthansaServiceGenerator;
import com.workshop.hilpitome.flightscheduler.utils.PrefUtils;

public class MainActivity extends AppCompatActivity implements CommonView, MainActivityView {
    private final String TAG = MainActivity.class.getSimpleName();
    private MainActivityPresenter presenter;
    private PrefUtils prefUtils;
    private ProgressBar progressBar;
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
        presenter.authenticateApplication(clientId, secret);

    }
    // connect the html to their java equivalents
    public void initViews(){
        progressBar = findViewById(R.id.progressBar);
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
    }
}
