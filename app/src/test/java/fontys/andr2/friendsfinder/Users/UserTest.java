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
    public void setUp() {
        defaultUser = new User("",  name,  email,  latitude,  longitude);
    }

    @Test
    public void userHasName() {
    }

    @Test
    public void userHasEmail(){

    }

    @Test
    public void  userHasLatitudeAndLongitude(){

    }

    @Test
    public void LongitudeAndLagitudeUpdateOK(){

    }

    @Test
    public void  profilePictureUriIsWebURL(){

    }
}