package com.example.tests.ui.eat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tests.R;

import java.util.ArrayList;
import java.util.function.Consumer;

public class DayMealAdapter extends RecyclerView.Adapter<DayMealAdapter.ViewHolder> {

    private ArrayList<DayMeal> dayMeals;
    private Consumer<Integer> onDayRemovedListener;

    public DayMealAdapter(ArrayList<DayMeal> dayMeals, Consumer<Integer> onDayRemovedListener) {
        this.dayMeals = dayMeals;
        this.onDayRemovedListener = onDayRemovedListener;
    }

    @NonNull
    @Override
    public DayMealAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DayMeal dayMeal = dayMeals.get(position);
        holder.dayTitle.setText(dayMeal.getDayTitle());

        // Utilisation de Consumer<Integer> pour gérer le clic de suppression
        holder.removeDayButton.setOnClickListener(v -> onDayRemovedListener.accept(position));
    }

    @Override
    public int getItemCount() {
        return dayMeals.size();
    }

    public void setDayMeals(ArrayList<DayMeal> dayMeals) {
        this.dayMeals = dayMeals;
        notifyDataSetChanged(); // Notifie que les données ont changé
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dayTitle;
        View removeDayButton;

        public ViewHolder(View itemView) {
            super(itemView);
            dayTitle = itemView.findViewById(R.id.dayTitle);
            removeDayButton = itemView.findViewById(R.id.removeDayButton);
        }
    }

    public void addDayMeal(DayMeal dayMeal) {
        dayMeals.add(dayMeal);
        notifyItemInserted(dayMeals.size() - 1);
    }

    public void removeDayMeal(int position) {
        if (position >= 0 && position < dayMeals.size()) {
            dayMeals.remove(position);
            notifyItemRemoved(position);
        }
    }
}
