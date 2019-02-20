package com.example.hend.bakingapp.restApi;

import com.example.hend.bakingapp.Models.RecipeModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Service {

     @GET("baking.json")
    Call<ArrayList<RecipeModel>> getRecipes();
}
