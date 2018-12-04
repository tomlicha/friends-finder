package fontys.andr2.friendsfinder.Users;

import android.graphics.Bitmap;

public class User{
    private String profilePicture;
    private String name;
    private String email;

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(String profilePicture, String name, String email) {

        this.profilePicture = profilePicture;
        this.name = name;
        this.email = email;
    }

    public User() {
    }

    /**
     * Send to firebase the update of latitude and longitude
     */
    public void updateMyPosition() {

    }
}
