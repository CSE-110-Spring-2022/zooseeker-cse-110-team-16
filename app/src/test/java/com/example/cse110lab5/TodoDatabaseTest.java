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
import java.util.List;
import java.util.Map;

@RunWith(AndroidJUnit4.class)
public class TodoDatabaseTest {

    private ListItemDao dao;
    private ListDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, ListDatabase.class)
                .allowMainThreadQueries()
                .build();
        dao = db.listItemDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void testInsert() {
        ListItem item1 = new ListItem("Pizza time", 0);
        ListItem item2 = new ListItem("Photos of Spider man", 1);

        long id1 = dao.insert(item1);
        long id2 = dao.insert(item2);

        //check that these have all been inserted with unique ids
        assertNotEquals(id1, id2);
    }

    @Test
    public void testGet() {
        ListItem insertedItem = new ListItem("Pizza time", 0);
        long id = dao.insert(insertedItem);

        ListItem item = dao.get(id);
        assertEquals(id, item.id);
        assertEquals(insertedItem.text, item.text);
        assertEquals(insertedItem.order, item.order);
    }


    @Test
    public void testUpdate() {
        ListItem item = new ListItem("Pizza time", 0);
        long id = dao.insert(item);

        item = dao.get(id);
        item.text = "Photos of Spider-Man";
        int itemsUpdated = dao.update(item);
        assertEquals(1, itemsUpdated);

        item = dao.get(id);
        assertNotNull(item);
        assertEquals("Photos of Spider-Man", item.text);
    }

    @Test
    public void testDelete() {
        ListItem item = new ListItem("Pizza time", 0);
        long id = dao.insert(item);

        item = dao.get(id);
        int itemsDeleted = dao.delete(item);
        assertEquals(1, itemsDeleted);
        assertNull(dao.get(id));
    }

    //testing vertex info database
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
        assertEquals(data.get("gorilla").getName() ,  "Gorillas");
    }

    @Test
    public void testAnimalTagInDatabase() {
        Context context = ApplicationProvider.getApplicationContext();
        ZooData zooData = new ZooData();
        zooData.populateDatabase(context);
        Map<String, ZooData.VertexInfo> data = zooData.getVertexDatabase();
        assertEquals(data.get("gorilla").getTags() ,  "[\"gorilla\",\"primate\",\"mammal\",\"great\",\"ape\",\"primate\",\"africa\"]");
    }

    //testing vertex info getter methods
    @Test
    public void testVertexInfoGetters() {
        Context context = ApplicationProvider.getApplicationContext();
        ZooData zooData = new ZooData();
        zooData.populateDatabase(context);
        Map<String, ZooData.VertexInfo> data = zooData.getVertexDatabase();

        assertEquals(data.get("gorilla").getName() ,  "Gorillas");
        assertEquals(data.get("gorilla").getTags() ,  "[\"gorilla\",\"primate\",\"mammal\",\"great\",\"ape\",\"primate\",\"africa\"]");
        assertEquals(data.get("gorilla").getKind().toString(), "EXHIBIT");
        assertEquals(data.get("gorilla").getParentId(), null);
        assertEquals(data.get("gorilla").getLat(), 32.74812588554637, 0.0001);
        assertEquals(data.get("gorilla").getLng(), -117.17565073656901, 0.0001);
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
        assertEquals(data.get("fern_to_fern").street ,  "Fern Canyon Trail");
    }

    @Test
    public void testEdgeInfoGetters() {
        Context context = ApplicationProvider.getApplicationContext();
        ZooData zooData = new ZooData();
        zooData.populateDatabase(context);
        Map<String, ZooData.EdgeInfo> data = zooData.getEdgeDatabase();
        assertEquals(data.get("fern_to_fern").getStreet() ,  "Fern Canyon Trail");
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
        IdentifiedWeightedEdge testEdge = data.getEdge("koi", "intxn_front_lagoon_2");

        assertEquals(data.getEdgeWeight(testEdge), 30.0, 0.01);
        assertEquals(testEdge.getId(), "lagoon2_to_koi");
    }
}
