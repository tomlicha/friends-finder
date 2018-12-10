package fontys.andr2.friendsfinder.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.owlike.genson.Genson;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

import fontys.andr2.friendsfinder.R;
import fontys.andr2.friendsfinder.Users.User;


public class ProfileFragment extends Fragment {
    private Genson genson= new Genson();
    private User UserData;
    ImageView profilePicture;
    private Bitmap profilePictureBitmap;
    byte[] byteArray;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LayoutInflater lf = Objects.requireNonNull(getActivity()).getLayoutInflater();
        View view =  lf.inflate(R.layout.fragment_profile, container, false);
        TextView textViewEmail = (TextView) view.findViewById(R.id.emailFragmentProfile);
        TextView textViewName = (TextView) view.findViewById(R.id.nameFragmentProfile);

        profilePicture = (ImageView) view.findViewById(R.id.profilePictureFragment);

        SharedPreferences sharedPref = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        String teste = sharedPref.getString("userData", "null");
        Log.d("user created:", teste);


        UserData = genson.deserialize(teste, User.class);
        textViewName.setText(UserData.getName());
        textViewEmail.setText(UserData.getEmail());

        Picasso.with(getActivity())
                .load(Uri.parse(UserData.getProfilePicture()))
                .into(profilePicture, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        profilePictureBitmap = ((BitmapDrawable)profilePicture.getDrawable()).getBitmap();
                        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
                        profilePictureBitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
                        byteArray = bStream.toByteArray();
                    }

                    @Override
                    public void onError() {

                    }
                });

        return view;
    }

}
