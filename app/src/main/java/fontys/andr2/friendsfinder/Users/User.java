package fontys.andr2.friendsfinder.Users;

import android.graphics.Bitmap;

public class User{
    private byte[] profilePicture;
    private String name;
    private String email;
    private double latitude;
    private double longitude;


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
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

    public User(byte[] profilePicture, String name, String email) {

        this.profilePicture = profilePicture;
        this.name = name;
        this.email = email;
        this.latitude=0.0;
        this.longitude=0.0;
    }

    public User() {
    }
}
