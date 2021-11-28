package com.example.zoomsoft.eventInfo;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zoomsoft.MainActivity;
import com.example.zoomsoft.MainPageTabs;
import com.example.zoomsoft.R;
import com.example.zoomsoft.loginandregister.Login;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.zoomsoft.databinding.ActivityMapsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private SearchView searchView;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private ArrayList<String> list = new ArrayList<>();
    private ArrayList<LatLng> locationArrayList;
    public static String email = MainPageTabs.email;
    public double latitude = 25.2;
    public double longitude = 21.3;
    Button okButton;
    public boolean locationChosen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        okButton = findViewById(R.id.okButton);
        okButton.setVisibility(View.INVISIBLE);
        Intent intent = getIntent();
        String isSearching = intent.getStringExtra("isSearching");
        if(isSearching.equals("true")) {
            searchView = (SearchView) findViewById(R.id.sv_location);
            searchView.setVisibility(View.VISIBLE);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    mMap.clear();
                    String location = searchView.getQuery().toString();
                    List<Address> addressList = null;
                    if (location != null || !location.equals("")) {
                        Geocoder geocoder = new Geocoder(getApplicationContext());
                        try {
                            addressList = geocoder.getFromLocationName(location, 1);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if(addressList.size() == 0) return false;
                        Address address = addressList.get(0);
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(latLng).title(location));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                        latitude = latLng.latitude;
                        longitude = latLng.longitude;
                        okButton.setVisibility(View.VISIBLE);
                        okButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                final CollectionReference collectionReference = db.collection("Events");
                                DocumentReference documentReference = collectionReference.document(email);
                                ArrayList<String> arrayList = new ArrayList<>();
                                arrayList.add(latitude+"");
                                arrayList.add(longitude+"");
                                Map<String,Object> updates = new HashMap<>();
                                updates.put(HabitInfo.clickedHabit + "." + HabitEventDisplay.clickedDate + "." + "location", arrayList);
                                documentReference.update(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful())
                                            Log.w("TAG", "Saved to firebase");
                                        else
                                            Log.w("TAG", "Error saving document");
                                    }
                                });
                                Toast.makeText(MapsActivity.this,
                                        "Location Saved", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    return false;
                }
            });
        }
        else {
            //we are not searching
            String positionString = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
            String[] split = positionString.split(" ");
            latitude = Double.parseDouble(split[0]);
            longitude = Double.parseDouble(split[1]);
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng position = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions()
                .position(position)
                .title("Habit"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
    }
}

//Playgrounds
// inside on map ready method
// we will be displaying all our markers.
// for adding markers we are running for loop and
// inside that we are drawing marker on our map.
//        for (int i = 0; i < locationArrayList.size(); i++) {
//
//            // below line is use to add marker to each location of our array list.
//            mMap.addMarker(new MarkerOptions().position(locationArrayList.get(i)).title(list.get(i)));
//
//            // below lin is use to zoom our camera on map.
//            mMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f));
//
//            // below line is use to move our camera to the specific location.
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList.get(i)));
//        }