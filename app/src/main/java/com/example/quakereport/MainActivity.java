package com.example.quakereport;


import static android.view.View.GONE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import android.app.LoaderManager;

import android.content.Context;
import android.content.Loader;


import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Earthquake>> {
    //  above generic is same as loader class
    private static final String LOG_TAG=EarthquakeLoader.class.getName();
    private TextView mEmptyTextView;

    private static final int EARTHQUAKE_LOADER_ID = 1;

    /** URL for earthquake data from the USGS dataset */
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=5&limit=10";
    private EarthquakeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG,"onCreate method called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /** it gives the template on which data can be added further by addAll function of adapter
        adapter= new EarthquakeAdapter(this,new ArrayList<Earthquake>());
         */





        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        mEmptyTextView=(TextView)findViewById(R.id.empty_view);

        earthquakeListView.setEmptyView(mEmptyTextView);

        // Create a new {@link ArrayAdapter} of earthquakes
        adapter=new EarthquakeAdapter(this,new ArrayList<Earthquake>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);


        //  setting up listener on listView
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Earthquake currentEarthquake = (Earthquake) adapter.getItem(position);
                Uri webpage = Uri.parse(currentEarthquake.getUrl());
                Log.v("MyActivityOutside", currentEarthquake.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(intent);
                /*
                Must check why this doesn't work
                if (intent.resolveActivity(getPackageManager()) != null) {
                    Log.v("MyActivity",currentEarthquake.getUrl());
                    startActivity(intent);
                }

                 */
            }
        });

        Log.i(LOG_TAG,"calling initLoader method ");
        ConnectivityManager conMgr = (ConnectivityManager) getSystemService (Context.CONNECTIVITY_SERVICE);
        if(conMgr.getActiveNetworkInfo()!=null&&conMgr.getActiveNetworkInfo().isConnected()&&conMgr.getActiveNetworkInfo().isAvailable())
        getLoaderManager().initLoader(EARTHQUAKE_LOADER_ID,null,this);
        else{
            ProgressBar progressBar=(ProgressBar) findViewById(R.id.progress_bar);
            progressBar.setVisibility(GONE);
            mEmptyTextView.setText(R.string.no_internet_found);

        }


 /**
  *              Without http connection



        // Create a fake list of earthquake locations.
         ArrayList<Earthquake> earthquakes =QueryUtils.extractEarthquakes();


        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
        EarthquakeAdapter adapter = new EarthquakeAdapter(this, earthquakes);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);

      //  setting up listener on listView
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Earthquake currentEarthquake  =(Earthquake) adapter.getItem(i);
                Uri webpage = Uri.parse(currentEarthquake.getUrl());
                Log.v("MyActivityOutside",currentEarthquake.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(intent);
                /*
                Must check why this doesn't work
                if (intent.resolveActivity(getPackageManager()) != null) {
                    Log.v("MyActivity",currentEarthquake.getUrl());
                    startActivity(intent);
                }


            }
        });
     // Start the AsyncTask to fetch the earthquake data
          EarthquakeAsyncTask task = new EarthquakeAsyncTask();
                task.execute(USGS_REQUEST_URL);

  */


   }




/*
Instead of deciding exactly what activity we want to launch with an intent, we can instead specify what action we want to
 perform, without giving any option on what activity should actually handle that action. In this case, we'll create an intent
 with the action of viewing something. What do we want to view? Well this Intent constructor also accepts a URI for the data
  resource we want to view, and Android will sort out the best app to handle this sort of content. For instance, if the URI
  represented a location, Android would open up a mapping app. In this case, the resource is an HTTP URL, so Android will
  usually open up a browser.
 */


    /*
    Not appropriate
   public void listen(){
       Uri webpage = Uri.parse(url);
       Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
       if (intent.resolveActivity(getPackageManager()) != null) {
           startActivity(intent);
       }
   }

     */





  /** M-I to implement thread using AsyncTAsk but it can leads to crashes */

//
//   private class QuakeAsyncTask extends AsyncTask<String, Void, ArrayList<Earthquake>> {
//
//            @Override
//      protected ArrayList<Earthquake> doInBackground(String... strings) {
//                if (strings.length < 1)
//                    return null;
//                ArrayList<Earthquake> earthquakes = QueryUtils.extractEarthquakes(strings[0]);
//                return earthquakes;
//            }
//
//            @Override
//            protected void onPostExecute(ArrayList<Earthquake> earthquakes) {
//
//                /**
//                 * M-II of below implementation thing in document 15.Networking of quakeReport of threads & parallelism
//
//                 */


//                // Find a reference to the {@link ListView} in the layout
//                ListView earthquakeListView = (ListView) findViewById(R.id.list);
//
//                // Create a new {@link ArrayAdapter} of earthquakes
//                 adapter = new EarthquakeAdapter(MainActivity.this, earthquakes);
//
//                // Set the adapter on the {@link ListView}
//                // so the list can be populated in the user interface
//                earthquakeListView.setAdapter(adapter);
//
//                //  setting up listener on listView
//                earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        Earthquake currentEarthquake = (Earthquake) adapter.getItem(i);
//                        Uri webpage = Uri.parse(currentEarthquake.getUrl());
//                        Log.v("MyActivityOutside", currentEarthquake.getUrl());
//                        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
//                        startActivity(intent);
//                /*
//                Must check why this doesn't work
//                if (intent.resolveActivity(getPackageManager()) != null) {
//                    Log.v("MyActivity",currentEarthquake.getUrl());
//                    startActivity(intent);
//                }
//
//                 */
//                    }
//                });
//
//            }
//        }



    @NonNull
    @Override
    public Loader<ArrayList<Earthquake>> onCreateLoader(int id,  Bundle args) {
        Log.i(LOG_TAG,"onCreateLoader method ");

        return new EarthquakeLoader(this, USGS_REQUEST_URL);
    }



    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Earthquake>> loader, ArrayList<Earthquake> earthquakes) {


        Log.i(LOG_TAG,"onLoadFinished method ");
        mEmptyTextView.setText(R.string.no_earthquake_found);

        ProgressBar progressBar=(ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(GONE);
        /*
         * M-II of below implementation in document 15.Networking of quakeReport of threads & parallelism
         * is used here M-I used without loader
         */
        // Clear the adapter of previous earthquake data

        adapter.clear();

        if(earthquakes==null)
            return;
       adapter.addAll(earthquakes);




    }



    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Earthquake>> loader) {
        Log.i(LOG_TAG,"onLoaderReset method ");


        // Loader reset, so we can clear out our existing data.
           adapter.clear();
           // M-II
        /// adapter.addAll(new ArrayList<Earthquake>())
    }


}