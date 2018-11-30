package fontys.andr2.friendsfinder.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.owlike.genson.Genson;

import fontys.andr2.friendsfinder.MainActivity;
import fontys.andr2.friendsfinder.R;
import fontys.andr2.friendsfinder.User;


public class ProfileFragment extends Fragment {
    private Genson genson= new Genson();
    private User UserData;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LayoutInflater lf = getActivity().getLayoutInflater();
        View view =  lf.inflate(R.layout.fragment_profile, container, false);


                SharedPreferences sharedPref = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
                String teste = sharedPref.getString("userData", "null");
                Log.d("user created:", teste);


                    UserData = genson.deserialize(teste, User.class);

        TextView textViewEmail = (TextView) view.findViewById(R.id.emailFragmentProfile);
        textViewEmail.setText(UserData.getEmail());
        TextView textViewName = (TextView) view.findViewById(R.id.nameFragmentProfile);
        textViewName.setText(UserData.getName());
        ImageView profilePicture = (ImageView) view.findViewById(R.id.profilePictureFragment);
        profilePicture.setImageBitmap(BitmapFactory.decodeByteArray(UserData.getProfilePicture(), 0, UserData.getProfilePicture().length));
        return view;
    }

}
