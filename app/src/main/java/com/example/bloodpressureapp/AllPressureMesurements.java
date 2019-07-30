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

public class AllPressureMesurements extends Base
        implements NavigationView.OnNavigationItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_pressure_mesurements);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        calendar();
        dbHelper = new DbController(getApplicationContext());
        CheckIfAllDataFromTableApears(((DbController) dbHelper).getAllPressure());

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
            Intent intent = new Intent(AllPressureMesurements.this, AllPressureMesurements.class);
            startActivity(intent);
        } else if (id == R.id.last_pressure_measurement) {
            Intent intent = new Intent(AllPressureMesurements.this, LastPressureMesurement.class);
            startActivity(intent);

        } else if (id == R.id.all_weight_measurement) {
            Intent intent = new Intent(AllPressureMesurements.this, LastWeightMeasurement.class);
            startActivity(intent);

        } else if (id == R.id.last_weight_measurement) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void calendar(){
        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {

                String date = i2+"-"+(i1+1)+"-"+i;
                System.out.println(date);
                dbHelper = new DbController(getApplicationContext());
                CheckIfAllDataFromTableApears(((DbController) dbHelper).getPressureWhenData(date));
            }
        });
    }

    public void CheckIfAllDataFromTableApears(List dbList) {
        TextView date= findViewById(R.id.datePressureTable);
        TextView time=findViewById(R.id.timePressureTable);
        TextView pressure=findViewById(R.id.pressurePressureTable);
        TextView heartbeat=findViewById(R.id.heartbeatPressureTable);
        String dateS="";
        String timeS="";
        String pressureS="";
        String heartbeatS="";
        List data= null;
        if (dbList != null) {
            data = dbList;
        }
        if (data != null) {
            for (int i = data.size()-1; i>checklength(data)-1; i--) {
                List col = (List) data.get(i);
                dateS=dateS+'\n'+(String) col.get(1)+'\n';
                timeS=timeS+'\n'+(String) col.get(2)+'\n';
                pressureS= pressureS +'\n'+(String) col.get(3)+'\n';
                heartbeatS=heartbeatS+'\n'+(String) col.get(4)+'\n';
                Log.d("data", (String) col.get(0) + "\n" + col.get(1) + "\n" + col.get(2) + "\n" + col.get(3)+ "\n" + col.get(4));
            }
        }
        date.setText(dateS);
        time.setText(timeS);
        pressure.setText(pressureS);
        heartbeat.setText(heartbeatS);
    }
    private int checklength(List list){
        if(list.size()-1>10)return list.size()-11;else return 0;

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
}
