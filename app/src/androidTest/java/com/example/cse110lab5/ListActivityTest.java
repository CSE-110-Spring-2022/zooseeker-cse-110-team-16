package com.example.cse110lab5;

import android.app.ListActivity;
import android.content.Context;
import android.view.View;

import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ListActivityTest {
    ListDatabase testDb;
    ListItemDao listItemDao;

    private static void forceLayout(RecyclerView recyclerView) {
        recyclerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        recyclerView.layout(0, 0, 1080, 2280);
    }

    @Before
    public void resetDatabase() {
        Context context = ApplicationProvider.getApplicationContext();
        testDb = Room.inMemoryDatabaseBuilder(context, ListDatabase.class)
                .allowMainThreadQueries()
                .build();
        ListDatabase.injectTestDatabase(testDb);

        List<ListItem> items = ListItem.loadJSON(context, "demo_todos.json");
        listItemDao = testDb.listItemDao();
        listItemDao.insertAll(items);
    }

//    @Test
//    public void testAddNewItem() {
//        String newText = "Ensure all tests pass";
//
//        ActivityScenario<TodoListActivity> scenario
//                = ActivityScenario.launch(TodoListActivity.class);
//        scenario.moveToState(Lifecycle.State.CREATED);
//        scenario.moveToState(Lifecycle.State.STARTED);
//        scenario.moveToState(Lifecycle.State.RESUMED);
//
//        scenario.onActivity(activity -> {
//            List<ListItem> beforeList = listItemDao.getAll();
//
//            newItemText.setText(newText);
//
//        })
//    }
}
