package fontys.andr2.friendsfinder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.Status;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private static final int RC_SIGN_IN = 9001;

    private GoogleApiClient googleApiClient;

    private ImageView profilePic;
    private TextView fullnameTextView;
    private TextView emailTextView;

    private String email,fullname;
    private Bitmap profilePicture;
    //Used for OAuth - End

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Used for OAuth - Start
        //Show Token
        profilePic = findViewById(R.id.profile_image);
        fullnameTextView = findViewById(R.id.name_view);
        emailTextView = findViewById(R.id.email_view);


        //OnClickListener
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);


        //Sign-In-token
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setColorScheme(SignInButton.COLOR_LIGHT);

        //Used for OAuth - End
    }

    void launchMapActivity() {

        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        if (profilePicture!=null) profilePicture.compress(Bitmap.CompressFormat.PNG, 100, bStream);
        byte[] byteArray = bStream.toByteArray();

        Intent intent = new Intent(this, MainActivity.class);
        Log.d("\nintent extras : ",email + fullname);
        intent.putExtra("email",email);
        intent.putExtra("name",fullname);
        intent.putExtra("profilePicture",byteArray);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_map:
                launchMapActivity();
                break;
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.sign_out_button:
                signOut();
                break;
        }
    }

    //Used for OAuth - Start


    //OnStart
    public void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        //updateUI(account);
    }


    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("result code ", Integer.toString(resultCode));

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Log.d("call function handlesigninresult", "");
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("result received :", " " + result.isSuccess());
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            Log.d("result received :", " " + account.getDisplayName() + account.getEmail() + account.getFamilyName() + account.getGivenName());
            email =account.getEmail();

            fullname = account.getDisplayName();
            emailTextView.setText("email : " + account.getEmail());
            fullnameTextView.setText("Hello "+account.getDisplayName());
            emailTextView.setVisibility(View.VISIBLE);
            fullnameTextView.setVisibility(View.VISIBLE);
            Uri personPhoto = account.getPhotoUrl();
            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);
            if (personPhoto != null) {
                final ImageView imgView = profilePic;
                // Download photo and set to image
                Context context = imgView.getContext();
                Picasso.with(context).load(personPhoto).into(imgView);
                Picasso.with(this)
                        .load(personPhoto)
                        .into(imgView, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                profilePicture = ((BitmapDrawable)imgView.getDrawable()).getBitmap();

                            }

                            @Override
                            public void onError() {

                            }


                        });
            }


        } else {
            Log.d("login failed :", " " + result.getStatus());
        }

    }


    private void signOut () {

        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallbacks<Status>() {
            @Override
            public void onSuccess(@NonNull Status status) {
               Toast.makeText(getApplication(),"Successfuly signed out",Toast.LENGTH_SHORT).show();
                emailTextView.setVisibility(View.GONE);
                fullnameTextView.setVisibility(View.GONE);
                findViewById(R.id.sign_out_button).setVisibility(View.GONE);
                findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
                profilePic.setImageResource(0);

            }

            @Override
            public void onFailure(@NonNull Status status) {
                Log.e("statut : ",status.toString());
            }
        });
    }


            @Override
            public void onConnectionFailed (@NonNull ConnectionResult connectionResult){

            }

            @Override
            public void onPointerCaptureChanged ( boolean hasCapture){

            }


        }

