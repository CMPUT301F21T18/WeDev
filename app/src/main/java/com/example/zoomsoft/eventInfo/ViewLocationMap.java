package com.example.zoomsoft.eventInfo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.zoomsoft.MainActivity;
import com.example.zoomsoft.MainPageTabs;
import com.example.zoomsoft.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ViewLocationMap extends AppCompatActivity {

    private static final int REQUEST_LOCATION = 1;

    Button currentLocationButton;
    Button confirmButton;
    Button searchLocationMap;
    TextView showLocationTxt;
    LocationManager locationManager;
    Double latitude, longitude;
    String temp = "a@gmail.com";
    public FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_search);
        currentLocationButton = findViewById(R.id.current);
        searchLocationMap = findViewById(R.id.search_location);
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
        searchLocationMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call the mapActivity to see the location
                Intent intent = new Intent(ViewLocationMap.this, MapsActivity.class);
                intent.putExtra("isSearching", "true");
                startActivity(intent);
            }
        });

        HabitEventFirebase habitEventFirebase = new HabitEventFirebase();
        habitEventFirebase.db.collection("Events").document(temp).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                habitEventFirebase.getHabitClickedDetails(new HabitEventFirebase.MyCallBack() {
                    @Override
                    public void getDescription(String s) {

                    }

                    @Override
                    public void getAllDates(List<String> list, List<Boolean> dateList) {

                    }

                    @Override
                    public void getHabitDetails(HashMap<String, Object> map) {
                        HashMap hashMap = (HashMap) map.get("October 21st, 2021");
                        ArrayList<String> location;
                        location = (ArrayList<String>) hashMap.get("location");
                        if(location.get(0).equals("N") || location.get(1).equals("N")) return;
                        double lat = Double.parseDouble(location.get(0));
                        double longi = Double.parseDouble(location.get(1));
                        Geocoder geocoder;
                        List<Address> addresses;
                        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                        try {
                            addresses = geocoder.getFromLocation(lat, longi,1);
                            String address = addresses.get(0).getAddressLine(0);
                            String city = addresses.get(0).getLocality();
                            String state = addresses.get(0).getAdminArea();
                            String zip = addresses.get(0).getPostalCode();
                            String country = addresses.get(0).getCountryName();
                            showLocationTxt.setText("Your Location:"+"\n"+"Address= "+address+"\n"+"City= "+city+"\n"+"State= "+state+"\n"+"Zip= "+zip+"\n"+"Country= "+country);
                            searchLocationMap.setVisibility(View.VISIBLE);
                            confirmButton = findViewById(R.id.confirm_button);
                            confirmButton.setVisibility(View.VISIBLE);
                        }
                        catch (Exception e) {
                            Log.d("Exception:", e.getLocalizedMessage());
                        }
                    }
                });
            }
        });
    }

    private void getLocation() throws IOException {
        //Check Permissions again
        if (ActivityCompat.checkSelfPermission(ViewLocationMap.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ViewLocationMap.this,

                Manifest.permission.ACCESS_COARSE_LOCATION) !=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(ViewLocationMap.this,new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
        else
        {
            Location LocationGps= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location LocationNetwork=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location LocationPassive=locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (LocationGps !=null)
            {
                double lat=LocationGps.getLatitude();
                double longi=LocationGps.getLongitude();
                latitude=lat;
                longitude = longi;
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
                searchLocationMap.setVisibility(View.VISIBLE);
            }
            else if (LocationNetwork !=null)
            {
                double lat=LocationNetwork.getLatitude();
                double longi=LocationNetwork.getLongitude();

                latitude = lat;
                longitude = longi;
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
                searchLocationMap.setVisibility(View.VISIBLE);
            }
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
                searchLocationMap.setVisibility(View.VISIBLE);
            }
            else
            {
                Toast.makeText(ViewLocationMap.this, "Can't Get Your Location", Toast.LENGTH_SHORT).show();
            }
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