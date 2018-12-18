package fontys.andr2.friendsfinder.Users;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import fontys.andr2.friendsfinder.Fragments.FriendsFragment;

public class UsersAvailable {

    private HashMap<String,User> usersAvailable;
    private RefreshListener refreshListener;


    public UsersAvailable() {
        this.usersAvailable = new HashMap<>();
    }

    public User getUser(String email){
        return usersAvailable.get(email);
    }

    public HashMap<String, User> getAvailable(){
        return usersAvailable ;
    }

    public void setRefresh(DatabaseReference mDatabase){
        usersAvailable.clear();
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
                            addUser(user);
                        }
                        if(refreshListener!=null) refreshListener.onRefresh();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

    public void setRefreshListener(RefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }

    public void addUser(User newUser) {
        usersAvailable.put(newUser.getEmail(), newUser);
    }

    public int getNumberOfUsers() {
        return usersAvailable.size();
    }

    public RefreshListener getRefreshListener() {
        return refreshListener;
    }

    public interface RefreshListener{
        void onRefresh();
    }

}
