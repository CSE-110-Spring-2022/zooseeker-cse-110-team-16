package com.example.cse110lab5;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.jgrapht.Graph;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class NearestNeighborTest {
    private final ZooData zooData = new ZooData();
    private final RouteStrategy s = new NearestNeighborRouteStrategy();
    private Graph<String, IdentifiedWeightedEdge> edgeData;
    private final List<String> sortedVertexList = new ArrayList<>();
    private final Context context = ApplicationProvider.getApplicationContext();

    @Before
    public void runBefore() {
        zooData.populateDatabase(context);
        edgeData = zooData.getGraphDatabase();
    }

    @Test
    public void test1Vertex() {
        List<String> selectedExhibits = new ArrayList<>(Arrays.asList("entrance_exit_gate"));
        List<String> expected = Arrays.asList("entrance_exit_gate");
        assertEquals(expected, s.makeRoute(edgeData, selectedExhibits));
    }

    @Test
    public void test2Vertices() {
        List<String> selectedExhibits = new ArrayList<>(Arrays.asList("entrance_exit_gate", "intxn_front_treetops"));
        List<String> expected = Arrays.asList("entrance_exit_gate", "intxn_front_treetops");
        assertEquals(expected, s.makeRoute(edgeData, selectedExhibits));
    }

    @Test
    public void test3Vertices() {
        List<String> selectedExhibits = new ArrayList<>(Arrays.asList("entrance_exit_gate", "intxn_front_treetops", "intxn_front_monkey"));
        List<String> expected = Arrays.asList("entrance_exit_gate", "intxn_front_treetops", "intxn_front_monkey");
        assertEquals(expected, s.makeRoute(edgeData, selectedExhibits));
    }

    @Test
    public void test3VerticesOppositeOrder() {
        List<String> selectedExhibits = new ArrayList<>(Arrays.asList("entrance_exit_gate", "intxn_front_treetops", "intxn_front_monkey"));
        List<String> expected = Arrays.asList("entrance_exit_gate", "intxn_front_treetops", "intxn_front_monkey");
        //"intxn_front_monkey", "intxn_front_treetops", "entrance_exit_gate"
        assertEquals(expected, s.makeRoute(edgeData, selectedExhibits));
    }
}
