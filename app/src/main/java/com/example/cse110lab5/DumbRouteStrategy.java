package com.example.cse110lab5;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DumbRouteStrategy implements RouteStrategy {
    @Override
    public List<String> makeRoute(Graph<String, IdentifiedWeightedEdge> edgeData, Map<String, ZooData.VertexInfo> vertexData, List<String> selectedExhibits) {
        List<String> output = new ArrayList<>();

        String start = "entrance_exit_gate";
        selectedExhibits.remove(start);

        output.add(start);
        output.addAll(selectedExhibits);

        return output;
    }
}
