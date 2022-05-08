package com.example.cse110lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.gson.Gson;

import org.jgrapht.GraphPath;

import java.util.List;

public class DirectionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);

        //get route for plan
        Gson gson = new Gson();
        List<GraphPath<String, IdentifiedWeightedEdge>> path = gson.fromJson(getIntent().getStringExtra("JsonRoute"), List.class);


    }
}