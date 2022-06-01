package com.example.cse110lab5;

import android.util.Log;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NearestNeighborRouteStrategy implements RouteStrategy {
    @Override
    public List<String> makeRoute(Graph<String, IdentifiedWeightedEdge> edgeData, Map<String, ZooData.VertexInfo> vertexData, List<String> selectedExhibits) {
        return makeRouteRecursively(edgeData, vertexData, selectedExhibits, "entrance_exit_gate");
    }

    private List<String> makeRouteRecursively(Graph<String, IdentifiedWeightedEdge> edgeData, Map<String, ZooData.VertexInfo> vertexData, List<String> remainingExhibits, String current) {
        List<String> output = new ArrayList<>();
        output.add(current);

        remainingExhibits.remove(current);

        if (!remainingExhibits.isEmpty()) {
            String closest = getClosest(edgeData, vertexData, remainingExhibits, current);
            output.addAll(makeRouteRecursively(edgeData, vertexData, remainingExhibits, closest));
        }

        return output;
    }

    private String getClosest(Graph<String, IdentifiedWeightedEdge> edgeData, Map<String, ZooData.VertexInfo> vertexData, List<String> remainingExhibits, String current) {
        current = getGroupIdIfNecessary(vertexData, current);

        Log.e("getClosest", String.format("%s", current));

        double minDistance = Double.MAX_VALUE;
        String closest = "";

        for (String exhibit : remainingExhibits) {
            String groupIdedExhibitIfNecessary = getGroupIdIfNecessary(vertexData, exhibit);

            GraphPath<String, IdentifiedWeightedEdge> path = DijkstraShortestPath.findPathBetween(edgeData, current, groupIdedExhibitIfNecessary);
            double pathLength = path.getWeight();

            if (pathLength < minDistance) {
                minDistance = pathLength;
                closest = exhibit;
            }
        }

        return closest;
    }

    private String getGroupIdIfNecessary(Map<String, ZooData.VertexInfo> vertexData, String vertex) {
        ZooData.VertexInfo currentVertex = vertexData.get(vertex);
        if (currentVertex.getInGroup()) {
            return currentVertex.getGroupId();
        }
        return vertex;
    }
}
