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
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DirectionsActivity extends AppCompatActivity {
    private int numNextClicks = 0;
    private int numBackClicks = 0;
    private List<GraphPath<String, IdentifiedWeightedEdge>> route = new ArrayList<>();
    private List<String> sortedVertexList;
    private final ZooData zooData = new ZooData();
    private Graph<String, IdentifiedWeightedEdge> edgeData;
    boolean directionType = false;
    String current_detailed_directions = "";
    String current_brief_directions = "";
    String title = "";
    String start = "";
    String end = "";
    String current = "";

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
    }


    public void onNextBtnClick(View view) {
        numNextClicks++;

        Map<String, ZooData.VertexInfo> vInfo = zooData.getVertexDatabase();
        Map<String, ZooData.EdgeInfo> eInfo = zooData.getEdgeDatabase();

        //populate direction text
        String title = "";
        int numPaths = route.size();
        int i = 0;
        int j = 0;

        current_detailed_directions = "";
        current_brief_directions = "";
        if (numNextClicks <= numPaths) {
            GraphPath<String, IdentifiedWeightedEdge> path = this.route.get(numNextClicks - 1);

            current_brief_directions +=
                    (j + 1)
                    + " Walk " + path.getWeight()
                    + " meters from " + path.getStartVertex()
                    + " to " + path.getEndVertex()
                    +  "\n\n";
            j++;

            for (IdentifiedWeightedEdge e : path.getEdgeList()) {
                if (i == 0) {
                    start = path.getStartVertex();
                    end = path.getEndVertex();
                }
                IdentifiedWeightedEdge previousEdge;
                String source = vInfo.get(edgeData.getEdgeSource(e).toString()).name;
                String target = vInfo.get(edgeData.getEdgeTarget(e).toString()).name;
                if (numNextClicks == 1) {
                    current = "entrance_exit_gate";
                }

                //need to reverse edge
                boolean switchEdge = false;
                if (target == current) {
                    switchEdge = true;
                    source = vInfo.get(edgeData.getEdgeTarget(e).toString()).name;
                    target = vInfo.get(edgeData.getEdgeSource(e).toString()).name;
                }

                current_detailed_directions +=
                        (i + 1)
                        + " Walk " + edgeData.getEdgeWeight(e)
                        + " meters along " + eInfo.get(e.getId()).street
                        + " from " + source
                        + " to " + target
                        + "\n\n";
                i++;
//                end = vInfo.get(edgeData.getEdgeTarget(e)).name;
                if (!switchEdge) {
                    current = vInfo.get(edgeData.getEdgeTarget(e).toString()).name;
                }
                else {
                    current = vInfo.get(edgeData.getEdgeSource(e).toString()).name;
                }

            }
            title = "The shortest path from " + start + " to " + end;

            TextView directionsTextView = (TextView) findViewById(R.id.directionsView);
            directionsTextView.setText(title + "\n\n" + current_detailed_directions);

        }

        else {
            TextView directionsTextView = (TextView) findViewById(R.id.directionsView);
            directionsTextView.setText("At final exhibit!");
        }
    }

    public void onToggleBtnClick(View view) {
        title = "The shortest path from " + start + " to " + end;
        directionType = !directionType;
        //detailed directions
        if (directionType == false) {
            TextView directionsTextView = (TextView) findViewById(R.id.directionsView);
            directionsTextView.setText(title + "\n\n" + current_detailed_directions);
        }

        //brief directions
        else {
            TextView directionsTextView = (TextView) findViewById(R.id.directionsView);
            directionsTextView.setText(title + "\n\n" + current_brief_directions);
        }

    }

    public void onBackBtnClick(View view) {
        if (numNextClicks > 1) {
            numNextClicks--;
            numBackClicks++;

            GraphPath<String, IdentifiedWeightedEdge> path = this.route.get(numNextClicks - 1);


            Map<String, ZooData.VertexInfo> vInfo = zooData.getVertexDatabase();
            Map<String, ZooData.EdgeInfo> eInfo = zooData.getEdgeDatabase();

            int numPaths = route.size();
            int i = 0;
            int j = 0;
            if (numNextClicks <= numPaths) {

                current_brief_directions +=
                        (j + 1)
                        + " Walk " + path.getWeight()
                        + " meters from " + path.getEndVertex()
                        + " to " + path.getStartVertex()
                        + "\n\n";
                j++;

                current_detailed_directions = "";
                Collections.reverse(path.getEdgeList());
                for (IdentifiedWeightedEdge e : path.getEdgeList()) {
                    if (i == 0) {
                        start = path.getStartVertex();
                        end = path.getEndVertex();
                    }
                    IdentifiedWeightedEdge previousEdge;
                    String source = vInfo.get(edgeData.getEdgeSource(e).toString()).name;
                    String target = vInfo.get(edgeData.getEdgeTarget(e).toString()).name;
                    if (numNextClicks == 1) {
                        current = "entrance_exit_gate";
                    }

                    //need to reverse edge
                    boolean switchEdge = false;
                    if (target == current) {
                        switchEdge = true;
                        source = vInfo.get(edgeData.getEdgeTarget(e).toString()).name;
                        target = vInfo.get(edgeData.getEdgeSource(e).toString()).name;
                    }

                    current_detailed_directions +=
                            (i + 1)
                            + " Walk " + edgeData.getEdgeWeight(e)
                            + " meters along " + eInfo.get(e.getId()).street
                            + " from " + target
                            + " to " + source
                            + "\n\n";
                    i++;
//                end = vInfo.get(edgeData.getEdgeTarget(e)).name;
                    if (!switchEdge) {
                        current = vInfo.get(edgeData.getEdgeTarget(e).toString()).name;
                    }
                    else {
                        current = vInfo.get(edgeData.getEdgeSource(e).toString()).name;
                    }

                }
                title = "The shortest path from " + start + " to " + end;

                TextView directionsTextView = (TextView) findViewById(R.id.directionsView);
                directionsTextView.setText(title + "\n\n" + current_detailed_directions);

            }


            title = "The shortest path from " + path.getEndVertex() + " to " + path.getStartVertex();

            TextView directionsTextView = (TextView) findViewById(R.id.directionsView);
            directionsTextView.setText(title + "\n\n" + current_detailed_directions);
        }

    }
}