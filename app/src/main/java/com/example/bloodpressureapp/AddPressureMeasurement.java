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

public class AddPressureMeasurement extends Base {
    Button addValueButton;
    Calendar c = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pressure_measurement);
        addValueButton = findViewById(R.id.newPressureActivityButton);
        initialize();
        this.navView(this,this);
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

    private void initialize() {
        Button addMeasurement = findViewById(R.id.addMeasurementButton);
        addMeasurement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbController dbController = new DbController(getApplicationContext());
                dbController.insert_pressure_data(dbController.getDate(), dbController.getTime(), getPressure(), getheartBeat());
                dbController.CheckIfAllDataFromTableApears(dbController.getAllPressure());
                dbController.getDate();
                dbController.getTime();
                getPressure();
                Intent intent = new Intent(AddPressureMeasurement.this, LastPressureMesurement.class);
                startActivity(intent);
            }
        });
    }

    public String getheartBeat() {
        EditText heartbeat = findViewById(R.id.heartbeat);
        return String.valueOf(heartbeat.getText());
    }

    public String getPressure() {
        EditText skurczowe = findViewById(R.id.sPressure);
        EditText rozkurczowe = findViewById(R.id.rPressure);
        String pressure = skurczowe.getText() + "/" + rozkurczowe.getText();
        return pressure;
    }
}
