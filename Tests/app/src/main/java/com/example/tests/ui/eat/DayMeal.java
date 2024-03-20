package com.example.tests.ui.eat;

public class DayMeal {

    private String dayTitle;
    private String breakfastName;
    private int breakfastId;
    private String lunchName;
    private int lunchId;
    private String dinnerName;
    private int dinnerId;

    public DayMeal(String dayTitle) {
        this.dayTitle = dayTitle;

        this.breakfastName = "Matin";
        this.lunchName = "Midi";
        this.dinnerName = "Soir";

        this.breakfastId = 0;
        this.lunchId = 0;
        this.dinnerId = 0;
    }

    public String getDayTitle() {
        return dayTitle;
    }

    public void setDayTitle(String dayTitle) {
        this.dayTitle = dayTitle;
    }

    public String getBreakfastName() {
        return breakfastName;
    }

    public void setBreakfastName(String breakfastName) {
        this.breakfastName = breakfastName;
    }

    public int getBreakfastId() {
        return breakfastId;
    }

    public void setBreakfastId(int breakfastId) {
        this.breakfastId = breakfastId;
    }

    public String getLunchName() {
        return lunchName;
    }

    public void setLunchName(String lunchName) {
        this.lunchName = lunchName;
    }

    public int getLunchId() {
        return lunchId;
    }

    public void setLunchId(int lunchId) {
        this.lunchId = lunchId;
    }

    public String getDinnerName() {
        return dinnerName;
    }

    public void setDinnerName(String dinnerName) {
        this.dinnerName = dinnerName;
    }

    public int getDinnerId() {
        return dinnerId;
    }

    public void setDinnerId(int dinnerId) {
        this.dinnerId = dinnerId;
    }

    public void resetMeals() {
        this.breakfastName = "Matin";
        this.lunchName = "Midi";
        this.dinnerName = "Soir";
        this.breakfastId = 0;
        this.lunchId = 0;
        this.dinnerId = 0;
    }

    @Override
    public String toString() {
        return "DayMeal{" +
                "dayTitle='" + dayTitle + '\'' +
                ", breakfastName='" + breakfastName + '\'' +
                ", lunchName='" + lunchName + '\'' +
                ", dinnerName='" + dinnerName + '\'' +
                '}';
    }

}
