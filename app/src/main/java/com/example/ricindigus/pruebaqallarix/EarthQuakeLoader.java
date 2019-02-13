package com.example.ricindigus.pruebaqallarix;


import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

public class EarthQuakeLoader extends AsyncTaskLoader<List<EarthQuake>> {

    /** Tag for log messages */
    private static final String LOG_TAG = EarthQuakeLoader.class.getName().toUpperCase();

    /** Query URL */
    private String mUrl;

    /**
     * Constructs a new {@link EarthQuakeLoader}.
     *
     * @param context of the activity
     * @param mUrl to load data from
     */

    public EarthQuakeLoader(Context context, String mUrl) {
        super(context);
        this.mUrl = mUrl;
    }




    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG,"onStartLoading(): Se fuerza la carga asincrona");
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<EarthQuake> loadInBackground() {
        Log.i(LOG_TAG,"loadInBackground(): Se carga en background los nuevos datos");
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<EarthQuake> earthquakes = QueryUtils.fetchEarthquakeData(mUrl);

        return earthquakes;
    }
}
