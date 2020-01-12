package com.example.c_o_you;

import java.io.Serializable;

public class ResultItem implements Serializable {

    private String title;
    private String[] ingredients;
    private float co2;

    public ResultItem(String title, String[] ingredients, float co2kilos) {
        this.title = title;
        this.ingredients = ingredients;
        this.co2 = co2kilos;
    }

    public String getTitle() {
        return title;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public float getCo2() {
        return co2;
    }
}
