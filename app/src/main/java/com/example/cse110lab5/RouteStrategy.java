package com.example.cse110lab5;

import java.util.List;

public interface RouteStrategy {
    public List<ZooData> makeRoute(List<ZooData> selectedExhibits);
}
