package com.example.cse110lab5;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

import java.util.ArrayList;
import java.util.List;

public class NearestNeighborRouteStrategy implements RouteStrategy {
    @Override
    public List<String> makeRoute(Graph<String, IdentifiedWeightedEdge> edgeData, List<String> selectedExhibits) {
        return null;
    }

    public List<String> makeRouteRecursively(Graph<String, IdentifiedWeightedEdge> edgeData, List<String> remainingExhibits, String current) {
        List<String> output = new ArrayList<>();
        output.add(current);

        String closest = getClosest(edgeData,remainingExhibits,current);

        remainingExhibits.remove(closest);

        output.addAll(makeRouteRecursively(edgeData, remainingExhibits, closest));

        remainingExhibits.remove(current);

        String next = remainingExhibits.get(0);
        remainingExhibits.remove(0);

        GraphPath<String, IdentifiedWeightedEdge> pathToNext = DijkstraShortestPath.findPathBetween(edgeData, current, next);
//        output.add(pathToNext);

        if (!remainingExhibits.isEmpty())
            output.addAll(makeRouteRecursively(edgeData, remainingExhibits, next));

        return output;
    }

    private String getClosest(Graph<String, IdentifiedWeightedEdge> edgeData, List<String> remainingExhibits, String current) {
        int minDistance = Integer.MAX_VALUE;
        String closest = "";

        for (String exhibit : remainingExhibits) {
            GraphPath<String, IdentifiedWeightedEdge> path = DijkstraShortestPath.findPathBetween(edgeData, current, exhibit);
            int pathLength =  path.getLength();

            if (pathLength < minDistance) {
                minDistance = pathLength;
                closest = exhibit;
            }
        }

        return closest;
    }
}
