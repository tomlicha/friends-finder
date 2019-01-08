package fontys.andr2.friendsfinder.Users;

import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;

import static org.junit.Assert.*;

public class UserTest {

    private User defaultUser;
    private final String VALID_NAME = "Vinh";
    private final String VALID_EMAIL = "n4truong@gmail.com";
    private final String VALID_URL = "https://www.google.nl/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png";
    private final double VALID_LATITUDE = 10.0;
    private final double VALID_LONGITUDE = 10.0;


    @Before
    public void setUp() {
        defaultUser = new User(VALID_URL, VALID_NAME, VALID_EMAIL, VALID_LATITUDE, VALID_LONGITUDE);
    }

    //----------------------------------------------------------------//

    @Test
    public void userHasName(){
        assertEquals("The defaultUser has not a VALID_NAME", VALID_NAME, defaultUser.getName());
    }

    @Test
    public void userHasEmail() {
        assertEquals("The defaultUser has not an VALID_EMAIL", VALID_EMAIL, defaultUser.getEmail());
    }

    @Test
    public void  userHasLatitudeAndLongitude() {
        assertEquals("The defaultUser has not a VALID_LATITUDE", VALID_LATITUDE, defaultUser.getLatitude(),0);
        assertEquals("The defaultUser has not a VALID_LONGITUDE", VALID_LONGITUDE, defaultUser.getLongitude(),0);
    }

    @Test
    public void longitudeAndLatitudeUpdateOK() {
        double newLatitude = 20.0;
        double newLongitude = 20.0;
        defaultUser.updateMyPosition(newLatitude,newLongitude);
        assertEquals("The VALID_LATITUDE's defaultUser has not been updated",newLatitude, defaultUser.getLatitude(),0);
        assertEquals("The VALID_LONGITUDE's defaultUser has not been updated",newLongitude, defaultUser.getLongitude(),0);
    }

    //----------------------------------------------------------------//



}