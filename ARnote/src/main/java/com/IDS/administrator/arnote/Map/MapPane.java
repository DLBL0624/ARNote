package com.IDS.administrator.arnote.Map;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import com.IDS.administrator.arnote.Message;
import com.IDS.administrator.arnote.MessageManager;
import com.IDS.administrator.arnote.R;
import com.IDS.administrator.arnote.VuforiaSamples.app.UserDefinedTargets.UserDefinedTargets;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class MapPane extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Context mContext;
    private LocationManager locationManager;
    private Location currentLocation;
    private Marker userMarker;
    public static LatLng userLocation;
    private boolean isMark = false;
    private ArrayList<Message> markedMess = new ArrayList<Message>();
    private ArrayList<Marker> messMarker = new ArrayList<Marker>();
    LocationListener userLocationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mContext = this;


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);

        //remove all existed message marker
        removeMessageMarks();
        MessageMarkDestroy();

        userLocationListener  = new LocationListener() {
            public void onLocationChanged(Location location) { //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
                // log it when the location changes
                if (location != null) {
                    if(isMark)userMarker.remove();
                    Log.i("SuperMap", "Location changed : Lat: "
                            + location.getLatitude() + " Lng: "
                            + location.getLongitude());
                    userLocation = new LatLng(location.getLatitude(),location.getLongitude());
                    userMarker = mMap.addMarker(new MarkerOptions().position(userLocation).title("Here's me").snippet("lalala").icon(BitmapDescriptorFactory.fromResource(R.drawable.usericon)));
                    MessageManager.latitude = userLocation.latitude;
                    MessageManager.longitude = userLocation.longitude;
                    isMark = true;
                }
            }

            public void onProviderDisabled(String provider) {
                // Provider被disable时触发此函数，比如GPS被关闭
            }

            public void onProviderEnabled(String provider) {
                //  Provider被enable时触发此函数，比如GPS被打开
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
                // Provider的转态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
            }
        };

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000, 5, userLocationListener);

//        Criteria criteria = new Criteria();
//
//        Location location = locationManager.getLastKnownLocation(locationManager
//                .getBestProvider(criteria, false));
//        double latitude = location.getLatitude();
//        double longitude = location.getLongitude();

//        userLocation = new LatLng(latitude,longitude);
//        userMarker = mMap.addMarker(new MarkerOptions().position(userLocation).title("Here's me").snippet("lalala"));


        getMessageList(MessageManager.messList);
        drawMessageMarks();
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


//    public void getLastLocation(){
//        String provider = getBestProvider();
//        currentLocation = locationManager.getLastKnownLocation(provider);
//        if(currentLocation != null){
//            setCurrentLocation(currentLocation);
//        }
//        else
//        {
//            Toast.makeText(this, "Location not yet acquired", Toast.LENGTH_LONG).show();
//        }
//    }

    public double getUserPositionLatitude(){//X
        return this.userLocation.latitude;
    }

    public double getUserPositionlongitude(){//Y
        return this.userLocation.longitude;
    }

    public void getMessageList(ArrayList<Message> meslist){
        Message mes;
        LatLng mesLocation;
        String str;
        for(int i=0; i<meslist.size(); i++){
            mes = meslist.get(i);
            markedMess.add(mes);
        }
    }

    public void drawMessageMarks(){
        Message mes;
        LatLng mesLocation;
        String str;
        Marker mMarker;
        for(int i = 0; i< markedMess.size(); i++){
            mes = markedMess.get(i);

            mesLocation = new LatLng(mes.getLocationX(),mes.getLocationY());
            str = mes.getMessage();
            mMarker = mMap.addMarker(new MarkerOptions().position(mesLocation).title("Message!").snippet(str));
            messMarker.add(mMarker);
        }
    }

    public void removeMessageMarks(){
        for(int i = messMarker.size()-1; i>=0; i--){
            messMarker.get(i).remove();
            messMarker.remove(i);
        }
    }

    public void MessageMarkDestroy(){
        for(int i = markedMess.size()-1; i>=0; i--){
            markedMess.remove(i);
        }

    }

}
