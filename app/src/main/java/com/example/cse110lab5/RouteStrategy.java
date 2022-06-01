package com.example.cse110lab5;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;

import java.util.List;
import java.util.Map;

public interface RouteStrategy {
    public List<String> makeRoute(Graph<String, IdentifiedWeightedEdge> edgeData, Map<String, ZooData.VertexInfo> vertexData, List<String> selectedExhibits);
}
