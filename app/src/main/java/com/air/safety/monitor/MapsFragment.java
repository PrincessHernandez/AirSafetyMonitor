package com.air.safety.monitor;

import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.LocationRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    View myView;
    private GoogleMap mMap;
    private ChildEventListener mChildEventListener;
    private DatabaseReference mRef;
    FirebaseUser authData = FirebaseAuth.getInstance().getCurrentUser() ;
    Marker marker;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.maps_layout, container, false);
//    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//        checkLocationPermission();
//    }
//        SupportMapFragment mapFragment = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);


        ChildEventListener mChildEventListener;
        mRef= FirebaseDatabase.getInstance().getReference(authData.getUid());
        mRef.push().setValue(marker);
        return myView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

//        // Add a marker in Humber and move the camera
//        LatLng latLng = new LatLng(43.731380, -79.597420);
//        mMap.addMarker(new MarkerOptions().position(latLng).title("You"));
//        //mMap.moveCamera(CameraUpdateFactory.newLatLng(humberCollege));
//        float zoomLevel = 16.0f; //This goes up to 21
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));

//        googleMap.setOnMarkerClickListener((GoogleMap.OnMarkerClickListener)this);
//        //googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
//        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot s : dataSnapshot.getChildren()) {
//                    Coordinates user = s.getValue(Coordinates.class);
//                    LatLng location = new LatLng(user.latitude, user.longitude);
//                    mMap.addMarker(new MarkerOptions().position(location).title("Marker"));
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        //decalre object
        final Double[] latitu = {43.731380};
        final Double[] longitu = {-79.597420};

        mRef = FirebaseDatabase.getInstance().getReference().child("coordinates");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                latitu[0] = (Double)
                        dataSnapshot.child("lat").getValue();
                longitu[0] = (Double)
                        dataSnapshot.child("lon").getValue();

                Log.d("LatLon", latitu[0] + longitu[0] +"");
                //Toast.makeText(LiveTrain.this, latitu[0].toString()+" - "+ longitu[0].toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                Log.w("Exception FB",databaseError.toException());
            }
        });

        LatLng coordinates = new LatLng(latitu[0], longitu[0]);


        mMap.addMarker(new MarkerOptions().position(coordinates).title("Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(coordinates));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinates,20f));

    }
}
