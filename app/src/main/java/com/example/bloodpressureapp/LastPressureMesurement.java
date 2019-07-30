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

public class LastPressureMesurement extends Base
        implements NavigationView.OnNavigationItemSelectedListener {
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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
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

    public void permission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                1);
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
                //Log.d("data", (String) col1.get(0) + "\n" + col1.get(1) + "\n" + col1.get(2) + "\n" + col1.get(3)+ "\n" + col1.get(4));
            }
            Log.d("data", "null");
        }
    }

    private int checklength(List list) {
        if (list.size() - 1 > 10) return list.size() - 11;
        else return 0;

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // Handle navigation view item clicks here.
        int id = menuItem.getItemId();

        if (id == R.id.mail) {
            SQLiteExcel sqLiteExcel = new SQLiteExcel(getApplicationContext());
            sqLiteExcel.ifFileExistsAndCreate();
            sqLiteExcel.checkIfXslCreated();
            sendMail(sqLiteExcel);
            // Handle the camera action
        } else if (id == R.id.all_pressure_measurement) {
            Intent intent = new Intent(LastPressureMesurement.this, AllPressureMesurements.class);
            startActivity(intent);
        } else if (id == R.id.last_pressure_measurement) {
            Intent intent = new Intent(LastPressureMesurement.this, LastPressureMesurement.class);
            startActivity(intent);

        } else if (id == R.id.all_weight_measurement) {
            Intent intent = new Intent(LastPressureMesurement.this, LastWeightMeasurement.class);
            startActivity(intent);

        } else if (id == R.id.last_weight_measurement) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
