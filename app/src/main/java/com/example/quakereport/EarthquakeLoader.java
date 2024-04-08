package com.example.quakereport;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.content.AsyncTaskLoader;
import android.util.Log;

import java.util.ArrayList;

public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<Earthquake>> {
    /** this generic parameter we passed while extending implies that the same value is return by loadInBackground which do same work as doInBackground*/

    private static final String LOG_TAG=EarthquakeLoader.class.getName();

    private String mUrl;

    public EarthquakeLoader(@NonNull Context context,String url) {

        super(context);
        mUrl=url;
    }

    @Override
    protected void onStartLoading() {  //forceLoad() is called in omStartLoading function best way
        Log.i(LOG_TAG,"onStartLoading method ");
        forceLoad();
    }

    @Nullable
    @Override
    public ArrayList<Earthquake> loadInBackground() {
        Log.i(LOG_TAG,"loadInBackground method ");
         if (mUrl==null)
              return null;
            ArrayList<Earthquake> earthquakes = QueryUtils.extractEarthquakes(mUrl);
        return earthquakes;
    }
}
