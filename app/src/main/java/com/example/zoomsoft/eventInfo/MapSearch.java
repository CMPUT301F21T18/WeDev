package com.example.zoomsoft.eventInfo;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.zoomsoft.MainActivity;
import com.example.zoomsoft.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapSearch extends AppCompatActivity {

    private static final int REQUEST_LOCATION = 1;

    Button currentLocationButton;
    Button viewLocationMap;
    TextView showLocationTxt;
    LocationManager locationManager;
    Double latitude, longitude;
    public FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_search);
        currentLocationButton = findViewById(R.id.current);
        viewLocationMap = findViewById(R.id.view_location);
        showLocationTxt = findViewById(R.id.location);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        currentLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
                //Check gps is enabled or not
                if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    //write function to enable gps
                    onGPS();
                }
                else {
                    //GPS is already on
                    try {
                        getLocation();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        viewLocationMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call the mapActivity to see the location
                Intent intent = new Intent(MapSearch.this, MapsActivity.class);
                intent.putExtra(MainActivity.EXTRA_MESSAGE, latitude + " " + longitude);
                startActivity(intent);
            }
        });
    }

    private void getLocation() throws IOException {

        //Check Permissions again

        if (ActivityCompat.checkSelfPermission(MapSearch.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapSearch.this,

                Manifest.permission.ACCESS_COARSE_LOCATION) !=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MapSearch.this,new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
        else
        {
            Location LocationGps= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location LocationNetwork=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location LocationPassive=locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

//            if (LocationGps !=null)
//            {
//                double lat=LocationGps.getLatitude();
//                double longi=LocationGps.getLongitude();
//
//                latitude=String.valueOf(lat);
//                longitude=String.valueOf(longi);
//
//                showLocationTxt.setText("Your Location:"+"\n"+"Latitude= "+latitude+"\n"+"Longitude= "+longitude);
//            }
//            else if (LocationNetwork !=null)
//            {
//                double lat=LocationNetwork.getLatitude();
//                double longi=LocationNetwork.getLongitude();
//
//                latitude=String.valueOf(lat);
//                longitude=String.valueOf(longi);
//
//                showLocationTxt.setText("Your Location:"+"\n"+"Latitude= "+latitude+"\n"+"Longitude= "+longitude);
//            }
            if (LocationPassive !=null)
            {
                double lat=LocationPassive.getLatitude();
                double longi=LocationPassive.getLongitude();

                latitude=lat;
                longitude=longi;
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(this, Locale.getDefault());
                addresses = geocoder.getFromLocation(lat, longi,1);
                String address = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String zip = addresses.get(0).getPostalCode();
                String country = addresses.get(0).getCountryName();
                showLocationTxt.setText("Your Location:"+"\n"+"Address= "+address+"\n"+"City= "+city+"\n"+"State= "+state+"\n"+"Zip= "+zip+"\n"+"Country= "+country);
                viewLocationMap.setVisibility(View.VISIBLE);
            }
            else
            {
                Toast.makeText(MapSearch.this, "Can't Get Your Location", Toast.LENGTH_SHORT).show();
            }

            //Thats All Run Your App
        }

    }

    private void onGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        final AlertDialog alertDialog= builder.create();
        alertDialog.show();
    }
    //Call snapShot to retrieve immediately the page loads
    //Interact with firebase by adding the longitude and latitude to fireStore
}