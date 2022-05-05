package com.example.cse110lab5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class PlanActivity extends AppCompatActivity {
    // TODO: How do we get stuff from the database?

    // TODO: Add/subtract the selected exhibit names
    private final List<String> selectedExhibitNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        // =============== Search Box ===============
        AutoCompleteTextView textView = findViewById(R.id.search_textview);
        String[] autocomplete = {}; // TODO: Populate this with the names of all exhibits
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, autocomplete);
        textView.setAdapter(adapter);
    }

    // ==================== Basic Bottom Navigation UI ====================
    public void onRouteBtnClicked(View view) {
        Intent intent = new Intent(this, RouteActivity.class);
        // Pass the array of selected exhibit names to the route activity
        intent.putExtra("selectedExhibitNames", selectedExhibitNames.toArray());
        startActivity(intent);
    }

    // TODO: Display number of items in selectedExhibitNames
    // TODO: Get code from lab5 for adding items to an expanding view on screen
}