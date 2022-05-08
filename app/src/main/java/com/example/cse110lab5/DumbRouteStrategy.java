package com.example.cse110lab5;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

import java.util.ArrayList;
import java.util.List;

public class DumbRouteStrategy implements RouteStrategy {
    @Override
    public List<GraphPath<String, IdentifiedWeightedEdge>> makeRoute(Graph<String, IdentifiedWeightedEdge> edgeData, List<String> selectedExhibits) {
        List<GraphPath<String, IdentifiedWeightedEdge>> output = new ArrayList<>();
        String start = "entrance_exit_gate";
        selectedExhibits.remove(start);

        String next = selectedExhibits.get(0);
        selectedExhibits.remove(0);
        output.addAll(makeRouteRecursively(edgeData, selectedExhibits, next));

        return output;
    }

    public List<GraphPath<String, IdentifiedWeightedEdge>> makeRouteRecursively(Graph<String, IdentifiedWeightedEdge> edgeData, List<String> remainingExhibits, String current) {
        List<GraphPath<String, IdentifiedWeightedEdge>> output = new ArrayList<>();
        if (remainingExhibits.isEmpty()) return output;

        String next = remainingExhibits.get(0);
        remainingExhibits.remove(0);

        GraphPath<String, IdentifiedWeightedEdge> path = DijkstraShortestPath.findPathBetween(edgeData, current, next);

        output.addAll(makeRouteRecursively(edgeData, remainingExhibits, next));

        return output;
    }
}
