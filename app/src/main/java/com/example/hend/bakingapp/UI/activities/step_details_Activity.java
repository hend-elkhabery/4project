package com.example.hend.bakingapp.ui.activities;

import android.os.Bundle;
import android.widget.Toast;

import com.example.hend.bakingapp.Models.StepsModel;
import com.example.hend.bakingapp.R;
import com.example.hend.bakingapp.Utils.ConstantsUtil;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

public class step_details_Activity extends AppCompatActivity {
    private static final String TAG = step_details_Activity.class.getSimpleName();
    FragmentManager fragmentManager;
    List<StepsModel> ALstepModel;
    private int mVideoNumber = 0;
    StepsModel step;
    String mJsonResult;
    Bundle stepsBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details_);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        StepDetailsFragment detailFragment = new StepDetailsFragment();
        step = getIntent().getParcelableExtra(ConstantsUtil.STEP_SINGLE);
        stepsBundle = new Bundle();
        stepsBundle.putParcelable(ConstantsUtil.ARG_STEP_POSITION, step);
        detailFragment.setArguments(stepsBundle);


        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fl2_step_details_frag, detailFragment)
                .commit();
        Toast.makeText(this, "this is it ", Toast.LENGTH_LONG).show();
    }

}
