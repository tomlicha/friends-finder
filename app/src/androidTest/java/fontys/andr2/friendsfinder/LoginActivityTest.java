package fontys.andr2.friendsfinder;

import android.content.Intent;
import android.net.Uri;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public IntentsTestRule<LoginActivity> mLoginActivityActivityTestRule =
            new IntentsTestRule<>(LoginActivity.class);


    @Test
    public void startMapActivity() {
        onView(withId(R.id.btn_start_map)).perform(click());
        intended(hasComponent(MainActivity.class.getName()));
    }

    @Test
    public void assertUserConnect() throws Throwable {
        final String VALID_EMAIL = "email@mock.com";
        final String VALID_FIRSTNAME = "Mocked";
        final String VALID_LASTNAME = "User";
        final String VALID_DISPLAYNAME = VALID_FIRSTNAME + " " + VALID_LASTNAME;

        GoogleSignInAccount account = mock(GoogleSignInAccount.class);
        when(account.getEmail()).thenReturn(VALID_EMAIL);
        when(account.getDisplayName()).thenReturn(VALID_DISPLAYNAME);
        when(account.getFamilyName()).thenReturn(VALID_LASTNAME);
        when(account.getGivenName()).thenReturn(VALID_FIRSTNAME);
        when(account.getPhotoUrl()).thenReturn(Uri.EMPTY);

        final GoogleSignInResult result = mock(GoogleSignInResult.class);
        when(result.isSuccess()).thenReturn(true);
        when(result.getSignInAccount()).thenReturn(account);

        mLoginActivityActivityTestRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mLoginActivityActivityTestRule.getActivity().handleSignInResult(result);
            }
        });
        onView(withId(R.id.email_view)).check(matches(withText(containsString(VALID_EMAIL))));
        onView(withId(R.id.name_view)).check(matches(withText(containsString(VALID_DISPLAYNAME))));
    }
}