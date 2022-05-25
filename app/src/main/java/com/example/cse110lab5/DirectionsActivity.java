package com.example.cse110lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DirectionsActivity extends AppCompatActivity {
    private int numNextClicks = 0;
    private List<GraphPath<String, IdentifiedWeightedEdge>> route = new ArrayList<>();
    private List<String> sortedVertexList;
    private final ZooData zooData = new ZooData();
    private Graph<String, IdentifiedWeightedEdge> edgeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);

        //get sortedVertexList from planActivity
        Gson gson = new Gson();
        this.sortedVertexList = gson.fromJson(getIntent().getStringExtra("sortedVertexList"), List.class);

        zooData.populateDatabase(this);
        edgeData = zooData.getGraphDatabase();

        // Create route from sortedVertexList
        for (int i = 0; i < sortedVertexList.size() - 1; i++) {
            route.add(DijkstraShortestPath.findPathBetween(edgeData, sortedVertexList.get(i), sortedVertexList.get(i + 1)));
        }

        //addition
    }


    public void onNextBtnClick(View view) {
        numNextClicks++;

        Map<String, ZooData.VertexInfo> vInfo = zooData.getVertexDatabase();
        Map<String, ZooData.EdgeInfo> eInfo = zooData.getEdgeDatabase();

        //populate direction text

        String directions = "";
        String title = "";
        int numPaths = route.size();
        int i = 0;
        if (numNextClicks <= numPaths) {
            GraphPath<String, IdentifiedWeightedEdge> path = this.route.get(numNextClicks - 1);
            String start = "";
            String end = "";
            //title = "The shortest path from " + vInfo.get(edgeData.getEdgeTarget(path.getEdgeList().get(i)).toString()).name + " to " + vInfo.get(edgeData.getEdgeTarget(path.getEdgeList().get(i+1)).toString()).name;
            for (IdentifiedWeightedEdge e : path.getEdgeList()) {
                if (i == 0) {
                    start = vInfo.get(edgeData.getEdgeSource(e).toString()).name;
                }
                directions +=
                        + (i + 1)
                        + " Walk " + edgeData.getEdgeWeight(e)
                        + " meters along " + eInfo.get(e.getId()).street
                        + " from " + vInfo.get(edgeData.getEdgeSource(e).toString()).name
                        + " to " + vInfo.get(edgeData.getEdgeTarget(e).toString()).name
                        + "\n\n";
                i++;
                end = vInfo.get(edgeData.getEdgeTarget(e)).name;
            }
            title = "The shortest path from " + start + " to " + end;

            TextView directionsTextView = (TextView) findViewById(R.id.directionsView);
            directionsTextView.setText(title + "\n\n" + directions);
        }

        else {
            TextView directionsTextView = (TextView) findViewById(R.id.directionsView);
            directionsTextView.setText("At final exhibit!");
        }
    }
}