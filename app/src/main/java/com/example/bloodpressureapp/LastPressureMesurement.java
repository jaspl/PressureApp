package com.example.bloodpressureapp;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.bloodpressureapp.database.DbController;
import com.example.bloodpressureapp.database.SQLiteExcel;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.util.List;

public class LastPressureMesurement extends Base {
    DbController dbHelper;
    Button addValueButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        permission();
        dbHelper = new DbController(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addValueButton = findViewById(R.id.newPressureActivityButton);
        addValueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LastPressureMesurement.this, AddPressureMeasurement.class);
                startActivity(intent);
            }
        });
        getAllData();
        this.navView(this, this);
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


    public void permission() { 
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE},1);
    }

    public void getAllData() {
        CheckIfAllDataFromTableApears(dbHelper.getAllPressure());
    }

    public void CheckIfAllDataFromTableApears(List dbList) {
        TextView date = findViewById(R.id.datePressureTable);
        TextView time = findViewById(R.id.timePressureTable);
        TextView pressure = findViewById(R.id.pressurePressureTable);
        TextView heartbeat = findViewById(R.id.heartbeatPressureTable);
        List data = null;
        if (dbList != null) {
            data = dbList;
        }
        if (data != null) {
            for (int i = data.size() - 1; i > checklength(data) - 1; i--) {
                List col1 = (List) data.get(i);
                date.append("\n" + (CharSequence) col1.get(1) + "\n");
                time.append("\n" + (CharSequence) col1.get(2) + "\n");
                pressure.append("\n" + (CharSequence) col1.get(3) + "\n");
                heartbeat.append("\n" + (CharSequence) col1.get(4) + "\n");
            }
            Log.d("data", "null");
        }
    }

    private int checklength(List list) {
        if (list.size() - 1 > 10) return list.size() - 11;
        else return 0;

    }
}
