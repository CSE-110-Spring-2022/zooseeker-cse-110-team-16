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
    private List<String> selectedExhibitNames;
    private String currentExhibit;
    private String nextExhibit;
    private final ZooData zooData = new ZooData();
    private Graph<String, IdentifiedWeightedEdge> edgeData;
    private List<GraphPath<String, IdentifiedWeightedEdge>> route;

    // TODO: selectedExhibitNames cannot be empty yet, remember to select some animals before entering this activity!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        zooData.populateDatabase(this);
        edgeData = zooData.getGraphDatabase();

        // Receive the selected exhibit names from ListActivity
        Bundle extras = getIntent().getExtras();
        this.selectedExhibitNames = new ArrayList<>(Arrays.asList(extras.getStringArray("addedAnimals")));

        GraphPath<String, IdentifiedWeightedEdge> path = DijkstraShortestPath.findPathBetween(edgeData, "entrance_exit_gate", "gorillas");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, path.getVertexList());
        ListView exhibitList = this.findViewById(R.id.selected_exhibits);
        exhibitList.setAdapter(adapter);

//        route = STRATEGY.makeRoute(edgeData, selectedExhibitNames);
    }

    // ======================== Basic Bottom Navigation UI ========================
    public void onListBtnClicked(View view) {
        Intent intent = new Intent(this, TodoListActivity.class);
        startActivity(intent);
    }

    public void onDirectionsBtnClick(View view) {
        //pass path information to directions activity

        Intent intent = new Intent(this, DirectionsActivity.class);
        //route = STRATEGY.makeRoute(edgeData, selectedExhibitNames);

        //create mock route
        GraphPath<String, IdentifiedWeightedEdge> path = DijkstraShortestPath.findPathBetween(edgeData, "entrance_exit_gate", "gorillas");
        GraphPath<String, IdentifiedWeightedEdge> path1 = DijkstraShortestPath.findPathBetween(edgeData, "gorillas", "lions");

        List<GraphPath<String, IdentifiedWeightedEdge>> mockRoute = new ArrayList<>();
        mockRoute.add(path);
        mockRoute.add(path1);

        Gson gson = new Gson();
        String JsonRoute = gson.toJson(mockRoute);
        intent.putExtra("JsonRoute", JsonRoute);

        startActivity(intent);

    }

    // TODO: Display directions from current exhibit to next exhibit
    // TODO: Next button, i.e reached next exhibit
}