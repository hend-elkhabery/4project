package com.example.hend.bakingapp.ui.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.hend.bakingapp.Models.RecipeModel;
import com.example.hend.bakingapp.R;
import com.example.hend.bakingapp.ui.adapters.RecipeAdapter;
import com.example.hend.bakingapp.restApi.Client;
import com.example.hend.bakingapp.restApi.Service;
import com.google.gson.Gson;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    RecyclerView rvrecipes;
    RecipeAdapter RecipeAdapter;
    ArrayList<RecipeModel> ALrecipeModels;
    Service service;
    String mJsonResult;
    boolean booltab;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.mainactivity_tab) != null) {
            booltab = true;
        } else {
            booltab = false;
        }

        ALrecipeModels = new ArrayList<>();
        rvrecipes = (RecyclerView) findViewById(R.id.rvrecipes);
        RecipeAdapter = new RecipeAdapter(getApplicationContext(), ALrecipeModels, mJsonResult);
        if (booltab) {
            layoutManager = new GridLayoutManager(MainActivity.this, 2);
        } else {
            layoutManager = new LinearLayoutManager(MainActivity.this);
        }
        rvrecipes.setAdapter(RecipeAdapter);
        rvrecipes.setLayoutManager(layoutManager);

        service = new Client().mRecipeService;
        new LoadRecipesAsync().execute();
    }

    private void loadRecipes() {
        Call<ArrayList<RecipeModel>> call = service.getRecipes();
        call.enqueue(new Callback<ArrayList<RecipeModel>>() {
            @Override
            public void onResponse(Call<ArrayList<RecipeModel>> call, Response<ArrayList<RecipeModel>> response) {

                ALrecipeModels = response.body();
                mJsonResult = new Gson().toJson(response.body());

                RecipeAdapter = new RecipeAdapter(getApplicationContext(), ALrecipeModels, mJsonResult);
                rvrecipes.setLayoutManager(layoutManager);
                rvrecipes.setAdapter(RecipeAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<RecipeModel>> call, Throwable t) {
                Log.d("Failure", t.toString());

            }

        });
    }

    private class LoadRecipesAsync extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            loadRecipes();
            return null;
        }
    }
}
