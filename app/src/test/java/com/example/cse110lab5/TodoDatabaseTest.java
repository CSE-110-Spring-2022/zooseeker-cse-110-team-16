package com.example.cse110lab5;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.jgrapht.Graph;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Map;

@RunWith(AndroidJUnit4.class)
public class TodoDatabaseTest {

    @Test
    public void testVertexDatabaseNotNull() {
        Context context = ApplicationProvider.getApplicationContext();
        ZooData zooData = new ZooData();
        zooData.populateDatabase(context);
        Map<String, ZooData.VertexInfo> data = zooData.getVertexDatabase();
        assertNotNull(data);
    }

    @Test
    public void testAnimalInDatabase() {
        Context context = ApplicationProvider.getApplicationContext();
        ZooData zooData = new ZooData();
        zooData.populateDatabase(context);
        Map<String, ZooData.VertexInfo> data = zooData.getVertexDatabase();
        assertEquals(data.get("gorillas").name ,  "Gorillas");
    }

    @Test
    public void testAnimalTagInDatabase() {
        Context context = ApplicationProvider.getApplicationContext();
        ZooData zooData = new ZooData();
        zooData.populateDatabase(context);
        Map<String, ZooData.VertexInfo> data = zooData.getVertexDatabase();
        assertEquals(data.get("gorillas").getTags() ,  "[\"gorilla\",\"monkey\",\"ape\",\"mammal\"]");
    }

    //testing vertex info getter methods
    @Test
    public void testVertexInfoGetters() {
        Context context = ApplicationProvider.getApplicationContext();
        ZooData zooData = new ZooData();
        zooData.populateDatabase(context);
        Map<String, ZooData.VertexInfo> data = zooData.getVertexDatabase();

        assertEquals(data.get("gorillas").getName() ,  "Gorillas");
        assertEquals(data.get("gorillas").getTags() ,  "[\"gorilla\",\"monkey\",\"ape\",\"mammal\"]");
        assertEquals(data.get("gorillas").getKind().toString(), "EXHIBIT");
    }

    //Testing edge info database
    @Test
    public void testEdgeDatabaseNotNull() {
        Context context = ApplicationProvider.getApplicationContext();
        ZooData zooData = new ZooData();
        zooData.populateDatabase(context);
        Map<String, ZooData.EdgeInfo> data = zooData.getEdgeDatabase();
        assertNotNull(data);
    }

    @Test
    public void testEdgeInDatabase() {
        Context context = ApplicationProvider.getApplicationContext();
        ZooData zooData = new ZooData();
        zooData.populateDatabase(context);
        Map<String, ZooData.EdgeInfo> data = zooData.getEdgeDatabase();
        assertEquals(data.get("edge-3").street ,  "Africa Rocks Street");
    }

    @Test
    public void testEdgeInfoGetters() {
        Context context = ApplicationProvider.getApplicationContext();
        ZooData zooData = new ZooData();
        zooData.populateDatabase(context);
        Map<String, ZooData.EdgeInfo> data = zooData.getEdgeDatabase();
        assertEquals(data.get("edge-3").getStreet() ,  "Africa Rocks Street");
    }

    //testing graph database
    @Test
    public void testGraphInfoNotNull() {
        Context context = ApplicationProvider.getApplicationContext();
        ZooData zooData = new ZooData();
        zooData.populateDatabase(context);
        Graph<String, IdentifiedWeightedEdge> data = zooData.getGraphDatabase();

        assertNotNull(data);
    }

    @Test
    public void testEdgeWeight() {
        Context context = ApplicationProvider.getApplicationContext();
        ZooData zooData = new ZooData();
        zooData.populateDatabase(context);
        Graph<String, IdentifiedWeightedEdge> data = zooData.getGraphDatabase();
        IdentifiedWeightedEdge testEdge = data.getEdge("entrance_plaza", "arctic_foxes");

        assertEquals(data.getEdgeWeight(testEdge), 300.0, 0.01);
        assertEquals(testEdge.getId(), "edge-4");
    }
}
