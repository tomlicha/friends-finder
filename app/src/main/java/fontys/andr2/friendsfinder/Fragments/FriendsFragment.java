package fontys.andr2.friendsfinder.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.owlike.genson.Genson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import fontys.andr2.friendsfinder.R;
import fontys.andr2.friendsfinder.Users.User;


public class FriendsFragment extends Fragment {

    ListView listView;
    private User user;
    Genson genson = new Genson();
    LinkedHashMap<String, User> listUsers;
    String[] names;
    String[] location;
    Geocoder gcd;


    public FriendsFragment() {
        // Required empty public constructor
        listUsers = new LinkedHashMap<>();
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_friends, container, false);

        gcd = new Geocoder(getContext(), Locale.getDefault());
        listView = v.findViewById(R.id.listviewfriends);
        refreshListView();

        SharedPreferences sharedPref = Objects.requireNonNull(getActivity()).getSharedPreferences("pref", Context.MODE_PRIVATE);
        String jsonUser = sharedPref.getString("userData", "null");
        Log.d("user created:", jsonUser);
        this.user = genson.deserialize(jsonUser, User.class);

        return v;
    }

    public void refresh(HashMap<String, User> users) {
        for (Map.Entry<String, User> user_entry : users.entrySet())
        {
            listUsers.put(user_entry.getKey(), user_entry.getValue());
        }
        if(listView!=null) refreshListView();

    }

    private void refreshListView(){
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
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
            Log.d("latitude and longitude",Double.toString(getUserByIndex(i).getLatitude())+" "+Double.toString(getUserByIndex(i).getLongitude()));
            Picasso.with(getActivity())
                    .load(getUserByIndex(i).getProfilePicture())
                    .into(imageView, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {

                        }
                    });
            try {
                List<Address> addresses = gcd.getFromLocation(getUserByIndex(i).getLatitude(),getUserByIndex(i).getLongitude(),1);
                if (addresses.size() > 0) {

                    Log.d("latitude and longitude", Double.toString(getUserByIndex(i).getLatitude()) + " " + Double.toString(getUserByIndex(i).getLongitude()));
                    textView_location.setText(addresses.get(0).getLocality());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            textView_name.setText(getUserByIndex(i).getName());
            return convertView;
        }
    }

    public User getUserByIndex(int index){
        return listUsers.get( (Objects.requireNonNull(listUsers.keySet().toArray()))[ index ] );
    }

}
