package fontys.andr2.friendsfinder.Users;

import android.graphics.Bitmap;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.MalformedInputException;

public class User{

    private String profilePictureUri;
    private String name;
    private String email;
    private double latitude;
    private double longitude;

    public User(String profilePictureUri, String name, String email, double latitude, double longitude) {
        setProfilePicture(profilePictureUri);
        this.name = name;
        this.email = email;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getProfilePicture() {
        return profilePictureUri;
    }

    public void setProfilePicture(String profilePicture){
        this.profilePictureUri = profilePicture;
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

    public User(String profilePictureUri, String name, String email) {

        this.profilePictureUri = profilePictureUri;
        this.name = name;
        this.email = email;
        this.latitude=0.0;
        this.longitude=0.0;
    }

    public User() {
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        if (longitude==null) longitude = 0.0;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        if (latitude==null) latitude = 0.0;
        this.latitude = latitude;
    }

    /**
     * Send to firebase the update of latitude and longitude
     */
    public void updateMyPosition(double latitude, double longitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public void setEmptyProfilePicture() {
        profilePictureUri = "";
    }
}

