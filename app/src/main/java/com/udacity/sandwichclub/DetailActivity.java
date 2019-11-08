package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    //Initialize a sandwich object
    private Sandwich sandwich;

    //TextView "origin of the Sandwich"
    @BindView(R.id.origin_tv)
    //TextView "place of origin of the Sandwich"
    TextView placeOfOriginTextView;
    //TextView "also known as"
    @BindView(R.id.also_known_tv)
    TextView alsoKnownAsTextView;
    //TextView "description of the Sandwich"
    @BindView(R.id.description_tv)
    TextView descriptionTextView;
    //TextView "ingredients of the Sandwich"
    @BindView(R.id.ingredients_tv)
    TextView ingredientsTextView;
    //ImageView of the Sandwich
    @BindView(R.id.image_iv)
    ImageView sandwichImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Bind views with Butter Knife
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];

        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.mipmap.ic_launcher)//can also be a drawable
                .error(R.mipmap.ic_launcher)
                .into(sandwichImageView);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        //Get list of strings from "alsoKnownAs"
        List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();
        if(alsoKnownAsList !=null){
        //If the alsoKnownAsList is not null, set TextView and use TextUtils.join method
            alsoKnownAsTextView.setText(TextUtils.join(",", alsoKnownAsList));
        }else{
        //If the alsoKnownAsList is null, show the user the information availability
            alsoKnownAsTextView.setText(R.string.missing_information);
        }

        //Get place of the origin string
        String originString = sandwich.getPlaceOfOrigin();
        //Check if the origin String is not null, set the text to origin textView, otherwise hide the TextView
        if (originString != null) {
            placeOfOriginTextView.setText(originString);
        } else {
        //If the origin String is null, show the user the information availability
            placeOfOriginTextView.setText(getString(R.string.missing_information));
        }
        //Set the description String to the description TextView
        descriptionTextView.setText(sandwich.getDescription());

        //Get List of Strings from "Ingredients"
        List<String> ingredientsList = sandwich.getIngredients();
        //Check if the ingredientsList is not null, set TextView and use TextUtils.join method
        if(ingredientsList !=null){
            ingredientsTextView.setText(TextUtils.join(",",ingredientsList));
        }else{
        //If the ingredientsList is null, show the user the information availability
            ingredientsTextView.setText(R.string.missing_information);
        }
    }
}
