package com.example.tests.ui.eat;

public class DayMeal {
    private String dayTitle;
    private int mealId;

    public DayMeal(String dayTitle) {
        this.dayTitle = dayTitle;
        this.mealId = 0;
    }

    public DayMeal(String mealTitle, int mealId ) {
        this.dayTitle = mealTitle;
        this.mealId = mealId;
    }

    public String getDayTitle() {
        return dayTitle;
    }

    public int getMealId() {
        return mealId;
    }

    public void setDayTitle(String dayTitle) {
        this.dayTitle = dayTitle;
    }

    public void setMealId(int mealId) {
        this.mealId = mealId;
    }

    @Override
    public String toString() {
        return dayTitle + " " + mealId;
    }
}
