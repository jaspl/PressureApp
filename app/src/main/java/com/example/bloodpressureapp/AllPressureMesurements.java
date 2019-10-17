package com.example.bloodpressureapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.bloodpressureapp.database.DbController;
import com.example.bloodpressureapp.database.DbHelper;
import com.example.bloodpressureapp.database.SQLiteExcel;

import android.util.Log;

import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.widget.Toolbar;

import android.widget.CalendarView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

public class AllPressureMesurements extends Base {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_pressure_mesurements);
        calendar();
        dbHelper = new DbController(getApplicationContext());
        CheckIfAllDataFromTableApears(((DbController) dbHelper).getAllPressure());
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

    private void calendar() {
        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
                String date = i2 + "-" + (i1 + 1) + "-" + i;
                System.out.println(date);
                dbHelper = new DbController(getApplicationContext());
                CheckIfAllDataFromTableApears(((DbController) dbHelper).getPressureWhenData(date));
            }
        });
    }

    public void CheckIfAllDataFromTableApears(List dbList) {
        TextView date = findViewById(R.id.datePressureTable);
        TextView time = findViewById(R.id.timePressureTable);
        TextView pressure = findViewById(R.id.pressurePressureTable);
        TextView heartbeat = findViewById(R.id.heartbeatPressureTable);
        String dateS = "";
        String timeS = "";
        String pressureS = "";
        String heartbeatS = "";
        List data = null;
        if (dbList != null) {
            data = dbList;
        }
        if (data != null) {
            for (int i = data.size() - 1; i > checklength(data) - 1; i--) {
                List col = (List) data.get(i);
                dateS = dateS + '\n' + (String) col.get(1) + '\n';
                timeS = timeS + '\n' + (String) col.get(2) + '\n';
                pressureS = pressureS + '\n' + (String) col.get(3) + '\n';
                heartbeatS = heartbeatS + '\n' + (String) col.get(4) + '\n';
                Log.d("data", (String) col.get(0) + "\n" + col.get(1) + "\n" + col.get(2) + "\n" + col.get(3) + "\n" + col.get(4));
            }
        }
        date.setText(dateS);
        time.setText(timeS);
        pressure.setText(pressureS);
        heartbeat.setText(heartbeatS);
    }

    private int checklength(List list) {
        if (list.size() - 1 > 10) return list.size() - 11;
        else return 0;
    }
}
