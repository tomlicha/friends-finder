package fontys.andr2.friendsfinder;

import android.graphics.Bitmap;
import android.net.Uri;


public class User{
    private String profilePictureUri;
    private String name;
    private String email;

    public String getProfilePicture() {
        return profilePictureUri;
    }

    public void setProfilePicture(String profilePicture) {
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
    }

    public User() {
    }
}
