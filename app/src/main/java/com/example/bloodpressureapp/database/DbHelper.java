package com.example.bloodpressureapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

/**
 * Class creates ned database and tables of sqlite database
 */
public class DbHelper extends SQLiteOpenHelper {
    String pressure_table_name = "PRESSURE_TABLE";
    String pressure_date_col = "PRESSURE_DATE";
    String pressure_time_col = "PRESSURE_TIME";
    String pressure_col = "PRESSURE";
    String heartbeat_col = "HEARTBEAT";

    String weigh_table_name = "WEIGH_TABLE";
    String weigh_date_col = "WEIGH_DATE";
    String weigh_time_col = "WEIGH_TIME";
    String weigh_col = "WEIGH";


    /**
     * Method creates db
     *
     * @param context
     */
    public DbHelper(Context context) {
        super(context, "BloodPressureAppDatabase.v3", null, 1);
    }

    /**
     * Method creates all Dables in database
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createPressureTableCommandCerator());
        db.execSQL(createWeightTableCommandCeratoar());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + pressure_table_name + "'");
        onCreate(db);
    }

    /**
     * Method creates sqlite command to create tables in database
     *
     * @return
     */
    private String createPressureTableCommandCerator() {
        String command = "CREATE TABLE " + pressure_table_name + "(ID INTEGER PRIMARY KEY AUTOINCREMENT," + pressure_date_col + " TEXT, " + pressure_time_col + " TEXT, " + pressure_col + " TEXT,"+ heartbeat_col + " TEXT);";
        return command;
    }

    private String createWeightTableCommandCeratoar() {
        String command = "CREATE TABLE " + weigh_table_name + "(ID INTEGER PRIMARY KEY AUTOINCREMENT," + weigh_date_col + " TEXT, " + weigh_time_col + " TEXT, " + weigh_col + " TEXT);";
        return command;
    }

    /**
     * Method inserts data into database
     *
     * @param date
     * @param time
     * @param pressure
     */
    public void insert_pressure_data(String date, String time, String pressure, String heartbeat) {
    }

    public void insert_weight_data(String date, String time, String weight) {
    }


    /**
     * Method returns data from Pressure pressure_table
     *
     * @param date
     * @return
     */
    public List getPressureData(String date) {
        return null;
    }


    /**
     * Method returns data from Pressure pressure_table for last 30 days
     *
     * @return
     */
    public List getPressure30Data() {
        return null;
    }


    /**
     * Method deletes all data from Pressure pressure_table
     */
    public void getDeletePressure() {
    }


}


