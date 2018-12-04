package fontys.andr2.friendsfinder.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.*;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.owlike.genson.Genson;

import java.util.List;

import fontys.andr2.friendsfinder.MyLocation;
import fontys.andr2.friendsfinder.R;
import fontys.andr2.friendsfinder.User;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;


public class MapFragment extends Fragment implements OnMapReadyCallback, EasyPermissions.PermissionCallbacks {
    FragmentActivity activity;

    private final static int LOCATION_REQUEST_ID = 0100;
    private GoogleMap mMap;
    private MyLocation myLocation;
    private Genson genson= new Genson();

    private User user;

    public MapFragment() {

    }

    public void setActivity(FragmentActivity activity){
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);
        return v;
    }

    private void checkEasyPermission() {
        if (!EasyPermissions.hasPermissions(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
            EasyPermissions.requestPermissions(
                    new PermissionRequest.Builder(activity, LOCATION_REQUEST_ID, Manifest.permission.ACCESS_FINE_LOCATION)
                            .setRationale("The application need Location permission to work properly")
                            .setPositiveButtonText("I understand")
                            .setNegativeButtonText("I refuse")
                            .build());
        } else {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(activity,
                        "The application need location permission to work properly",
                        Toast.LENGTH_LONG)
                        .show();
                return;
            }
            mMap.setMyLocationEnabled(true);

        }
    }


    /**
     * Manipulates the fragment_map once available.
     * This callback is triggered when the fragment_map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMyLocationButtonClickListener(onMyLocationButtonClickListener);
        mMap.setOnMyLocationClickListener(onMyLocationClickListener);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        checkEasyPermission();
        SharedPreferences sharedPref = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        String teste = sharedPref.getString("userData", "null");
        Log.d("user created:", teste);


        user = genson.deserialize(teste, User.class);
        myLocation = new MyLocation(getActivity());
        user.setLatitude(myLocation.getLatitude());
        user.setLongitude(myLocation.getLongitude());
        DatabaseReference objRef = FirebaseDatabase.getInstance().getReference("Users");
        Query pendingTasks = objRef.orderByChild("name").equalTo(user.getName());
        pendingTasks.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot tasksSnapshot) {
                for (DataSnapshot snapshot: tasksSnapshot.getChildren()) {
                    snapshot.getRef().child("latitude").setValue(user.getLatitude());
                    snapshot.getRef().child("longitude").setValue(user.getLongitude());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());

            }


        });
        // Add a marker in Sydney and move the camera



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(activity,
                    "The application need location permission to work properly",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }
        mMap.setMyLocationEnabled(true);

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(activity,
                "The application need location permission to work properly",
                Toast.LENGTH_LONG)
                .show();
    }
    private GoogleMap.OnMyLocationButtonClickListener onMyLocationButtonClickListener =
            new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    return false;
                }
            };

    private GoogleMap.OnMyLocationClickListener onMyLocationClickListener =
            new GoogleMap.OnMyLocationClickListener() {
                @Override
                public void onMyLocationClick(@NonNull Location location) {

/*
                    CircleOptions circleOptions = new CircleOptions();
                    circleOptions.center(new LatLng(location.getLatitude(),
                            location.getLongitude()));

                    circleOptions.radius(200);
                    circleOptions.fillColor(Color.RED);
                    circleOptions.strokeWidth(6);

                    mMap.addCircle(circleOptions);*/
                }
            };

}
