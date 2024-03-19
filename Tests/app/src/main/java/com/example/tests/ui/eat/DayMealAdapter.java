package com.example.tests.ui.eat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tests.R;

import java.util.ArrayList;

public class DayMealAdapter extends RecyclerView.Adapter<DayMealAdapter.ViewHolder> {

    private ArrayList<DayMeal> dayMeals;
    private OnDayRemovedListener listener;

    public interface OnDayRemovedListener {
        void onDayRemoved(int position);
    }

    public DayMealAdapter(ArrayList<DayMeal> dayMeals, OnDayRemovedListener listener) {
        this.dayMeals = dayMeals;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DayMealAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return dayMeals.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DayMeal dayMeal = dayMeals.get(position);
        holder.dayTitle.setText(dayMeal.getDayTitle());

        holder.removeDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null) {
                    listener.onDayRemoved(position);
                }
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView dayTitle;
        public View removeDayButton;

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
}
