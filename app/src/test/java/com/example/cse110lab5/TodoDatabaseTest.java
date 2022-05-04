package com.example.cse110lab5;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Map;

@RunWith(AndroidJUnit4.class)
public class TodoDatabaseTest {
    //private VertexInfoItemDao dao;
    private TodoDatabase db;

    //we might not need a database, only a map and/or list to store vertex info
//    @Before
//    public void createDb() {
//        Context context = ApplicationProvider.getApplicationContext();
//        db = Room.inMemoryDatabaseBuilder(context, TodoDatabase.class)
//                .allowMainThreadQueries()
//                .build();
//        //dao = db.vertexInfoItemDao();
//    }
//
//    @After
//    public void closeDb() throws IOException {
//        db.close();
//    }

//    @Test
//    public void testInsert() {
//        TodoListItem item1 = new TodoListItem("Pizza time", false, 0);
//        TodoListItem item2 = new TodoListItem("Photos of Spider-Man", false, 1);
//
//        long id1 = dao.insert(item1);
//        long id2 = dao.insert(item2);
//
//        assertNotEquals(id1, id2);
//    }

    //WILL NEED TO IMPLEMENT THIS IN SEARCH
//    @Test
//    public void testGet() {
//        TodoListItem insertedItem = new TodoListItem("Pizza time", false, 0);
//        long id = dao.insert(insertedItem);
//
//        TodoListItem item = dao.get(id);
//        assertEquals(id, item.id);
//        assertEquals(insertedItem.text, item.text);
//        assertEquals(insertedItem.completed, item.completed);
//        assertEquals(insertedItem.order, item.order);
//    }
/*
    @Test
    public void testUpdate() {
        TodoListItem item = new TodoListItem("Pizza time", false, 0);
        long id = dao.insert(item);

        item = dao.get(id);
        item.text = "Photos of Spider-Man";
        int itemsUpdated = dao.update(item);
        assertEquals(1, itemsUpdated);

        item = dao.get(id);
        assertNotNull(item);
        assertEquals("Photos of Spider-Man", item.text);
    } */
/*
    @Test
    public void testDelete() {
        TodoListItem item = new TodoListItem("Pizza time", false, 0);
        long id = dao.insert(item);

        item = dao.get(id);
        int itemsDeleted = dao.delete(item);
        assertEquals(1, itemsDeleted);
        assertNull(dao.get(id));
    }
*/
    @Test
    public void testDatabaseNotNull() {
        Context context = ApplicationProvider.getApplicationContext();
        ZooData zooData = new ZooData();
        Map<String, ZooData.VertexInfo> data = zooData.getData(context);
        assertNotNull(data);
    }

    @Test
    public void testAnimalInDatabase() {
        Context context = ApplicationProvider.getApplicationContext();
        ZooData zooData = new ZooData();
        Map<String, ZooData.VertexInfo> data = zooData.getData(context);
        assertEquals(data.get("gorillas").name ,  "Gorillas");
    }

    @Test
    public void testAnimalTagInDatabase() {
        Context context = ApplicationProvider.getApplicationContext();
        ZooData zooData = new ZooData();
        Map<String, ZooData.VertexInfo> data = zooData.getData(context);
        assertEquals(data.get("gorillas").tags ,  "[\"gorilla\",\"monkey\",\"ape\",\"mammal\"]");
    }
}
