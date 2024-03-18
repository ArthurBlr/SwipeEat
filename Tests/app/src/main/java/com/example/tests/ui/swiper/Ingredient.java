package com.example.tests.ui.swiper;

public class Ingredient {
    private String name;
    private int imageUrl;

    public Ingredient(String name, int imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public int getImageUrl() {
        return imageUrl;
    }
}
