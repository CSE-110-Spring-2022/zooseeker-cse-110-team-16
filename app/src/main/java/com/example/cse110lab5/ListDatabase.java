package com.example.cse110lab5;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;
import java.util.concurrent.Executors;

@Database(entities = {ListItem.class}, version = 1)
public abstract class ListDatabase extends RoomDatabase {
    private static ListDatabase singleton = null;

    public abstract ListItemDao listItemDao();

    public synchronized static ListDatabase getSingleton(Context context) {
        if (singleton == null) {
            singleton = ListDatabase.makeDatabase(context);
        }
        return singleton;
    }

    //deleted contents of demo_todos.json so list is not populated at start
    private static ListDatabase makeDatabase(Context context) {
        return Room.databaseBuilder(context, ListDatabase.class, "zoo_app.db")
                .allowMainThreadQueries()
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadScheduledExecutor().execute(() -> {
                            List<ListItem> items = ListItem
                                    .loadJSON(context, "demo_todos.json");  //TODO refactor/delete unused parameters
                            getSingleton(context).listItemDao().insertAll(items);
                        });
                    }
                })
                .build();
    }

    @VisibleForTesting
    public static void injectTestDatabase(ListDatabase testDatabase) {
        if (singleton != null) {
            singleton.close();
        }
        singleton = testDatabase;
    }
}

