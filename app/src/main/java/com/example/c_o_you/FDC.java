package com.example.c_o_you;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class FDC {

    public static void getIngredients(String term) throws Exception {
        StringBuilder result = new StringBuilder();
        String formattedTerm = term.replaceAll(" ", "%20");
        URL url = new URL("https://api.nal.usda.gov/fdc/v1/search?api_key=uM2ndaOlQYzFPO74HfjZzoppSsnVoLL03Lw7BoAA&generalSearchInput=" + formattedTerm);
        HttpURLConnection httpClient = (HttpURLConnection) url.openConnection();
        httpClient.setRequestMethod("GET");
        httpClient.setRequestProperty("Content-Type", "application/json");
        int responseCode = httpClient.getResponseCode();
        System.out.println("response code");
        System.out.println(responseCode);
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(httpClient.getInputStream()))) {

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(response.toString());
            JSONArray foods = (JSONArray)jsonObject.get("foods");
            JSONObject food = (JSONObject)foods.get(0);
            String ingredients = (String)food.get("ingredients");

            String[] ingredientsArr = ingredients.split(", ");
            String[] topIngredientsArr = Arrays.copyOfRange(ingredientsArr, 0, 5);
//            System.out.println("INGREDIENTSSS");
//            System.out.println(ingredients);
//            System.out.println(Arrays.toString(ingredientsArr));
//            System.out.println(Arrays.toString(topIngredientsArr));
            //print result
            System.out.println(response.toString());

        }
    }
}
