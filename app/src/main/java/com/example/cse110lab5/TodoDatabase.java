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

    @VisibleForTesting
    public static void injectTestDatabase(TodoDatabase testDatabase) {
        if (singleton != null) {
            singleton.close();
        }
        singleton = testDatabase;
    }
}
