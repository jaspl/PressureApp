package com.example.bloodpressureapp.asharedPrefereces;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.bloodpressureapp.R;


/**
 * Class handle SharePreferences for reference values of blood preasure
 */
public class BloodPressureSharePreferences {
    private Context context;
    private android.content.SharedPreferences preferences;
    private static final String PREFERENCES_NAME = "bolldPreasureReferenceValues";
    private static final String PREFERENCES_HIGHER_SYSTOLIC_PREASURE_KEY = "higherSPressure";//cisnienie skurcczowe górnej wartości - higherSystolicPressure
    private static final String PREFERENCES_HIGHER_DIASTOLIC_PREASURE_KEY = "higherDPressure";//cisnienie rozkurczowe górnej wartości - higherDiastolicPressure
    private static final String PREFERENCES_LOWER_SYSTOLIC_PREASURE_KEY = "lowerSPressure";//cisnienie skurcczowe dolnej wartości - lowerSystolicPressure
    private static final String PREFERENCES_LOWER_DIASTOLIC_PREASURE_KEY = "lowerDPressure";//cisnienie rozkurczowe dolnej wartości - lowerDiastolicPressure

    public BloodPressureSharePreferences(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
    }

    /**
     * Method saves data in sharePreferences
     *
     * @param lowerSystolicPressure
     * @param lowerDiastolicPressure
     * @param higherSystolicPressure
     * @param higherDiastolicPressure
     */
    public void saveData(int lowerSystolicPressure, int lowerDiastolicPressure,
                         int higherSystolicPressure, int higherDiastolicPressure) {
        android.content.SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.putInt(PREFERENCES_HIGHER_SYSTOLIC_PREASURE_KEY, higherSystolicPressure);
        preferencesEditor.putInt(PREFERENCES_HIGHER_DIASTOLIC_PREASURE_KEY, higherDiastolicPressure);
        preferencesEditor.putInt(PREFERENCES_LOWER_SYSTOLIC_PREASURE_KEY, lowerSystolicPressure);
        preferencesEditor.putInt(PREFERENCES_LOWER_DIASTOLIC_PREASURE_KEY, lowerDiastolicPressure);
        preferencesEditor.commit();
    }

    /**
     * Method returns Higher Systolic Pressure
     * @return
     */
    public int getHigherSystolicPressure() {
        return preferences.getInt(PREFERENCES_HIGHER_SYSTOLIC_PREASURE_KEY, context.getResources().getInteger(R.integer.higherSystolicPressure));
    }

    /**
     * Metod returns Higher Diastolic Pressure
     * @return
     */
    public int getHigherDiastolicPressure() {
        return preferences.getInt(PREFERENCES_HIGHER_DIASTOLIC_PREASURE_KEY, context.getResources().getInteger(R.integer.higherDiastolicPressure));
    }

    /**
     * Method returns Lower Systolic Pressure
     * @return
     */
    public int getLowerSystolicPressure() {
        return preferences.getInt(PREFERENCES_LOWER_SYSTOLIC_PREASURE_KEY, context.getResources().getInteger(R.integer.lowerSystolicPressure));
    }

    /**
     * Method returns Lower Diastolic Pressure
     * @return
     */
    public int getLowerDiastolicPressure() {
        return preferences.getInt(PREFERENCES_LOWER_DIASTOLIC_PREASURE_KEY, context.getResources().getInteger(R.integer.lowerDiastolicPressure));
    }

    /**
     * Method clears sharedPreferences data
     */
    public void removeBloodPreasureDataSP() {
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.clear().commit();
    }
}
