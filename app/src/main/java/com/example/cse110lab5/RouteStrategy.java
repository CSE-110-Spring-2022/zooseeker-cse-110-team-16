package com.example.cse110lab5;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;

import java.util.List;

public interface RouteStrategy {
    public List<GraphPath<String, IdentifiedWeightedEdge>> makeRoute(Graph<String, IdentifiedWeightedEdge> edgeData, List<String> selectedExhibits);
}
