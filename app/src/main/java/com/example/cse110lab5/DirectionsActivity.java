package com.example.cse110lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.gson.Gson;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;

import java.util.List;
import java.util.Map;

public class DirectionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);

        //get route for plan
        Gson gson = new Gson();
        List<GraphPath<String, IdentifiedWeightedEdge>> path = gson.fromJson(getIntent().getStringExtra("JsonRoute"), List.class);

        ZooData zooData = new ZooData();
        Graph<String, IdentifiedWeightedEdge> g = zooData.getGraphDatabase();
        Map<String, ZooData.VertexInfo> vInfo = zooData.getVertexDatabase();
        Map<String, ZooData.EdgeInfo> eInfo = zooData.getEdgeDatabase();

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
        }

    }
}