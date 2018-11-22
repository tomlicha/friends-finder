package fontys.andr2.friendsfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    void launchMapActivity(){
        startActivity(new Intent(this, MapsActivity.class));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start_map:
                launchMapActivity();
                break;
        }
    }
}
