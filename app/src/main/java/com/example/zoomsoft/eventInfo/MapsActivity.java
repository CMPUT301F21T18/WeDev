package com.example.zoomsoft.eventInfo;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.TextView;

import com.example.zoomsoft.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.zoomsoft.databinding.ActivityMapsBinding;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    LatLng TamWorth = new LatLng(-31.083332, 150.916672);
    LatLng NewCastle = new LatLng(-32.916668, 151.750000);
    LatLng Brisbane = new LatLng(-27.470125, 153.021072);
    LatLng sydney = new LatLng(-34, 151);
    private ArrayList<String> list = new ArrayList<>();
    private ArrayList<LatLng> locationArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationArrayList = new ArrayList<>();

//        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(@NonNull LatLng latLng) {
//                Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(getResources().getResourceName(R.drawable.pin), "drawable", getPackageName()));
//                Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, 38, 38, false);
//
//                googleMap.addMarker(new MarkerOptions()
//                        .position(new LatLng(point.latitude, point.longitude))
//                        .anchor(0.5f, 0.1f)
//                        .title("")
//                        .snippet("")
//                        .icon(BitmapDescriptorFactory.fromBitmap(resizedBitmap)));
//            }
//        });
        // on below line we are adding our
        // locations in our array list.
        locationArrayList.add(sydney); list.add("Sydney");
        locationArrayList.add(TamWorth); list.add("TamWorth");
        locationArrayList.add(NewCastle); list.add("NewCastle");
        locationArrayList.add(Brisbane); list.add("Brisbane");
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
        TextView textView = findViewById(R.id.textView6);
        mMap = googleMap;
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                mMap.clear();
                mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title("Location")
                );
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        });
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