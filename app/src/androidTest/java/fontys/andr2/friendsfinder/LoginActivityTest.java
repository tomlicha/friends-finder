package fontys.andr2.friendsfinder;

import android.net.Uri;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {
    private static final long CONNECT_TIME = 500;
    private final String VALID_EMAIL = "email@mock.com";
    private final String VALID_FIRSTNAME = "Mocked";
    private final String VALID_LASTNAME = "User";
    private final String VALID_DISPLAYNAME = VALID_FIRSTNAME + " " + VALID_LASTNAME;


    @Rule
    public IntentsTestRule<LoginActivity> mLoginActivityActivityTestRule =
            new IntentsTestRule<>(LoginActivity.class);

    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION);

    @Test
    public void startMapBtnInvisibleByDefault() {
        onView(withId(R.id.btn_start_map)).check(matches(not(isDisplayed())));
    }

    @Test
    public void startMapBtnVisibleAfterConnect() throws Throwable {
        mockConnect();
        TimeUnit.MILLISECONDS.sleep(CONNECT_TIME);
        onView(withId(R.id.btn_start_map)).check(matches(isDisplayed()));
    }

    @Test
    public void startMapBtnInvisibleAfterDisconnect() throws Throwable {
        mockConnect();
        TimeUnit.MILLISECONDS.sleep(CONNECT_TIME);
        onView(withId(R.id.sign_out_button)).perform(click());

        TimeUnit.MILLISECONDS.sleep(CONNECT_TIME);
        onView(withId(R.id.btn_start_map)).check(matches(not(isDisplayed())));
    }


    @Test
    public void assertUserConnect() throws Throwable {
        mockConnect();

        onView(withId(R.id.email_view)).check(matches(withText(containsString(VALID_EMAIL))));
        onView(withId(R.id.name_view)).check(matches(withText(containsString(VALID_DISPLAYNAME))));
        onView(withId(R.id.btn_start_map)).check(matches(isClickable()));
    }

    @Test
    public void launchMapAfterConnect() throws Throwable {
        mockConnect();
        TimeUnit.MILLISECONDS.sleep(CONNECT_TIME);
        onView(withId(R.id.btn_start_map)).perform(click());
        TimeUnit.MILLISECONDS.sleep(CONNECT_TIME);
        intended(hasComponent(MainActivity.class.getName()));
    }



    // END OF TEST

    private void mockConnect() throws Throwable {
        final GoogleSignInResult result = getMockGoogleSignInResult();

        mLoginActivityActivityTestRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mLoginActivityActivityTestRule.getActivity().handleSignInResult(result);
            }
        });
    }

    private GoogleSignInAccount getMockGoogleAccount() {
        GoogleSignInAccount account = mock(GoogleSignInAccount.class);
        when(account.getEmail()).thenReturn(VALID_EMAIL);
        when(account.getDisplayName()).thenReturn(VALID_DISPLAYNAME);
        when(account.getFamilyName()).thenReturn(VALID_LASTNAME);
        when(account.getGivenName()).thenReturn(VALID_FIRSTNAME);
        when(account.getPhotoUrl()).thenReturn(Uri.EMPTY);
        return account;
    }

    private GoogleSignInResult getMockGoogleSignInResult() {
        GoogleSignInAccount account = getMockGoogleAccount();

        GoogleSignInResult result = mock(GoogleSignInResult.class);
        when(result.isSuccess()).thenReturn(true);
        when(result.getSignInAccount()).thenReturn(account);
        return result;
    }
}