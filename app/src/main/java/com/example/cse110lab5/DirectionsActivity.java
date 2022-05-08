package com.example.cse110lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

import java.util.List;
import java.util.Map;

public class DirectionsActivity extends AppCompatActivity {
    private int numNextClicks = 0;
    private List<GraphPath<String, IdentifiedWeightedEdge>> route;
    private List<String> sortedVertexList;
    private final ZooData zooData = new ZooData();
    private Graph<String, IdentifiedWeightedEdge> edgeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);

        //get sortedVertexList from planActivity
        Gson gson = new Gson();
        this.sortedVertexList = gson.fromJson(getIntent().getStringExtra("JsonRoute"), List.class);

        zooData.populateDatabase(this);
        edgeData = zooData.getGraphDatabase();

        //@Ilan, you can make your final route here and set this.route to be equal to what you get

    }

    public void onNextBtnClick(View view) {
        numNextClicks++;

        Map<String, ZooData.VertexInfo> vInfo = zooData.getVertexDatabase();
        Map<String, ZooData.EdgeInfo> eInfo = zooData.getEdgeDatabase();

        /*
        System.out.printf("The shortest path from '%s' to '%s' is:\n", "entrance_exit_gate", "lions");

        int i = 1;
        for (IdentifiedWeightedEdge e : path.get(i).getEdgeList()) {
            System.out.printf("  %d. Walk %.0f meters along %s from '%s' to '%s'.\n",
                    i,
                    g.getEdgeWeight(e),
                    eInfo.get(e.getId()).street,
                    vInfo.get(g.getEdgeSource(e).toString()).name,
                    vInfo.get(g.getEdgeTarget(e).toString()).name);
            i++;
        } */

        //populate direction text
        String title = "The shortest path from entrance_exit_gate to lions is: \n";
        String directions = "";


        GraphPath<String, IdentifiedWeightedEdge> path = this.route.get(numNextClicks);
        int i = 0;
        for (IdentifiedWeightedEdge e : path.getEdgeList()) {
            directions += numNextClicks
                    + i
                    + " Walk " + edgeData.getEdgeWeight(e)
                    + " meters along " + eInfo.get(e.getId()).street
                    + " from " + vInfo.get(edgeData.getEdgeSource(e).toString()).name
                    + " to " + vInfo.get(edgeData.getEdgeTarget(e).toString()).name
                    + "\n";
            i++;
        }

        TextView directionsTextView = (TextView) findViewById(R.id.directionsView);
        directionsTextView.setText(title + directions);
    }
}