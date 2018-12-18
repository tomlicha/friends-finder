package fontys.andr2.friendsfinder.Users;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    private User defaultUser;
    private final String name = "Vinh";
    private final String email = "n4truong@gmail.com";
    private final double latitude = 10.0;
    private final double longitude = 10.0;


    @Before
    public void setUp() throws ProfilePictureUriException {
        defaultUser = new User("https://www.google.nl/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png",  name,  email,  latitude,  longitude);
    }

    //----------------------------------------------------------------//

    @Test
    public void userHasName() throws ProfilePictureUriException {
        assertEquals("The defaultUser has not a name", name, defaultUser.getName());
    }

    @Test
    public void userHasEmail() throws ProfilePictureUriException {
        assertEquals("The defaultUser has not an email", email, defaultUser.getEmail());
    }

    @Test
    public void  userHasLatitudeAndLongitude() throws ProfilePictureUriException {
        assertEquals("The defaultUser has not a latitude",latitude, defaultUser.getLatitude(),0);
        assertEquals("The defaultUser has not a longitude",longitude, defaultUser.getLongitude(),0);
    }

    @Test
    public void longitudeAndLagitudeUpdateOK() throws ProfilePictureUriException {
        User user = new User("",  name,  email,  latitude,  longitude);
        double newLatitude = 20.0;
        double newLongitude = 20.0;
        user.updateMyPosition(newLatitude,newLongitude);
        assertEquals("The latitude's defaultUser has not been updated",newLatitude, user.getLatitude(),0);
        assertEquals("The longitude's defaultUser has not been updated",newLongitude, user.getLongitude(),0);
    }

    @Test(expected = ProfilePictureUriException.class)
    public void profilePictureUriIsWebURL()throws ProfilePictureUriException{
        new User("test",  name,  email,  latitude,  longitude);
    }


}