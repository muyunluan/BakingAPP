package com.muyunluan.bakingapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.muyunluan.bakingapp.R;

public class StepDetailsActivity extends AppCompatActivity {

    private static final String TAG = StepDetailsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);

        // Only create new fragments when there is no previously saved state
        if (null == savedInstanceState) {
            StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
            if (null != getIntent()) {
                Bundle args = new Bundle();
                if (getIntent().hasExtra("position")) {
                    //Log.i(TAG, "onCreate: has desired info");
                    args.putInt("position", getIntent().getExtras().getInt("position"));
                    args.putParcelableArrayList("steps",
                            getIntent().getExtras().getParcelableArrayList("steps"));
                    stepDetailsFragment.setArguments(args);
                } else {
                    Log.e(TAG, "onCreate: no desired info found");
                }
            } else {
                Log.e(TAG, "onCreate: empty Bundle for Step object");
            }

            getSupportFragmentManager().beginTransaction().add(
                    R.id.frame_step_details,
                    stepDetailsFragment).commit();

        }
    }

}
