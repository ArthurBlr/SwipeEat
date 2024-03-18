package com.example.tests.ui.likedislike;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tests.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {

    private Map<String, Boolean> ingredients;
    private OnIngredientSwitchChanged listener;

    public IngredientsAdapter(Map<String, Boolean> ingredients, OnIngredientSwitchChanged listener) {
        this.ingredients = ingredients;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        List<String> ingredients = new ArrayList<>(this.ingredients.keySet());
        String ingredient = ingredients.get(position);
        holder.ingredientName.setText(ingredient);
        holder.ingredientSwitch.setOnCheckedChangeListener(null);
        // Mettre à jour l'état du switch si nécessaire
        holder.ingredientSwitch.setChecked(this.ingredients.get(ingredient));
        holder.ingredientSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(listener != null) {
                listener.onSwitchChanged(ingredient, isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView ingredientName;
        SwitchCompat ingredientSwitch;

        ViewHolder(View view) {
            super(view);
            ingredientName = view.findViewById(R.id.ingredient_name);
            ingredientSwitch = view.findViewById(R.id.ingredient_switch);
        }
    }

    public interface OnIngredientSwitchChanged {
        void onSwitchChanged(String ingredient, boolean isChecked);
    }

    public void updateIngredients(Map<String, Boolean> newIngredients) {
        this.ingredients.clear();
        this.ingredients.putAll(newIngredients);
        notifyDataSetChanged(); // Avertir l'adapter d'un changement de données pour rafraîchir la vue
    }

}
