package com.example.tests.ui.eat;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;

public class DayMealViewModel extends ViewModel {
    private MutableLiveData<ArrayList<DayMeal>> dayMeals;

    public LiveData<ArrayList<DayMeal>> getDayMeals() {
        if (dayMeals == null) {
            dayMeals = new MutableLiveData<>(new ArrayList<>());
        }
        return dayMeals;
    }

    public void addDayMeal(DayMeal dayMeal) {
        ArrayList<DayMeal> currentMeals = dayMeals.getValue();
        if (currentMeals != null) {
            currentMeals.add(dayMeal);
            dayMeals.setValue(currentMeals);
        }
    }

    public void removeDayMeal(int position) {
        ArrayList<DayMeal> currentMeals = dayMeals.getValue();
        if (currentMeals != null && position >= 0 && position < currentMeals.size()) {
            currentMeals.remove(position);
            for (int i = position; i < currentMeals.size(); i++) {
                DayMeal dayMeal = currentMeals.get(i);
                dayMeal.setDayTitle("Jour " + (i + 1));
            }
            dayMeals.setValue(currentMeals);
        }
    }

}
