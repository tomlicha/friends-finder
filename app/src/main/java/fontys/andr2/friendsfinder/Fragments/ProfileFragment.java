package fontys.andr2.friendsfinder.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import fontys.andr2.friendsfinder.MainActivity;
import fontys.andr2.friendsfinder.R;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LayoutInflater lf = getActivity().getLayoutInflater();

        View view =  lf.inflate(R.layout.fragment_profile, container, false);
        TextView textViewEmail = (TextView) view.findViewById(R.id.emailFragmentProfile);
        textViewEmail.setText(MainActivity.email);
        TextView textViewName = (TextView) view.findViewById(R.id.nameFragmentProfile);
        textViewName.setText(MainActivity.name);
        ImageView profilePicture = (ImageView) view.findViewById(R.id.profilePictureFragment);
        profilePicture.setImageBitmap(MainActivity.profilePicture);
        return view;
    }

}
