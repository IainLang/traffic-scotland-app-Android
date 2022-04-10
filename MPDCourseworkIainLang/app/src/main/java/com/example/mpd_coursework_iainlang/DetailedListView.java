package com.example.mpd_coursework_iainlang;
//Iain Lang - s1822179
import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DirectAction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class DetailedListView extends AppCompatActivity {
    Button linkBTN;

    TextView titleTv;
    TextView descriptiontv;
TextView geoTv;
TextView pubTv;
    Button linkbtn;
    Button HomeBtn;

    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_list_view);

        HomeBtn = (Button)findViewById(R.id.HomeBtn);
        titleTv = (TextView) findViewById(R.id.tvTitle);
        descriptiontv = (TextView) findViewById(R.id.tvDescription);
        geoTv = (TextView) findViewById(R.id.tvGeo);
        pubTv = (TextView) findViewById(R.id.tvPub);
        linkBTN = (Button) findViewById(R.id.linkbtn);



        String title = getIntent().getExtras().getString("title");
        String description = getIntent().getExtras().getString("description");
        String geo = getIntent().getExtras().getString("geo");
        String pub = getIntent().getExtras().getString("pub");
        String link = getIntent().getExtras().getString("link");



        descriptiontv.setText(String.valueOf(description));
        titleTv.setText(String.valueOf(title));
        geoTv.setText(String.valueOf(geo));
        pubTv.setText(String.valueOf(pub));


        titleTv.setText(String.valueOf(title));
        Log.e(TAG, "set title to: " + title);
        HomeBtn.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });
        linkBTN.setOnClickListener(view -> {
            Uri uri = Uri.parse(link);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });

    }

}