package fontys.andr2.friendsfinder.Users;

import android.graphics.Bitmap;

public class User{
    private String profilePicture;
    private String name;
    private String email;
    private double longitude;
    private double latitude;

    //TODO : save the bitmap image

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

    public User(String profilePicture, String name, String email, double longitude, double latitude) {
        this.profilePicture = profilePicture;
        this.name = name;
        this.email = email;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public User(String profilePicture, String name, String email) {
        this.profilePicture = profilePicture;
        this.name = name;
        this.email = email;
        this.longitude = 0;
        this.latitude = 0;
    }

    public User() {
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Send to firebase the update of latitude and longitude
     */
    public void updateMyPosition(double latitude, double longitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
