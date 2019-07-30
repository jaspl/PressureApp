package com.example.bloodpressureapp;

import android.content.Intent;
import android.os.Bundle;

import com.example.bloodpressureapp.database.DbController;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class LastWeightMeasurement extends Base
        implements NavigationView.OnNavigationItemSelectedListener {
    DbController dbController;
     EditText weight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbController = new DbController(this);
        setContentView(R.layout.activity_last_weight_measurement);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        CheckIfAllDataFromTableApears(dbController.getAllWeight());
        Button addWeightActivityButton = findViewById(R.id.newWeightActivityButton);
        weight= findViewById(R.id.weight);
        addWeightActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbController.insert_weight_data(dbController.getDate(),dbController.getTime(),weight.getText().toString());
                TextView date = findViewById(R.id.dateWeightTable);
                TextView time = findViewById(R.id.timeWeightTable);
                TextView weight = findViewById(R.id.weightWeightTable);
                date.setText("");
                time.setText("");
                weight.setText("");
                CheckIfAllDataFromTableApears(dbController.getAllWeight());
            }
        });

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.mail) {
            // Handle the camera action
        } else if (id == R.id.mail) {

        } else if (id == R.id.mail) {

        } else if (id == R.id.mail) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void CheckIfAllDataFromTableApears(List dbList) {
        TextView date = findViewById(R.id.dateWeightTable);
        TextView time = findViewById(R.id.timeWeightTable);
        TextView weight = findViewById(R.id.weightWeightTable);
        List data = null;
        if (dbList != null) {
            data = dbList;
        }
        if (data != null) {
            for (int i = data.size() - 1; i > checklength(data) - 1; i--) {
                List col1 = (List) data.get(i);
                date.append("\n" + (CharSequence) col1.get(1) + "\n");
                time.append("\n" + (CharSequence) col1.get(2) + "\n");
                weight.append("\n" + (CharSequence) col1.get(3) + "\n");
                //Log.d("data", (String) col1.get(0) + "\n" + col1.get(1) + "\n" + col1.get(2) + "\n" + col1.get(3)+ "\n" + col1.get(4));
            }
            Log.d("data", "null");
        }
    }

    private int checklength(List list) {
        if (list.size() - 1 > 10) return list.size() - 11;
        else return 0;

    }
}
