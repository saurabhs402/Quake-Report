package com.example.quakereport;

import android.app.Activity;

import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {
    private static final String LOCATION_SEPARATOR = "of";
    private static final String TAG = "MyActivity";
    public EarthquakeAdapter(Activity context , ArrayList<Earthquake> earthquakes){
        super(context, 0, earthquakes);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView; // now this is pointing to the root linear layout for listItemView layout (which is activity_numbers.xml here)
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false); // false implies listItemView till now not attached to the parent
            /*
            if listItemView is null implies we have to inflate
            a new view object from xml file
            this case occur when we asked for a first View
             */
        }


        Earthquake currentEarthquake  =(Earthquake) getItem(position);



        // To get a decimal value one method shown ih 22

        /* Another method here no need to update class earthquake unlike we did in first method

         Drawback-Another advantage to storing the earthquake magnitude as a double is that we can
         do math calculations easily. For example, if we wanted to find the average magnitude of all
         the earthquakes, we can easily sum up all the magnitudes and divide by the number of earthquakes.
         We wouldn’t be able to do math calculations if the magnitude values were text Strings.
         */

        TextView magTextView = (TextView) listItemView.findViewById(R.id.mag);
        double magnitude=Double.parseDouble(currentEarthquake.getMag());

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magTextView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(magnitude);

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);



        DecimalFormat formatter = new DecimalFormat("0.0");
        String output = formatter.format(magnitude);
        magTextView.setText(output);





        String place=currentEarthquake.getPlace();
        // split function also used mention in 21 of JSON parsing
        String distance,destination;
        if(place.contains(LOCATION_SEPARATOR)) {

            int i=place.indexOf(LOCATION_SEPARATOR);

             distance = place.substring(0, i + 3);
             destination = place.substring(i + 3, place.length());
        }
        else{
            // direct getString() method is not working as this class is not associated with the xml
            distance= getContext().getString(R.string.near_the);
            destination=place;

        }

        TextView distanceTextView = (TextView) listItemView.findViewById(R.id.distance);
        distanceTextView.setText(distance);

        TextView destinationTextView = (TextView) listItemView.findViewById(R.id.destination);
        destinationTextView.setText(destination);

        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date);
        dateTextView.setText(currentEarthquake.getDate());

        TextView timeTextView = (TextView) listItemView.findViewById(R.id.time);
        timeTextView.setText(currentEarthquake.getTime());

//        ImageView image = (ImageView) listItemView.findViewById(R.id.image);
//        if(currentWord.hasImage()) {
//
//            image.setImageResource(currentWord.getImageResId());
//            // Make sure view is visible because if we use same layout file because we are using recycling concept there might be the case that previous layout image view visibility is set to GONE
//            image.setVisibility(View.VISIBLE);
//        }
//        else{
//
//            image.setVisibility(View.GONE);
//        }

        return listItemView;
    }

    int getMagnitudeColor(double mag){
        int magResId;
        int fmag=(int)Math.floor(mag);
        switch (fmag){
            case 0:
            case 1:
                magResId=R.color.magnitude1;
                break;
            case 2:
                magResId=R.color.magnitude2;
                break;
            case 3:
                magResId=R.color.magnitude3;
                break;
            case 4:
                magResId=R.color.magnitude4;
                break;
            case 5:
                magResId=R.color.magnitude5;
                break;
            case 6:
                magResId=R.color.magnitude6;
                break;
            case 7:
                magResId=R.color.magnitude7;
                break;
            case 8:
                magResId=R.color.magnitude8;
                break;
            case 9:
                magResId=R.color.magnitude9;
                break;
            default:
                magResId=R.color.magnitude10plus;

        }
        return ContextCompat.getColor(getContext(), magResId);
    }
}
