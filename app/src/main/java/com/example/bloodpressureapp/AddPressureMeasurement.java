package com.example.bloodpressureapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.bloodpressureapp.database.DbController;
import com.example.bloodpressureapp.database.SQLiteExcel;

import android.view.View;

import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.widget.Toolbar;

import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddPressureMeasurement extends Base
        implements NavigationView.OnNavigationItemSelectedListener {
    Button addValueButton;
    Calendar c = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pressure_measurement);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        addValueButton=findViewById(R.id.newPressureActivityButton);
        initialize();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.mail) {
            SQLiteExcel sqLiteExcel = new SQLiteExcel(getApplicationContext());
            sqLiteExcel.ifFileExistsAndCreate();
            sqLiteExcel.checkIfXslCreated();
            sendMail(sqLiteExcel);
            // Handle the camera action
        } else if (id == R.id.all_pressure_measurement) {
            Intent intent = new Intent(AddPressureMeasurement.this, AllPressureMesurements.class);
            startActivity(intent);
        } else if (id == R.id.last_pressure_measurement) {
            Intent intent = new Intent(AddPressureMeasurement.this, LastPressureMesurement.class);
            startActivity(intent);

        } else if (id == R.id.all_weight_measurement) {
            Intent intent = new Intent(AddPressureMeasurement.this, LastWeightMeasurement.class);
            startActivity(intent);

        } else if (id == R.id.last_weight_measurement) {
            Intent intent = new Intent(AddPressureMeasurement.this, LastWeightMeasurement.class);
            startActivity(intent);

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void sendMail(SQLiteExcel sqLiteExcel) {
        File file = new File(sqLiteExcel.getXslFile());
        Intent mailIntent = new Intent(Intent.ACTION_SEND);
        mailIntent.setType("application/message");
        mailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{""});
        mailIntent.putExtra(Intent.EXTRA_SUBJECT, "MySubject");
        Uri URI = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".fileprovider", file);
        mailIntent.putExtra(Intent.EXTRA_STREAM, URI);
        startActivity(Intent.createChooser(mailIntent, "Send it out!"));

    }
    private void initialize() {


        Button addMeasurement = findViewById(R.id.addMeasurementButton);
        addMeasurement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbController dbController = new DbController(getApplicationContext());
                dbController.insert_pressure_data(dbController.getDate(),dbController.getTime(),getPressure(),getheartBeat());
                dbController.CheckIfAllDataFromTableApears(dbController.getAllPressure());
                dbController.getDate();
                dbController.getTime();
                getPressure();
                Intent intent = new Intent(AddPressureMeasurement.this, LastPressureMesurement.class);
                startActivity(intent);
            }
        });
    }

    public String getheartBeat(){
        EditText heartbeat = findViewById(R.id.heartbeat);
        return String.valueOf(heartbeat.getText());
    }

    public String getPressure() {
        EditText skurczowe = findViewById(R.id.sPressure);
        EditText rozkurczowe = findViewById(R.id.rPressure);
        String pressure = skurczowe.getText() + "/" + rozkurczowe.getText();
        // Log.d("pressure", pressure);
        return pressure;
    }



}
