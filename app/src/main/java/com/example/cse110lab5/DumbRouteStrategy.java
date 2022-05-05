package com.example.cse110lab5;

import java.util.List;

public class DumbRouteStrategy implements RouteStrategy {
    @Override
    public List<ZooData> makeRoute(List<ZooData> selectedExhibits) {
        return selectedExhibits;
    }
}
