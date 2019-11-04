package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class JsonUtils {

    //Make logs by using TAG
    private static final String TAG = JsonUtils.class.getSimpleName();

    // String Keys for manipulating on JSON object
    private final static String KEY_NAME = "name";
    private final static String KEY_MAIN_NAME = "mainName";
    private final static String KEY_ALSO_KNOWN_AS = "alsoKnownAs";
    private final static String KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
    private final static String KEY_DESCRIPTION = "description";
    private final static String KEY_IMAGE_URL = "image";
    private final static String KEY_INGREDIENTS = "ingredients";


    public static Sandwich parseSandwichJson(String json) {

        try {
            //Create a JSONObject from the JSON String
            JSONObject sandwichObject = new JSONObject(json);

            //Extract a JSONObject with the key called "name"
            JSONObject name = sandwichObject.getJSONObject(KEY_NAME);

            //Extract a JSONObject with the key called "mainName"
            String mainName = name.getString(KEY_MAIN_NAME);

            //Extract the JSONArray with the key called "alsoKnownAs"
            JSONArray alsoKnownAsArray = name.getJSONArray(KEY_ALSO_KNOWN_AS);
            List<String> alsoKnownAs = new ArrayList<>();
            if (alsoKnownAsArray.length() != 0) {
                for (int i = 0; i < alsoKnownAsArray.length(); i++) {
                    alsoKnownAs.add(i, alsoKnownAsArray.getString(i));

                }
            } else {
                alsoKnownAs = null;
            }

            //Extract a JSONObject with the key called "PlaceOfOrigin"
            String placeOfOrigin = sandwichObject.getString(KEY_PLACE_OF_ORIGIN);

            //Extract a JSONObject with the key called "Description"
            String description = sandwichObject.getString(KEY_DESCRIPTION);

            //Image
            String imageUrl = sandwichObject.getString(KEY_IMAGE_URL);

            //Extract the JSONArray with the key called "Ingredients"
            JSONArray ingredientsArray = sandwichObject.getJSONArray(KEY_INGREDIENTS);
            List<String> ingredients = new ArrayList<>();
            if (ingredientsArray.length() != 0) {
                for (int i = 0; i < ingredientsArray.length(); i++) {
                    ingredients.add(i, ingredientsArray.getString(i));
                }
            } else {
                ingredients = null;
            }
            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, imageUrl, ingredients);

        } catch (JSONException e) {
        Log.e(TAG, "Unexpected JSON exception",e);
        //Do something to recover... or terminate the app
        }
        return null;
    }
}

