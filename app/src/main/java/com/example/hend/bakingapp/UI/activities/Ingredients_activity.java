package com.example.hend.bakingapp.UI.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hend.bakingapp.Models.IngredientModel;
import com.example.hend.bakingapp.Models.RecipeModel;
import com.example.hend.bakingapp.Models.StepsModel;
import com.example.hend.bakingapp.R;
import com.example.hend.bakingapp.UI.adapters.IngredientsAdapter;
import com.example.hend.bakingapp.Utils.ConstantsUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Ingredients_activity extends AppCompatActivity {

    RecyclerView rvingr;
    ArrayList<RecipeModel> ALRecipeModel;
    String strJsonResult;
    List<IngredientModel> ALIngredientModel;
    IngredientsAdapter ingredientsAdapter;

    ArrayList<StepsModel> mStepArrayList;
    String mJsonResult;
    public static final String RECIPE_JSON_STATE = "recipe_json_list";

    Button btnsteps;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingredients_activity);

        btnsteps = (Button) findViewById(R.id.btnsteps);
        rvingr = (RecyclerView) findViewById(R.id.rvingredients);

        Intent recipeIntent = getIntent();
        ALRecipeModel = recipeIntent.getParcelableArrayListExtra(ConstantsUtil.RECIPE_INTENT_EXTRA);
        strJsonResult = recipeIntent.getStringExtra(ConstantsUtil.JSON_RESULT_EXTRA);
        ALIngredientModel = ALRecipeModel.get(0).getIngredients();


        mStepArrayList = (ArrayList<StepsModel>) ALRecipeModel.get(0).getSteps();
        mJsonResult = savedInstanceState.getString(RECIPE_JSON_STATE);


        ingredientsAdapter = new IngredientsAdapter(getApplicationContext(), ALIngredientModel);
        rvingr.setAdapter(ingredientsAdapter);
        rvingr.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        btnsteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), cooking_Details_Activity.class);
                intent.putParcelableArrayListExtra(ConstantsUtil.STEP_INTENT_EXTRA, mStepArrayList);
                intent.putExtra(ConstantsUtil.JSON_RESULT_EXTRA, mJsonResult);
                startActivity(intent);

            }
        });

    }
}
