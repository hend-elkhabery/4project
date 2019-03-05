package com.example.hend.bakingapp.UI.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hend.bakingapp.Models.StepsModel;
import com.example.hend.bakingapp.R;
import com.example.hend.bakingapp.Utils.ConstantsUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class cooking_Details_Activity extends AppCompatActivity implements View.OnClickListener, OnStepClickListener {

    private boolean mTwoPane;
    List<StepsModel> ALstepModel;
    String mJsonResult;
    Button btnpre, btnnext;
    private int mVideoNumber = 0;


    FrameLayout frameLayout;
    FragmentManager fragmentManager;

    boolean boolwid;

    public static final String STEP_LIST_STATE = "step_list_state";
    public static final String STEP_NUMBER_STATE = "step_number_state";
    public static final String STEP_LIST_JSON_STATE = "step_list_json_state";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cook_details_activity);

        frameLayout = findViewById(R.id.fl_step_details_frag);


        if (findViewById(R.id.fl_step_details_frag) != null) {
            mTwoPane = true;
        } else {
            mTwoPane = false;
        }

        Intent intent = getIntent();

        if (intent.hasExtra(ConstantsUtil.STEP_INTENT_EXTRA)) {
            ALstepModel = getIntent().getParcelableArrayListExtra(ConstantsUtil.STEP_INTENT_EXTRA);
        }
        if (intent.hasExtra(ConstantsUtil.JSON_RESULT_EXTRA)) {
            mJsonResult = getIntent().getStringExtra(ConstantsUtil.JSON_RESULT_EXTRA);
        }
        if (intent.getStringExtra(ConstantsUtil.WIDGET_EXTRA) != null) {
            boolwid = true;
        } else {
            boolwid = false;
        }
        /*
        if (savedInstanceState == null) {
            playVideoReplace(ALstepModel.get(mVideoNumber));
        }

*/


        handleUiForDevice();


    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            ALstepModel = savedInstanceState.getParcelableArrayList(STEP_LIST_STATE);
            mJsonResult = savedInstanceState.getString(STEP_LIST_JSON_STATE);
            mVideoNumber = savedInstanceState.getInt(STEP_NUMBER_STATE);
        }
    }

    public void handleUiForDevice() {
        View recyclerView = findViewById(R.id.step_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (!mTwoPane) {

            Toast.makeText(this, "to the new activity ", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "replace container ", Toast.LENGTH_SHORT).show();

            step_details_Fragment detailFragment = new step_details_Fragment();

            Bundle bundle = new Bundle();
            detailFragment.setArguments(bundle);
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fl_step_details_frag, detailFragment)
                    .commit();

        }
    }

    @Override
    public void onClick(View v) {
        if (mVideoNumber == ALstepModel.size() - 1) {
            Toast.makeText(this, "done cooking", Toast.LENGTH_SHORT).show();
        }

        /*
        else {
            if (v.getId() == btnpre.getId()) {
                mVideoNumber--;
                if (mVideoNumber < 0) {
                    Toast.makeText(this, "see next step", Toast.LENGTH_SHORT).show();
                } else
                    playVideoReplace(ALstepModel.get(mVideoNumber));
            } else if (v.getId() == btnnext.getId()) {
                mVideoNumber++;
                playVideoReplace(ALstepModel.get(mVideoNumber));
            }
        }
        */
    }


    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new StepAdapter(this, ALstepModel, mVideoNumber, this));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }


    public void playVideoReplace(StepsModel step) {

        if (mTwoPane) {
            step_details_Fragment videoPlayerFragment = new step_details_Fragment();

            Bundle stepsBundle = new Bundle();
            stepsBundle.putParcelable(ConstantsUtil.ARG_STEP_POSITION, step);
            videoPlayerFragment.setArguments(stepsBundle);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fl_step_details_frag, videoPlayerFragment)
                    .addToBackStack(null)
                    .commit();

        } else {
            Intent intent = new Intent(getApplicationContext(), step_details_Activity.class);
            intent.putExtra(ConstantsUtil.STEP_SINGLE, step);
            startActivity(intent);
        }
    }


    @Override
    public void onStepClick(int position) {

        mVideoNumber = position;
        playVideoReplace(ALstepModel.get(position));
    }


    public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepsViewHolder> {
        Context context;
        List<StepsModel> stepsModels;
        private OnStepClickListener onStepClickListener;
        private int rowNo = 0;


        public StepAdapter(Context context, List<StepsModel> stepsModels, int rowNo, OnStepClickListener onStepClickListener) {
            this.context = context;
            this.stepsModels = stepsModels;
            this.rowNo = rowNo;
            this.onStepClickListener = onStepClickListener;

        }


        @NonNull
        @Override
        public StepAdapter.StepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(context).inflate(R.layout.single_step_layout, parent, false);
            final StepsViewHolder vh = new StepsViewHolder(view);


            return new StepAdapter.StepsViewHolder(view);

        }

        @Override
        public void onBindViewHolder(@NonNull final StepAdapter.StepsViewHolder holder, int position) {
            holder.tvstepshortdes.setText(stepsModels.get(position).getShortDescription());

            holder.tvstepnum.setText(String.valueOf(position + 1));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onStepClickListener.onStepClick(holder.getAdapterPosition());
                    rowNo = holder.getAdapterPosition();
                    notifyDataSetChanged();
                }
            });
            if (rowNo == position) {
                holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            }

        }

        @Override
        public int getItemCount() {
            return stepsModels.size();
        }


        public class StepsViewHolder extends RecyclerView.ViewHolder {
            TextView tvstepnum, tvstepshortdes;

            public StepsViewHolder(@NonNull View itemView) {
                super(itemView);

                this.tvstepnum = (TextView) itemView.findViewById(R.id.tvstepnum);
                this.tvstepshortdes = (TextView) itemView.findViewById(R.id.tvstepshortdes);

            }
        }


    }

}
