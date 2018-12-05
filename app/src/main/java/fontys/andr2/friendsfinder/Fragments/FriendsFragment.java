package fontys.andr2.friendsfinder.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fontys.andr2.friendsfinder.MainActivity;
import fontys.andr2.friendsfinder.R;


public class FriendsFragment extends Fragment {

    ListView lv;

    public FriendsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        lv= (ListView) view.findViewById(R.id.friendView);
        lv.setAdapter(new customAdapter());

        return view;
    }

    class customAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View v = getLayoutInflater().inflate(R.layout.friends_item, null);
            //Log.e("jumjumjum", Integer.toString(array1.length()));

            TextView fullname = (TextView) v.findViewById(R.id.nameView);
            TextView online = (TextView) v.findViewById(R.id.onlineView);
            TextView city = (TextView) v.findViewById(R.id.cityView);
            fullname.setText("Gary");
            online.setText("Offline");
            city.setText("Eindhoven");

            return v;
        }
    }




}
