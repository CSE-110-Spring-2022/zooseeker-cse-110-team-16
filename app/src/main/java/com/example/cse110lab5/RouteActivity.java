package com.example.cse110lab5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RouteActivity extends AppCompatActivity {
    private static final RouteStrategy STRATEGY = new DumbRouteStrategy(); // TODO: Replace this with a good strategy
    private List<String> selectedExhibitNames;
    private String currentExhibit;
    private String nextExhibit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        // Receive the selected exhibit names from PlanActivity
        Bundle extras = getIntent().getExtras();
        this.selectedExhibitNames = new ArrayList<String>(Arrays.asList(extras.getStringArray("selectedExhibitNames")));

        // TODO: Convert names to list of ZooData, pass that list to STRATEGY to get the smartly-ordered list of exhibits
    }

    // ======================== Basic Bottom Navigation UI ========================
    public void onPlanBtnClicked(View view) {
        Intent intent = new Intent(this, PlanActivity.class);
        startActivity(intent);
    }

    // TODO: Display directions from current exhibit to next exhibit
    // TODO: Next button, i.e reached next exhibit
}