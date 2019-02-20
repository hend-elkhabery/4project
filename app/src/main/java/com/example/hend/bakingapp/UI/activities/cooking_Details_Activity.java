package com.example.hend.bakingapp.UI.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.hend.bakingapp.Models.RecipeModel;
import com.example.hend.bakingapp.Models.StepsModel;
import com.example.hend.bakingapp.R;
import com.example.hend.bakingapp.Utils.ConstantsUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class cooking_Details_Activity extends AppCompatActivity  {

    private boolean mTwoPane;
    ArrayList<RecipeModel> ALRecipeModel;
    List<StepsModel> ALstepModel;
    String mJsonResult;
    Button btnpre, btnnext;
    private int mVideoNumber = 0;

    //private int mRecipeIndex = 0;
    //private int stepsModels = 0;

    FragmentManager fragmentManager;

    steps_Fragment frga_steps;
    step_details_Fragment frg_steps_details;
    boolean boolwid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cook_details_activity);

        if (findViewById(R.id.step_details_frag) != null) {
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
        if(intent.getStringExtra(ConstantsUtil.WIDGET_EXTRA) != null){
            boolwid = true;
        }
        else{
            boolwid = false;
        }


        if (savedInstanceState == null) {
            //playVideo(mStepArrayList.get(mVideoNumber));
        }

/*
        Intent stepsintent = getIntent();
        ALRecipeModel = stepsintent.getParcelableArrayListExtra(ConstantsUtil.STEP_ARRAYLIST);
        // stepsModels = ALRecipeModel.get(0).getSteps();

        if (savedInstanceState == null) {

            fragmentManager = getSupportFragmentManager();

            if (findViewById(R.id.step_details_frag) != null) {
                mTwoPane = true;

                step_details_Fragment stepDetailFragment = new step_details_Fragment();

                fragmentManager.beginTransaction()
                        .add(R.id.step_details_frag, stepDetailFragment)
                        .commit();
            } else {
                mTwoPane = false;
            }


        }

*/

        handleUiForDevice();


    }

    public void handleUiForDevice() {
        View recyclerView = findViewById(R.id.step_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (!mTwoPane) {
            Intent intent = new Intent(getApplicationContext(), step_details_Activity.class);
            //  intent.putParcelableArrayListExtra(ConstantsUtil.STEP_INTENT_EXTRA, stepsModels);
            intent.putExtra("start", 1);
            startActivity(intent);

        } else {
            step_details_Fragment detailFragment = new step_details_Fragment();
            Bundle bundle = new Bundle();
            detailFragment.setArguments(bundle);

            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.step_details_frag, detailFragment)
                    .commit();

        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new StepAdapter(this, ALstepModel,mVideoNumber ));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    /*
        @Override
        public void onStepSelected(int stepIndex) {
            stepsModels = ALRecipeModel.get(stepIndex).getSteps();
            if (mTwoPane) {
                Toast.makeText(this, "hhhhhhhhhhhh", Toast.LENGTH_LONG).show();

                step_details_Fragment detailFragment = new step_details_Fragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("step", (Parcelable) stepsModels);
                detailFragment.setArguments(bundle);

                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.step_details_frag, detailFragment)
                        .commit();
            } else {
                Intent intent = new Intent(getApplicationContext(), step_details_Activity.class);
                //  intent.putParcelableArrayListExtra(ConstantsUtil.STEP_INTENT_EXTRA, stepsModels);
                intent.putExtra("start", 1);
                startActivity(intent);
            }
        }

    */
    public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepsViewHolder> implements  OnStepClickListener {
        Context context;
        List<StepsModel> stepsModels;
        public OnStepClickListener mOnStepClick;
        private int rowNo = 0;


        public StepAdapter(Context context, List<StepsModel> stepsModels, int rowNo) {
            this.context = context;
            this.stepsModels = stepsModels;
            this.rowNo = rowNo;

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
                    mOnStepClick.onStepClick(holder.getAdapterPosition());
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

        @Override
        public void onStepClick(int position) {

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
