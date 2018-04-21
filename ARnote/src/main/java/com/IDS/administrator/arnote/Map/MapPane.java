
package com.IDS.administrator.arnote.Map;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.IDS.administrator.arnote.R;
import com.IDS.administrator.arnote.VuforiaSamples.app.UserDefinedTargets.UserDefinedTargets;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapPane extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney").snippet("The most populous city in Austrilia"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


    }

    public void OnMesClick(View view) {
        if( view.getId()==R.id.m_add) {

        }
    }

    public void OnAddClick(View view) {
        if( view.getId()==R.id.m_add) {
            Intent i = new Intent(MapPane.this, UserDefinedTargets.class);
            startActivity(i);
        }
    }

    public void OnMapClick(View view) {
        if( view.getId()==R.id.m_map) {

        }
    }
}
