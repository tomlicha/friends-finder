package fontys.andr2.friendsfinder.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.owlike.genson.Genson;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import fontys.andr2.friendsfinder.MainActivity;
import fontys.andr2.friendsfinder.MyLocation;
import fontys.andr2.friendsfinder.R;
import fontys.andr2.friendsfinder.Users.User;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

import static fontys.andr2.friendsfinder.MainActivity.usersAvailable;


public class MapFragment extends Fragment implements OnMapReadyCallback, EasyPermissions.PermissionCallbacks, GoogleMap.OnMarkerClickListener {
    FragmentActivity activity;
    LocationManager locationManager;
    private final static int LOCATION_REQUEST_ID = 0100;
    private GoogleMap mMap;
    private MyLocation myLocation;
    private FloatingActionButton findMe;
    private Genson genson = new Genson();
    LocationListener locationListenerGPS;
    DatabaseReference mDatabase;
    private User user;
    Marker marker;
    private HashMap<Marker, User> markerUserHashMap;
    List<Polyline> polylines;


    public MapFragment() {

    }

    public void setActivity(FragmentActivity activity) {
        this.activity = activity;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        markerUserHashMap = new HashMap<>();
        polylines = new ArrayList<Polyline>();
        assert mMapFragment != null;
        findMe = v.findViewById(R.id.findme);
        findMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(user.getLatitude(), user.getLongitude())) // Sets the center of the map to Mountain View
                        .zoom(17)                   // Sets the zoom
                        .bearing(0)                // Sets the orientation of the camera to east
                        .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


            }
        });


        locationListenerGPS = new LocationListener() {
            @Override
            public void onLocationChanged(android.location.Location location) {
                final double latitude = location.getLatitude();
                final double longitude = location.getLongitude();
                String msg = "New Latitude: " + latitude + "New Longitude: " + longitude;
                Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                mDatabase = FirebaseDatabase.getInstance().getReference("Users");
                Query pendingTasks = mDatabase.orderByChild("name").equalTo(user.getName());
                pendingTasks.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot tasksSnapshot) {
                        for (DataSnapshot snapshot : tasksSnapshot.getChildren()) {
                            snapshot.getRef().child("latitude").setValue(latitude);
                            snapshot.getRef().child("longitude").setValue(longitude);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getMessage());

                    }


                });
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };


        mMapFragment.getMapAsync(this);
        return v;
    }

    @Override
    public void onResume() {
        Log.e("DEBUG", "onResume of HomeFragment");
        if (mDatabase != null) usersAvailable.setRefresh(mDatabase);
        if (user != null)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(user.getLatitude(), user.getLongitude()), 14.0f));

        super.onResume();
    }

    @Override
    public void onPause() {
        Log.e("DEBUG", "OnPause of HomeFragment");
        super.onPause();
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
            locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    500,
                    10, locationListenerGPS);

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
        mMap.setOnMarkerClickListener(this);
        checkEasyPermission();
        SharedPreferences sharedPref = Objects.requireNonNull(getActivity()).getSharedPreferences("pref", Context.MODE_PRIVATE);
        String teste = sharedPref.getString("userData", "null");
        Log.d("user created:", teste);


        user = genson.deserialize(teste, User.class);
        myLocation = new MyLocation(getActivity());
        user.setLatitude(myLocation.getLatitude());
        user.setLongitude(myLocation.getLongitude());
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        Query pendingTasks = mDatabase.orderByChild("name").equalTo(user.getName());
        pendingTasks.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot tasksSnapshot) {
                for (DataSnapshot snapshot : tasksSnapshot.getChildren()) {
                    snapshot.getRef().child("latitude").setValue(user.getLatitude());
                    snapshot.getRef().child("longitude").setValue(user.getLongitude());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());

            }


        });

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(user.getLatitude(), user.getLongitude()), 14.0f));
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

    public void refresh(HashMap<String, User> users) {
        if (mMap == null) return;
        mMap.clear();
        for (Map.Entry<String, User> user_entry : users.entrySet()) {
            User user = user_entry.getValue();
            addUserOnMap(user);
        }
    }

    private void addUserOnMap(final User user) {
        if(myLocation==null) return;
        final LatLng latLng = new LatLng(user.getLatitude(), user.getLongitude());
        final Bitmap bitmap = createUserBitmap(user.getProfilePicture());

        //Add Distance Here!
        double distanceFriend = CalculationByDistance(myLocation.getLatitude(), myLocation.getLongitude(), user.getLatitude(), user.getLongitude());
        DecimalFormat decimalFormat = new DecimalFormat(".##");
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title(user.getName())
                .snippet(decimalFormat.format(distanceFriend) + " Km");
        if (bitmap!=null){
            BitmapDescriptor bmDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);
            markerOptions.icon(bmDescriptor);
        }else{
            System.err.println("User "+user.getName()+" -> Impossible to retrieve image");
        }
        marker = mMap.addMarker(markerOptions);
        if (marker != null && user != null) markerUserHashMap.put(marker, user);

//        options.anchor(0.5f, 0.907f);

    }

    private Bitmap createUserBitmap(String user_image_url) {
        Bitmap result = null;
        try {
            result = Bitmap.createBitmap(dp(62), dp(76), Bitmap.Config.ARGB_8888);
            result.eraseColor(Color.TRANSPARENT);
            Canvas canvas = new Canvas(result);
            Drawable drawable = getResources().getDrawable(R.drawable.ic_livepin);
            drawable.setBounds(0, 0, dp(62), dp(76));
            drawable.draw(canvas);

            Paint roundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            RectF bitmapRect = new RectF();
            canvas.save();

            URL url = new URL(user_image_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            //Bitmap bitmap = BitmapFactory.decodeFile(path.toString()); /*generate bitmap here if your image comes from any url*/
            if (bitmap != null) {
                BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                Matrix matrix = new Matrix();
                float scale = dp(52) / (float) bitmap.getWidth();
                matrix.postTranslate(dp(5), dp(5));
                matrix.postScale(scale, scale);
                roundPaint.setShader(shader);
                shader.setLocalMatrix(matrix);
                bitmapRect.set(dp(5), dp(5), dp(52 + 5), dp(52 + 5));
                canvas.drawRoundRect(bitmapRect, dp(26), dp(26), roundPaint);
            }
            canvas.restore();
            try {
                canvas.setBitmap(null);
            } catch (Exception e) {
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return result;
    }


    public int dp(float value) {
        if (value == 0) {
            return 0;
        }
        return (int) Math.ceil(getResources().getDisplayMetrics().density * value);
    }

    public double CalculationByDistance(double userLatitude, double userLongitude, double friendLatitude, double friendLongitude) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = userLatitude;
        double lat2 = friendLatitude;
        double lon1 = userLongitude;
        double lon2 = friendLongitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Radius * c;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        for (Polyline line : polylines) {
            line.remove();
        }
        polylines.clear();
        System.out.println("Marker clicked " + marker.getTitle());
        User user = markerUserHashMap.get(marker);
        Polyline line = mMap.addPolyline(
                new PolylineOptions().add(new LatLng(myLocation.getLatitude(),
                                myLocation.getLongitude()),
                        new LatLng(user.getLatitude(),
                                user.getLongitude()))
                        .width(5).color(Color.RED));
        polylines.add(line);
        return false;
    }



}
