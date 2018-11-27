package fontys.andr2.friendsfinder;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import fontys.andr2.friendsfinder.Fragments.FriendsFragment;
import fontys.andr2.friendsfinder.Fragments.MapFragment;
import fontys.andr2.friendsfinder.Fragments.ProfileFragment;

public class MainActivity extends FragmentActivity {
    public static String email;
    public static String name;
    public static Bitmap profilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        name = intent.getStringExtra("name");
        byte[] byteArray = getIntent().getByteArrayExtra("profilePicture");
        profilePicture = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

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
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_main, fragment);
        fragmentTransaction.commit();
    }

}
