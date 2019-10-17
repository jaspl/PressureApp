package com.example.bloodpressureapp;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.bloodpressureapp.database.DbController;
import com.example.bloodpressureapp.database.SQLiteExcel;
import com.google.android.material.navigation.NavigationView;

import java.io.File;

public class Base extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DbController dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mail) {
            SQLiteExcel sqLiteExcel = new SQLiteExcel(getApplicationContext());
            sqLiteExcel.ifFileExistsAndCreate();
            sqLiteExcel.checkIfXslCreated();
            sendMail(sqLiteExcel);
        } else if (id == R.id.all_pressure_measurement) {
            Intent intent = new Intent(this, AllPressureMesurements.class);
            startActivity(intent);
        } else if (id == R.id.last_pressure_measurement) {
            Intent intent = new Intent(this, LastPressureMesurement.class);
            startActivity(intent);

        } else if (id == R.id.all_weight_measurement) {
            Intent intent = new Intent(this, LastWeightMeasurement.class);
            startActivity(intent);

        } else if (id == R.id.last_weight_measurement) {
            Intent intent = new Intent(this, LastWeightMeasurement.class);
            startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        dbHelper = new DbController(this);
        if (id == R.id.action_settings) {
            Log.d("lol", "lol");
        } else if (id == R.id.dodaj_dane_do_wagi) {
            dbHelper.insert_weight_data("1212", "1212", "121");
            dbHelper.CheckIfAllDataFromTableApears(dbHelper.getAllWeight());
        } else if (id == R.id.usub_dane_o_wadze) {
            dbHelper.getDeleteWeight();
        }
        return super.onOptionsItemSelected(item);
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

    public void navView(Activity activity, NavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                activity, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(navigationItemSelectedListener);
    }
}
