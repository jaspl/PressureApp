package com.example.bloodpressureapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.bloodpressureapp.database.DbController;

public class Base extends AppCompatActivity {
    DbController dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
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
        dbHelper = new DbController(this);
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //dbHelper.getDeletePressure();
            Log.d("lol", "lol");

        } else if (id == R.id.dodaj_dane_do_wagi) {
            dbHelper.insert_weight_data("1212", "1212", "121");
            dbHelper.CheckIfAllDataFromTableApears(dbHelper.getAllWeight());
        } else if (id == R.id.usub_dane_o_wadze) {
            dbHelper.getDeleteWeight();
        }


        return super.onOptionsItemSelected(item);
    }

}
