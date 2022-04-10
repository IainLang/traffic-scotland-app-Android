package com.example.mpd_coursework_iainlang;
//Iain Lang - s1822179

import static android.content.ContentValues.TAG;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.Spinner;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    URL urlSource1 = new URL("https://trafficscotland.org/rss/feeds/currentincidents.aspx");
    URL urlSource2 = new URL("https://trafficscotland.org/rss/feeds/roadworks.aspx");
    URL urlSource3 = new URL("https://trafficscotland.org/rss/feeds/plannedroadworks.aspx");

    Button button;
    ArrayList<String> titles;
    ArrayList<String> descriptions;
    ArrayList<String> links;
    SearchView searchView;
    Spinner spinner;
    URL url;

    public static Boolean isCurrentIncidents;
    public static Boolean isPlannedRoadworks;
    public static Boolean isRoadworks;

    public MainActivity() throws MalformedURLException {
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        titles = new ArrayList<String>();
        descriptions = new ArrayList<String>();
        links = new ArrayList<String>();
        button = (Button) findViewById(R.id.HomeBtn);

        spinner = (Spinner) findViewById(R.id.spinnerView);
        searchView = (SearchView) findViewById(R.id.searchBar);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.spinner));
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Intent theIntent;
                switch (i) {

                    case 0:

                        break;
                    case 1: {
                        isCurrentIncidents = true;
                        isPlannedRoadworks = false;
                        isRoadworks = false;
                        theIntent = new Intent(getApplicationContext(), FetchRss.class);
                        startActivity(theIntent);
                        Log.e(TAG, "Current Incidents chosen: " + isCurrentIncidents);

                    }
                    break;
                    case 2: {

                        isCurrentIncidents = false;
                        isPlannedRoadworks = false;
                        isRoadworks = true;
                        theIntent = new Intent(getApplicationContext(), FetchRss.class);
                        startActivity(theIntent);
                        Log.e(TAG, "Current Roadworks chosen: " + isRoadworks);

                    }
                    break;
                    case 3: {
                        Log.e(TAG, "Current Planned Roadworks chosen: ");
                        isCurrentIncidents = false;
                        isPlannedRoadworks = true;
                        isRoadworks = false;
                        theIntent = new Intent(getApplicationContext(), FetchRss.class);
                        startActivity(theIntent);
                    }
                    break;
                    case 4: {
                        Log.e(TAG, "Journey Planner chosen: ");
//                        theIntent = new Intent(getApplicationContext(), mapactivity.class);
//                        startActivity(theIntent);
                    }

                }


            }

//            private void openJourneyActivity() {
//                Intent intent = new Intent(getApplicationContext(), mapClass.class);
//                startActivity(intent);
//            }

            private void openHome() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


//    public void setFragment(Fragment fragment) {
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.framelayout, fragment);
//        fragmentTransaction.commit();
//    }



        boolean connected = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState()== NetworkInfo.State.CONNECTED ||
        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED){

            Log.d("myApp", "Network Available");
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(this, "Wifi Connection lost", duration);
            toast.show();            connected = true;
        }else{
            Log.d("myApp", "Network not Available");
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(this, "Wifi Connection lost", duration);
            toast.show();
            connected = false;
        }

    }

}
