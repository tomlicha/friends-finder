package fontys.andr2.friendsfinder.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.owlike.genson.Genson;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import fontys.andr2.friendsfinder.MainActivity;
import fontys.andr2.friendsfinder.R;
import fontys.andr2.friendsfinder.User;


public class FriendsFragment extends Fragment {

    ListView listView;
    private DatabaseReference mDatabase;
    private User user;
    Genson genson = new Genson();
    List<User> listUsers;
    String[] names;
    String[] location;
    Geocoder gcd;


    public FriendsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_friends, container, false);
        listUsers = new ArrayList<>();
        gcd = new Geocoder(getContext(), Locale.getDefault());
        listView = v.findViewById(R.id.listviewfriends);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        SharedPreferences sharedPref = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        String teste = sharedPref.getString("userData", "null");
        Log.d("user created:", teste);
        user = genson.deserialize(teste, User.class);
        mDatabase.addValueEventListener
                (new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String name = ds.child("name").getValue(String.class);
                            String email = ds.child("email").getValue(String.class);
                            Double latitude = ds.child("latitude").getValue(Double.class);
                            Double longitude = ds.child("longitude").getValue(Double.class);
                            String profilePicture = ds.child("profilePicture").getValue(String.class);
                            Log.d("TAG", name + " / " + email + " / " + profilePicture);
                            User user = new User (profilePicture,name,email);
                            user.setLongitude(longitude);
                            user.setLatitude(latitude);
                            listUsers.add(user);

                        }
                        CustomAdapter customAdapter = new CustomAdapter();
                        listView.setAdapter(customAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }



                });

        return v;
    }

    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return listUsers.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.customlayout,null);
            ImageView imageView = convertView.findViewById(R.id.profilePictureFriend);
            TextView textView_name = convertView.findViewById(R.id.name);
            TextView textView_location = convertView.findViewById(R.id.location);
            Log.d("latitude and longitude",Double.toString(listUsers.get(i).getLatitude())+" "+Double.toString(listUsers.get(i).getLongitude()));
            Picasso.with(getActivity())
                    .load(listUsers.get(i).getProfilePicture())
                    .into(imageView, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {

                        }
                    });
            try {
                List<Address> addresses = gcd.getFromLocation(listUsers.get(i).getLatitude(),listUsers.get(i).getLongitude(),1);
                if (addresses.size() > 0) {

                    Log.d("latitude and longitude", Double.toString(listUsers.get(i).getLatitude()) + " " + Double.toString(listUsers.get(i).getLongitude()));
                    textView_location.setText(addresses.get(0).getLocality());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            textView_name.setText(listUsers.get(i).getName());
            return convertView;
        }
    }


}
