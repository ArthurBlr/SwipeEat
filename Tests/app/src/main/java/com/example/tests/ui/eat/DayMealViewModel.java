package com.example.tests.ui.eat;

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
            // Renomme les jours restants pour refléter leur nouvel ordre
            for (int i = position; i < currentMeals.size(); i++) {
                String updatedDayTitle = "Jour " + (i + 1);
                currentMeals.get(i).setDayTitle(updatedDayTitle);
            }
            dayMeals.setValue(currentMeals); // Met à jour LiveData avec la liste modifiée
        }
    }
}
