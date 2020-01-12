package com.example.c_o_you;

import android.util.Pair;

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

    public ResultItem(String title) {
        this.title = title;

        try {
            Pair<Double, String[]> pair = NLP.getIngredients(title);
            this.ingredients = pair.second;
            this.co2 = Math.round((float) pair.first.doubleValue() * 100) / 100f;
        } catch (Exception e) {
            e.printStackTrace();
        }
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
