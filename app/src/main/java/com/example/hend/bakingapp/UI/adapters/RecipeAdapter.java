package com.example.hend.bakingapp.UI.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hend.bakingapp.Models.RecipeModel;
import com.example.hend.bakingapp.R;
import com.example.hend.bakingapp.UI.activities.Ingredients_activity;
import com.example.hend.bakingapp.Utils.ConstantsUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    Context context;
    ArrayList<RecipeModel> recipeModels;
    String recipeJson;
    String mJsonResult;

    public RecipeAdapter(Context context, ArrayList<RecipeModel> recipeModels , String mJsonResult) {
        this.context = context;
        this.recipeModels = recipeModels;
        this.mJsonResult = mJsonResult;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.recipe_list_item, viewGroup, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecipeViewHolder recipeViewHolder, int i) {

        recipeViewHolder.tvrecipe.setText(recipeModels.get(i).getName());

        recipeViewHolder.ivrecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RecipeModel recipeModel = recipeModels.get(recipeViewHolder.getAdapterPosition());
                recipeJson = jsonToString(mJsonResult, recipeViewHolder.getAdapterPosition());
                Intent intent = new Intent(context, Ingredients_activity.class);
                ArrayList<RecipeModel> alRecipeModel = new ArrayList<>();
                alRecipeModel.add(recipeModel);
                intent.putParcelableArrayListExtra(ConstantsUtil.RECIPE_INTENT_EXTRA, alRecipeModel);
                intent.putExtra(ConstantsUtil.JSON_RESULT_EXTRA, recipeJson);
                context.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return recipeModels.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Context context;
        private List<RecipeModel> RecipeModel;
        private ImageView ivrecipe;
        private TextView tvrecipe;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            this.context = context;
            this.RecipeModel = RecipeModel;
            this.tvrecipe = (TextView) itemView.findViewById(R.id.tvrecipe);
            this.ivrecipe = itemView.findViewById(R.id.ivrecipe);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

        }
    }
    private String jsonToString(String jsonResult, int position){
        JsonElement jsonElement = new JsonParser().parse(jsonResult);
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        JsonElement recipeElement = jsonArray.get(position);
        return recipeElement.toString();
    }

}
