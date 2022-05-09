package com.example.cse110lab5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class PlanActivity extends AppCompatActivity {
    private static final RouteStrategy STRATEGY = new DumbRouteStrategy();
    private final ZooData zooData = new ZooData();
    private Graph<String, IdentifiedWeightedEdge> edgeData;
    private List<String> sortedVertexList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        zooData.populateDatabase(this);
        edgeData = zooData.getGraphDatabase();

        // Receive the selected exhibit names from ListActivity
        Bundle extras = getIntent().getExtras();
        List<String> selectedExhibitNames = new ArrayList<>(Arrays.asList(extras.getStringArray("addedAnimals")));

        this.sortedVertexList = STRATEGY.makeRoute(edgeData, selectedExhibitNames);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, sortedVertexList);
        ListView exhibitList = this.findViewById(R.id.selected_exhibits);
        exhibitList.setAdapter(adapter);
    }

    // ======================== Basic Bottom Navigation UI ========================
    public void onListBtnClicked(View view) {
        Intent intent = new Intent(this, TodoListActivity.class);
        startActivity(intent);
    }

    public void onDirectionsBtnClick(View view) {
        //pass in sortedVertexList to directionsActivity
        Intent intent = new Intent(this, DirectionsActivity.class);
        Gson gson = new Gson();
        String JsonRoute = gson.toJson(sortedVertexList);
        intent.putExtra("sortedVertexList", JsonRoute);

        startActivity(intent);
    }
}