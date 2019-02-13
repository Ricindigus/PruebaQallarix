package com.example.ricindigus.pruebaqallarix;

import android.app.LoaderManager;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class Main3Activity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<EarthQuake>> {

    Button btSalir;
    TextView tvContentJson;
    public static final String USGS_REQUEST_URL = "https://prueba-qallarix.firebaseio.com/features.json";
    private static final int EARTHQUAKE_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        btSalir = findViewById(R.id.btSalir);
        tvContentJson = findViewById(R.id.tvContentJson);

        btSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finishAffinity();
            }
        });

        LoaderManager loaderManager = getLoaderManager();
        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
    }

    @Override
    public Loader<List<EarthQuake>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL

//        Uri baseUri = Uri.parse(USGS_REQUEST_URL);
//        Uri.Builder uriBuilder = baseUri.buildUpon();
//
//        uriBuilder.appendQueryParameter("format", "geojson");
//        uriBuilder.appendQueryParameter("limit", "10");
//        uriBuilder.appendQueryParameter("minmag", minMagnitude);
//        uriBuilder.appendQueryParameter("orderby", orderBy);

        return new EarthQuakeLoader(this, USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<EarthQuake>> loader, List<EarthQuake> earthquakes) {

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (earthquakes != null && !earthquakes.isEmpty()) {
            tvContentJson.setText(earthquakes.get(0).getLocation());
        }

    }

    @Override
    public void onLoaderReset(Loader<List<EarthQuake>> loader) {

        tvContentJson.setText("");

    }
}
