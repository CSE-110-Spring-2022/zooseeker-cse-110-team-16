package com.example.cse110lab5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PlanActivity extends AppCompatActivity {
    private static final RouteStrategy STRATEGY = new DumbRouteStrategy(); // TODO: Replace this with a good strategy
    private List<String> selectedExhibitNames;
    private String currentExhibit;
    private String nextExhibit;
    private Map<String, ZooData.VertexInfo> nodeData = new ZooData().getVertexDatabase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        // Receive the selected exhibit names from ListActivity
        Bundle extras = getIntent().getExtras();
        this.selectedExhibitNames = new ArrayList<>(Arrays.asList(extras.getStringArray("addedAnimals")));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, selectedExhibitNames);
        ListView exhibitList = this.findViewById(R.id.selected_exhibits);
        exhibitList.setAdapter(adapter);
    }

    // ======================== Basic Bottom Navigation UI ========================
    public void onListBtnClicked(View view) {
        Intent intent = new Intent(this, TodoListActivity.class);
        startActivity(intent);
    }

    // TODO: Display directions from current exhibit to next exhibit
    // TODO: Next button, i.e reached next exhibit
}