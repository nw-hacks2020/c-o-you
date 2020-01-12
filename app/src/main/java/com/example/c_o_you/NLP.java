package com.example.c_o_you;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class NLP {
    public static double getIngredients(String term) throws Exception {
        Map<String, Double> map = new HashMap<String, Double>();
        map.put("PORK", 4.621484423);
        map.put("CHICKEN", 3.262298031);
        map.put("EGGS", 2.526441574);
        map.put("BEEF", 23.80216746);
        map.put("MILK", 1.36935455);
        map.put("BUTTER", 12.66705193);
        map.put("YOGURT", 1.538219779);
        map.put("OATS",	1.184860243);
        map.put("RICE", 3.579564355);
        map.put("RAPESEED_OIL", 2.373003323);
        map.put("OLIVE_OIL", 4.044846694);
        map.put("SUGAR", 1.523165461);
        map.put("TOMATO", 1.493989349);
        map.put("CUCUMBER", 0.734100042);
        map.put("ICEBERG_LETTUCE", 0.322647658);
        map.put("POTATO", 0.375638513);
        map.put("BEANS", 1.159890391);
        map.put("APPLE", 0.36967267);
        map.put("BANANA", 0.67261454);
        map.put("STRAWBERRY", 0.73858763);
        map.put("RASPBERRY", 0.759482);
        map.put("COFFEE", 6.406229764);
        map.put("PASTA", 1.76571932);
        map.put("BUNS", 1.447764517);
        map.put("BISCUITS", 1.104275024);
        map.put("PASTRY", 1.409857661);
        map.put("COFFEE", 6.406229764);
        map.put("BREAD", 1.02194756);
        map.put("ORANGE_JUICE", 1.687789075);
        map.put("APPLE_JUICE", 0.791822691);
        map.put("CHOCOLATE", 6.856879104);
        map.put("CHIPS", 2.89291368);
        map.put("ICE_CREAM", 4.281456556);
        map.put("MINERAL WATER", 0.277240578);
        map.put("SODA", 0.40582548);
        map.put("ONION", 0.381612481);
        map.put("SALMON", 6.065231518);

        String TERM = term.toUpperCase();

        if (TERM == "FRUIT SALAD") {
            double fruitSaladTotal = map.get("APPLE") + map.get("BANANA") + map.get("STRAWBERRY") + map.get("YOGURT");
            return fruitSaladTotal;
        } else if (TERM == "HASH BROWNS") {
            double hashTotal = map.get("POTATO") + map.get("OLIVE_OIL") + map.get("ONION");
            return hashTotal;
        }
        String _TERM = TERM.replaceAll(" ", "_");

        if (map.get(_TERM) != null) {
//            return map.get(_TERM);
        }
        String[] TERMArr = TERM.split(" ", 0);
        double termSum = 0;
        for (int i = 0; i < TERMArr.length; i++){
            if (map.get(TERMArr[i]) != null){
                termSum += map.get(TERMArr[i]);
                return termSum;
//                return map.get(TERMArr[i]);
            }
        }

        // use google nlp api to get the phrase(s) to search
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("encodingType", "UTF-8");
        Map<String,Object> document = new LinkedHashMap<>();
        document.put("type", "PLAIN_TEXT");
        document.put("content", term);
        params.put("document", document);
//        System.out.println(params);
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }

        URL url = new URL("https://language.googleapis.com/v1/documents:analyzeEntities?key=AIzaSyBeqztPTwLLGbKpCzk_Clab_kMleOIpcBo");

        final String POST_PARAMS = "{\r\n\t\"encodingType\": \"UTF8\",\r\n\t\"document\": {\r\n    \"type\": \"PLAIN_TEXT\",\r\n    \"content\": \"granola and yogurt\"\r\n  }\r\n}";
//        System.out.println(POST_PARAMS);
        HttpURLConnection postConnection = (HttpURLConnection) url.openConnection();
        postConnection.setRequestMethod("POST");
        postConnection.setRequestProperty("Content-Type", "application/json");
        postConnection.setDoOutput(true);
        OutputStream os = postConnection.getOutputStream();
        os.write(POST_PARAMS.getBytes());
        os.flush();
        os.close();
        int responseCode = postConnection.getResponseCode();
//        System.out.println("POST Response Code :  " + responseCode);
//        System.out.println("POST Response Message : " + postConnection.getResponseMessage());
        if (responseCode == 200) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    postConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in .readLine()) != null) {
                response.append(inputLine);
            } in .close();
            // print result
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(response.toString());
            JSONArray entities = (JSONArray)jsonObject.get("entities");
            String[] NLPTermArr = new String[entities.size()];

            if(entities!=null && entities.size()>0){
                for (int i = 0; i < entities.size(); i++) {
                    JSONObject entity = (JSONObject)entities.get(i);
                    String NLPTerm = (String)entity.get("name");
                    String NLPTERM = NLPTerm.toUpperCase();
                    if (NLPTERM == "GRANOLA") {
                        NLPTermArr[i] = "OATS";
                    } else if (NLPTerm == "CROISSANTS") {
                        NLPTermArr[i] = "PASTRY";
                    } else {
                        NLPTermArr[i] = NLPTERM;
                    }
                }
            }

            // try looking it up first
            double NLPTermSum = 0;
            for (int i= 0; i < NLPTermArr.length; i++){
                String NLPTermToMatch = NLPTermArr[i];
                if (map.get(NLPTermToMatch) != null) {
                    NLPTermSum += map.get(NLPTermToMatch);
                }
            }
            if (NLPTermSum > 0) {
                return NLPTermSum;
            }


            // else try matching it to our food data stuff
            double FDCTermSum = 0;
            String[] ingredients = FDC.getIngredients(TERM);
            for (int i = 0; i < ingredients.length; i++) {
                if (map.get(ingredients[i]) != null) {
                    FDCTermSum += map.get(map.get(ingredients[i]));
                }
            }
            if (FDCTermSum > 0) {
                return FDCTermSum;
            }
