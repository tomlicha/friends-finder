package fontys.andr2.friendsfinder;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;

import fontys.andr2.friendsfinder.Fragments.FriendsFragment;
import fontys.andr2.friendsfinder.Fragments.MapFragment;
import fontys.andr2.friendsfinder.Fragments.ProfileFragment;
import fontys.andr2.friendsfinder.Users.User;
import fontys.andr2.friendsfinder.Users.UsersAvailable;

public class MainActivity extends FragmentActivity {

    UsersAvailable usersAvailable;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

       // profilepicture = (Bitmap) intent.getParcelableExtra("profilePicture");

        setContentView(R.layout.activity_main);

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
                Log.i("MainActivity", usersAvailable.getAvailables().toString());
                mapFragment.refresh(usersAvailable.getAvailables(), MainActivity.this);
            }
        });
        startRefreshThread();
    }

    private void startRefreshThread() {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                usersAvailable.refreshAvailable();
                Log.i("MainActivity", "Refreshed");
                handler.postDelayed(this, 1000);
            }
        }).start();
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_main, fragment);
        fragmentTransaction.commit();
    }

}
