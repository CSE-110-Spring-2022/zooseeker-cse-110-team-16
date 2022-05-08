package com.example.cse110lab5;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

import java.util.ArrayList;
import java.util.List;

public class DumbRouteStrategy implements RouteStrategy {
    @Override
    public List<GraphPath<String, IdentifiedWeightedEdge>> makeRoute(Graph<String, IdentifiedWeightedEdge> edgeData, List<String> selectedExhibits) {
        return makeRouteRecursively(edgeData, selectedExhibits, "entrance_exit_gate");
    }

    private List<GraphPath<String, IdentifiedWeightedEdge>> makeRouteRecursively(Graph<String, IdentifiedWeightedEdge> edgeData, List<String> remainingExhibits, String current) {
        List<GraphPath<String, IdentifiedWeightedEdge>> output = new ArrayList<>();
        remainingExhibits.remove(current);

        String next = remainingExhibits.get(0);
        remainingExhibits.remove(0);

        GraphPath<String, IdentifiedWeightedEdge> pathToNext = DijkstraShortestPath.findPathBetween(edgeData, current, next);
        output.add(pathToNext);

        if (!remainingExhibits.isEmpty())
            output.addAll(makeRouteRecursively(edgeData, remainingExhibits, next));

        return output;
    }
}
