package com.example.hend.bakingapp.UI.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hend.bakingapp.Models.IngredientModel;
import com.example.hend.bakingapp.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {
    Context context;
    List<IngredientModel> ingredientModels;

    public IngredientsAdapter(Context context, List<IngredientModel> ingredientModels) {
        this.context = context;
        this.ingredientModels = ingredientModels;
    }

    @NonNull
    @Override
    public IngredientsAdapter.IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ingredients_card, parent, false);
        return new IngredientsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsAdapter.IngredientsViewHolder holder, int position) {
        IngredientModel ingredientModel = ingredientModels.get(position);

        holder.tvingname.setText(ingredientModel.getIngredient());
        holder.tvingquantity.setText(String.valueOf(ingredientModel.getQuantity()));
        holder.tvingnum.setText(String.valueOf(position + 1));
        holder.tvingmeasure.setText(String.valueOf(ingredientModel.getMeasure()));
    }

    @Override
    public int getItemCount() {
        return ingredientModels.size();
    }

    public class IngredientsViewHolder extends RecyclerView.ViewHolder {
        private TextView tvingname, tvingnum, tvingquantity, tvingmeasure;

        public IngredientsViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvingname = (TextView) itemView.findViewById(R.id.tvingredientname);
            this.tvingnum = itemView.findViewById(R.id.tvingredientnum);
            this.tvingquantity = itemView.findViewById(R.id.tvingredientquantity);
            this.tvingmeasure = itemView.findViewById(R.id.tvingredientmeasure);

        }
    }
}
