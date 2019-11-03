package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private Sandwich sandwich;

    @BindView(R.id.origin_tv)
    TextView placeOfOriginTextView;
    @BindView(R.id.also_known_tv)
    TextView alsoKnownAsTextView;
    @BindView(R.id.description_tv)
    TextView descriptionTextView;
    @BindView(R.id.ingredients_tv)
    TextView ingredientsTextView;

    @BindView(R.id.image_iv)
    ImageView sandwichImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

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
                .into(sandwichImageView);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        String originString = sandwich.getPlaceOfOrigin();
        if (originString != null) {
            placeOfOriginTextView.setText(originString);
        } else {
            placeOfOriginTextView.setText(getString(R.string.missing_information));
        }
        descriptionTextView.setText(getString(R.string.missing_information));

        List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();
        if(alsoKnownAsList !=null){
        alsoKnownAsTextView.setText(TextUtils.join(",", alsoKnownAsList));
        }else{
            alsoKnownAsTextView.setText(R.string.missing_information);
        }

        List<String> ingredientsList = sandwich.getIngredients();
        if(ingredientsList !=null){
            ingredientsTextView.setText(TextUtils.join(",",ingredientsList));
        }else{
            ingredientsTextView.setText(R.string.missing_information);
        }
    }
}
