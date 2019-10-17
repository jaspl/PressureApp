package com.example.bloodpressureapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Class controlls database
 */
public class DbController extends DbHelper {
    public DbController(Context context) {
        super(context);
    }


    private SQLiteDatabase db = this.getWritableDatabase();

    @Override
    public void insert_pressure_data(String date, String time, String pressure, String heartbeat) {
        ContentValues values = new ContentValues();
        values.put(pressure_date_col, date);
        values.put(pressure_time_col, time);
        values.put(pressure_col, pressure);
        values.put(heartbeat_col, heartbeat);
        long RESULT = db.insert(pressure_table_name, null, values);
        if (RESULT == -1) {
            Log.d("data insert check", " Data not inserted");
        } else {
            Log.d("data insert check", "Data inserted");
        }
    }

    @Override
    public void insert_weight_data(String date, String time, String weight) {
        ContentValues values = new ContentValues();
        values.put(weigh_date_col, date);
        values.put(weigh_time_col, time);
        values.put(weigh_col, weight);
        long RESULT = db.insert(weigh_table_name, null, values);
        if (RESULT == -1) {
            Log.d("data insert check", " Data not inserted");
        } else {
            Log.d("data insert check", "Data inserted");
        }
    }

    /**
     * Method puts all data into List
     *
     * @param res
     * @return
     */
    private List putValuesToList(Cursor res) {
        List<List> data = new ArrayList<>();
        if (res.getCount() == 0) {
            return null;
        }
        int count = res.getColumnCount();
        while (res.moveToNext()) {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                list.add(res.getString(i));
            }

            data.add(list);
        }
        return data;
    }


    public List getPressureWhenData(String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + pressure_table_name + " WHERE " + pressure_date_col + " LIKE '" + date + "';", null);
        return putValuesToList(res);
    }

    public List getAllPressure() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + pressure_table_name + ";", null);
        return putValuesToList(res);
    }

    public List getWeightWhenData(String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + weigh_table_name + " WHERE " + weigh_date_col + " LIKE '" + date + "';", null);
        return putValuesToList(res);
    }

    public List getAllWeight() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + weigh_table_name + ";", null);
        return putValuesToList(res);
    }


    public void getDeletePressure() {
        db.execSQL("delete from " + pressure_table_name);
    }
    public void getDeleteWeight() {
        db.execSQL("delete from " + weigh_table_name);
    }

    /**
     * Method shows in log all data from dbList
     *
     * @param dbList
     */
    //TODO wyświetla w logach wszystkie dane mysle ze moze sie przydac
    public void CheckIfAllDataFromTableApears(List dbList) {
        List data = null;
        if (dbList != null) {
            data = dbList;
        }
        if (data != null) {
            for (int i = 0; i < data.size(); i++) {
                List col1 = (List) data.get(i);
                String a = "";
                for (int j = 0; j < col1.size(); j++) {
                    a = a + col1.get(j) + "\n";
                }
                Log.d("data", a);
            }
            Log.d("data", "null");
        }
    }

    private int checkLength(List list) {
        if (list.size() - 1 > 10) return list.size() - 11;
        else return 0;

    }
    Calendar c = Calendar.getInstance();

    public String getDate() {
        SimpleDateFormat dateformat = new SimpleDateFormat("d-M-yyyy");
        String date = dateformat.format(c.getTime());
        return date;
    }

    public String getTime() {
        SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm");
        String time = timeformat.format(c.getTime());
        return time;
    }
}