//            System.out.println(response.toString());
        } else {
//            System.out.println("POST NOT WORKED");
            return 0;
        }

//        byte[] out = "{\\\"encodingType\\\": \\\"UTF8\\\",\\\"document\\\": {\\\"type\\\": \\\"PLAIN_TEXT\\\",\\\"content\\\": \\\"granola and yogurt\\\"}}" .getBytes(StandardCharsets.UTF_8);
//        int length = out.length;
//        URLConnection con = url.openConnection();
//        HttpURLConnection http = (HttpURLConnection)con;
//        http.setRequestMethod("POST"); // PUT is another valid option
//        http.setDoOutput(true);
//
//        http.setFixedLengthStreamingMode(length);
//        http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
//        http.connect();
//
//
//        try(OutputStream os = http.getOutputStream()) {
//            os.write(out);
//            System.out.println("OUT");
//            System.out.println(os);
//
//        }
//        try (BufferedReader in = new BufferedReader(
//                new InputStreamReader(http.getInputStream()))) {
//
//            StringBuilder response = new StringBuilder();
//            String line;
//
//            while ((line = in.readLine()) != null) {
//                response.append(line);
//            }
//            System.out.println("DONEEEEE!");
//            System.out.println(response);
//        }

        // get ingredients

        // SUM ingredients










//        String csvFile = "c_o_you";
//        BufferedReader br = null;
//        String line = "";
//        String cvsSplitBy = ",";
//        try {
//
//            br = new BufferedReader(new FileReader(csvFile));
//            while ((line = br.readLine()) != null) {
//
//                // use comma as separator
//                String[] country = line.split(cvsSplitBy);
//                System.out.println("HIIII");
//                System.out.println("Country [code= " + country[4] + " , name=" + country[5] + "]");
//
//            }
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        return 0;
    }
}
