package com.example.cse110lab5;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

import java.util.ArrayList;
import java.util.List;

public class NearestNeighborRouteStrategy implements RouteStrategy {
    @Override
    public List<String> makeRoute(Graph<String, IdentifiedWeightedEdge> edgeData, List<String> selectedExhibits) {
        return makeRouteRecursively(edgeData, selectedExhibits, "entrance_exit_gate");
    }

    private List<String> makeRouteRecursively(Graph<String, IdentifiedWeightedEdge> edgeData, List<String> remainingExhibits, String current) {
        List<String> output = new ArrayList<>();
        output.add(current);

        remainingExhibits.remove(current);

        if (!remainingExhibits.isEmpty()) {
            String closest = getClosest(edgeData, remainingExhibits, current);
            output.addAll(makeRouteRecursively(edgeData, remainingExhibits, closest));
        }

        return output;
    }

    private String getClosest(Graph<String, IdentifiedWeightedEdge> edgeData, List<String> remainingExhibits, String current) {
        double minDistance = Double.MAX_VALUE;
        String closest = "";

        for (String exhibit : remainingExhibits) {
            GraphPath<String, IdentifiedWeightedEdge> path = DijkstraShortestPath.findPathBetween(edgeData, current, exhibit);
            double pathLength = path.getWeight();

            if (pathLength < minDistance) {
                minDistance = pathLength;
                closest = exhibit;
            }
        }

        return closest;
    }
}
