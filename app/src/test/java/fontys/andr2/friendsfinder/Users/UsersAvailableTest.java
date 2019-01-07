package fontys.andr2.friendsfinder.Users;

import com.google.firebase.database.DatabaseReference;

import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class UsersAvailableTest {

    private User valid_user;
    private User defaultUser2;
    private final String VALID_NAME = "Vinh";
    private final String VALID_EMAIL = "n4truong@gmail.com";
    private final String VALID_URL = "https://www.google.nl/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png";
    private final double VALID_LATITUDE = 10.0;
    private final double VALID_LONGITUDE = 10.0;

    private UsersAvailable defaultUserAvailable;
    private HashMap<String,User> usersAvailable ;

    @Before
    public void setUp() throws MalformedURLException {
        defaultUserAvailable = new UsersAvailable();
        valid_user = new User(VALID_URL, VALID_NAME, VALID_EMAIL, VALID_LATITUDE, VALID_LONGITUDE);
        defaultUser2 = new User(VALID_URL, "Manach", "b4manach@gmail.com", 10.0, 10.0);
    }

    //----------------------------------------------------------------//

    @Test
    public void userListIsNotNull(){
        assertNotEquals("The list of user is null",null, defaultUserAvailable.getAvailable());
    }

    @Test
    public void userCanBeAddToList(){
        defaultUserAvailable.addUser(valid_user);
        defaultUserAvailable.addUser(defaultUser2);
        assertEquals("Impossible to add course", 2, defaultUserAvailable.getNumberOfUsers());
    }

    @Test
    public void userCanBeRetrievedFromEmail(){
        defaultUserAvailable.addUser(valid_user);
        assertEquals("Can not retrieved user from email", valid_user, defaultUserAvailable.getUser(VALID_EMAIL));
    }

    @Test
    public void verifyThatUserAvailableClearInSetRefreshMethod() {
        HashMap<String,User> usersAvailable = spy(new HashMap<String,User>());
        defaultUserAvailable.setUsersAvailable(usersAvailable);
        DatabaseReference dummyDb = mock(DatabaseReference.class);

        defaultUserAvailable.setRefresh(dummyDb);
        verify(usersAvailable, times(1)).clear();
    }

    @Test
    public void SetRefreshListenerIsCalled(){

        UsersAvailable.RefreshListener refreshListener = new UsersAvailable.RefreshListener() {
            @Override
            public void onRefresh() {

            }
        };
        defaultUserAvailable.setRefreshListener(refreshListener);

        assertEquals("the refresh listener is not the same", refreshListener  , defaultUserAvailable.getRefreshListener() );
    }

    @Test
    public void listenerCanBeSet(){
        //listener not equal to null
        assertNull("The refreshListener is not null when not implemented", defaultUserAvailable.getRefreshListener());
        defaultUserAvailable.setRefreshListener(new UsersAvailable.RefreshListener() {
            @Override
            public void onRefresh() {
                //Nothing
            }
        });
        assertNotEquals("The refreshListener should not be null when implemented", null, defaultUserAvailable.getRefreshListener());
    }

}