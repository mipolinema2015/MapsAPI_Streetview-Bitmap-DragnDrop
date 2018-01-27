package com.pastelstudio.hilal13.streetview;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanorama.OnStreetViewPanoramaChangeListener;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;
import com.squareup.picasso.Picasso;

import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,OnStreetViewPanoramaReadyCallback {
    private static final String MARKER_POSITION_KEY = "MarkerPosition";
    private GoogleMap mMap;
    private StreetViewPanorama mStreetViewPanorama;
    Button ton1;
    PlaceAutocompleteFragment placeAutoComplete;
    // George St, Sydney
    private static final LatLng SYDNEY = new LatLng(-33.87365, 151.20689);
    public LatLng currentLatLng;
    public double langitude,longitude;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

//        Picasso.with(this).load("http://i.imgur.com/DvpvklR.png").into(imageView);

        placeAutoComplete = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete);
        placeAutoComplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Log.d("Maps", "Place selected: " + place.getName());
                Toast.makeText(MapsActivity.this,"Place seleced :" + place.getName() + " and has Lat Lng " + place.getLatLng(),Toast.LENGTH_LONG).show();

                LatLng newSearch = place.getLatLng();
                markerRefresh(newSearch);
                mStreetViewPanorama.setPosition(newSearch);
            }

            @Override
            public void onError(Status status) {
                Log.d("Maps", "An error occurred: " + status);
                Toast.makeText(MapsActivity.this,"Error" + status,Toast.LENGTH_LONG).show();
            }


        });

        final LatLng markerPosition;
        if (savedInstanceState == null) {
            markerPosition = SYDNEY;
        } else {
            markerPosition = savedInstanceState.getParcelable(MARKER_POSITION_KEY);
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        StreetViewPanoramaFragment streetViewPanoramaFragment =
                (StreetViewPanoramaFragment)
                        getFragmentManager().findFragmentById(R.id.streetviewpanorama);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(new OnStreetViewPanoramaReadyCallback() {
            @Override
            public void onStreetViewPanoramaReady(StreetViewPanorama panorama) {
                mStreetViewPanorama = panorama;
                //mStreetViewPanorama.setOnStreetViewPanoramaChangeListener();
                // Only need to set the position once as the streetview fragment will maintain
                // its state.
                if (savedInstanceState == null) {
                    mStreetViewPanorama.setPosition(SYDNEY);

                }
            }
        });

        Button ton1 = (Button) findViewById(R.id.button1);
        ton1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent inte = new Intent(MapsActivity.this, StreetviewActivity.class);
                String lang = String.valueOf(langitude);
                String longi = String.valueOf(longitude);
                inte.putExtra("langitude",lang);
                inte.putExtra("longitude",longi);
                Toast.makeText(MapsActivity.this, "before! latitude "+lang+" and longitude "+longi,
                Toast.LENGTH_SHORT).show();
                startActivity(inte);
            }
        });

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
        LatLng sydney = new LatLng(-33.87365, 151.20689);
//        mStreetViewPanorama.setPosition(sydney);
        markerRefresh(sydney);
//        mStreetViewPanorama.setPosition(sydney);

    }

    public void markerRefresh(final LatLng pos){
//refresh marker tjoyy
//        langitude=pos.latitude;
//        longitude=pos.longitude;
        mMap.clear();
        mMap.addMarker(new MarkerOptions()
                .position(pos)
                .title("Marker in Searched position")
                .draggable(true));
//        mStreetViewPanorama.setPosition(pos);
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos,12.0f));
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {

            @Override
            public void onMarkerDragStart(Marker marker) {
                // TODO Auto-generated method stub
                // Here your code
                Toast.makeText(MapsActivity.this, "Dragging Start",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                // TODO Auto-generated method stub
                LatLng position = marker.getPosition();
                Toast.makeText(
                        MapsActivity.this,
                        "Lat " + position.latitude + " "
                                + "Long " + position.longitude,
                        Toast.LENGTH_LONG).show();
                langitude=position.latitude;
                longitude=position.longitude;
                currentLatLng = new LatLng(position.latitude, position.longitude);
                mStreetViewPanorama.setPosition(currentLatLng);

//            if(currentLatLng == null){
//                mStreetViewPanorama.setPosition(pos);
//            }
//                mStreetViewPanorama.setOnStreetViewPanoramaChangeListener();


            }

            @Override
            public void onMarkerDrag(Marker marker) {
                // TODO Auto-generated method stub
                // Toast.makeText(MainActivity.this, "Dragging",
                // Toast.LENGTH_SHORT).show();
                System.out.println("Draagging");
            }

        });

    }

    public void onStreetViewPanoramaReady(StreetViewPanorama panorama) {
//        mStreetViewPanorama = panorama;
//        mStreetViewPanorama.setOnStreetViewPanoramaChangeListener(
//                MapsActivity.this);
        // Only need to set the position once as the streetview fragment will maintain
        // its state.
//        if (savedInstanceState == null) {
        mStreetViewPanorama = panorama;
        //mStreetViewPanorama.setOnStreetViewPanoramaChangeListener(MapsActivity.this);
            panorama.setPosition(SYDNEY,150);
//        }
    }
}
