package com.example.cse110lab5;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

@Database(entities = {ZooData.VertexInfo.class}, version = 1)
public abstract class TodoDatabase extends RoomDatabase {
    private static TodoDatabase singleton = null;
    public Map<String, ZooData.VertexInfo> vInfo = null;

//    public abstract VertexInfoItemDao vertexInfoItemDao();

    //testing change
    public Map<String, ZooData.VertexInfo> getData(Context context){
        if (vInfo == null) {
            System.out.println("TodoDatabase line 26");
            vInfo = ZooData.loadVertexInfoJSON(context, "sample_node_info.json");
        }
        else {
            System.out.println("vInfo is not null");
            //System.out.println(vInfo);
        }
        return vInfo;
    }

//    //testing the reading data without database
//    public synchronized static TodoDatabase getSingleton(Context context) {
//        if (singleton == null) {
//            singleton = TodoDatabase.makeDatabase(context);
//        }
//        return singleton;
//    }

//    private static TodoDatabase makeDatabase(Context context) {
//        return Room.databaseBuilder(context, TodoDatabase.class, "todo_app.db")
//                .allowMainThreadQueries()
//                .addCallback(new Callback() {
//                    @Override
//                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
//                        super.onCreate(db);
//                        Executors.newSingleThreadScheduledExecutor().execute(() -> {
//                            Map<String, ZooData.VertexInfo> todos = ZooData
//                                    .loadVertexInfoJSON(context, "sample_node_info.json");
//                            getSingleton(context).vertexInfoItemDao().insertAll(todos);
//                        });
//                    }
//                })
//                .build();
//    }


    //testing
//    private static TodoDatabase makeDatabase(Context context) {
//        return Room.databaseBuilder(context, TodoDatabase.class, "todo_app.db")
//                .allowMainThreadQueries()
//                .addCallback(new Callback() {
//                    @Override
//                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
//                        super.onCreate(db);
//                        Executors.newSingleThreadScheduledExecutor().execute(() -> {
//                            List<TodoListItem> todos = TodoListItem
//                                    .loadJSON(context, "demo_todos.json");
//                            getSingleton(context).todoListItemDao().insertAll(todos);
//                        });
//                    }
//                })
//                .build();
//    }

    @VisibleForTesting
    public static void injectTestDatabase(TodoDatabase testDatabase) {
        if (singleton != null) {
            singleton.close();
        }
        singleton = testDatabase;
    }
}
