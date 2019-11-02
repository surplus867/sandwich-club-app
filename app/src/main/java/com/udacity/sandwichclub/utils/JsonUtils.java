package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private final static String KEY_NAME = "name";
    private final static String KEY_MAIN_NAME = "mainName";
    private final static String KEY_ALSO_KNOWN_AS = "alsoKnownAs";
    private final static String KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
    private final static String KEY_DESCRIPTION = "description";
    private final static String KEY_IMAGE_URL = "image";
    private final static String KEY_INGREDIENTS = "ingredients";


    public static Sandwich parseSandwichJson(String json) throws JSONException {

        JSONObject sandwichObject = new JSONObject(json);

        JSONObject name = sandwichObject.getJSONObject(KEY_NAME);

        String mainName = name.getString(KEY_MAIN_NAME);

        JSONArray alsoKnownAsArray = name.getJSONArray(KEY_ALSO_KNOWN_AS);
        List<String> alsoKnownAs = new ArrayList<>();
        if (alsoKnownAsArray.length() != 0) {
            for (int i = 0; i < alsoKnownAsArray.length(); i++) {
                alsoKnownAs.add(i, alsoKnownAsArray.getString(i));

            }
        } else {
            alsoKnownAs = null;
        }

        String placeOfOrigin = name.getString(KEY_PLACE_OF_ORIGIN);

        String description = name.getString(KEY_DESCRIPTION);

        String imageUrl = name.getString(KEY_IMAGE_URL);

        JSONArray ingredientsArray = name.getJSONArray(KEY_INGREDIENTS);
        List<String> ingredients = new ArrayList<>();
        if (ingredientsArray.length() != 0) {
            for (int i = 0; i < ingredientsArray.length(); i++) {
                ingredients.add(i, ingredientsArray.getString(i));
            }
        } else {
            ingredients = null;

        }
        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, imageUrl, ingredients);

    }
}