package fontys.andr2.friendsfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.MenuItem;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import fontys.andr2.friendsfinder.Fragments.FriendsFragment;
import fontys.andr2.friendsfinder.Fragments.MapFragment;
import fontys.andr2.friendsfinder.Fragments.ProfileFragment;
import fontys.andr2.friendsfinder.Users.User;
import fontys.andr2.friendsfinder.Users.UsersAvailable;

public class MainActivity extends FragmentActivity {

    public static UsersAvailable usersAvailable;
    User user;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");


        final ProfileFragment profileFragment = new ProfileFragment();
        final MapFragment mapFragment = new MapFragment();
        mapFragment.setActivity(this);
        final FriendsFragment friendsFragment = new FriendsFragment();


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_profile:
                        setFragment(profileFragment);
                        return true;
                    case R.id.navigation_map:
                        setFragment(mapFragment);
                        return true;
                    case R.id.navigation_friends:
                        setFragment(friendsFragment);
                        return true;
                }
                return false;
            }
        });
        navigation.setSelectedItemId(R.id.navigation_map);
        usersAvailable = new UsersAvailable();
        usersAvailable.setRefreshListener(new UsersAvailable.RefreshListener() {
            @Override
            public void onRefresh() {
                Log.i("MainActivity", usersAvailable.getAvailable().toString());
                mapFragment.refresh(usersAvailable.getAvailable());
                friendsFragment.refresh(usersAvailable.getAvailable());
            }
        });
        usersAvailable.setRefresh(mDatabase);
    }


    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_main, fragment);
        fragmentTransaction.commit();
    }
}
