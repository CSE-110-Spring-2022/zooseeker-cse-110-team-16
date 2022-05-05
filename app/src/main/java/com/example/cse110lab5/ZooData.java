package com.example.cse110lab5;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

public class ZooData {

    public static enum Kind {
        // The SerializedName annotation tells GSON how to convert
        // from the strings in our JSON to this Enum.
        @SerializedName("gate") GATE,
        @SerializedName("exhibit") EXHIBIT,
        @SerializedName("intersection") INTERSECTION
    }

    @Entity(tableName = "vertex_info_store")
    public static class VertexInfoStore {

        @PrimaryKey (autoGenerate = true)
        public long idx;

        @NonNull
        public String id;
        public Kind kind;
        public String name;
        public List<String> tags;

        VertexInfoStore(@NonNull String id, Kind kind, String name, List<String> tags) {
            this.id = id;
            this.kind = kind;
            this.name = name;
            this.tags = tags;
        }
    }

    @Entity(tableName = "vertex_info")
    public static class VertexInfo {
        @PrimaryKey (autoGenerate = true)
        public long idx;

        @NonNull
        public String id;
        public Kind kind;
        public String name;
        public String tags;

        VertexInfo(@NonNull String id, Kind kind, String name, String tags) {
            this.id = id;
            this.kind = kind;
            this.name = name;
            this.tags = tags;
        }
    }

    public Map<String, ZooData.VertexInfo> vInfo = null;

    public Map<String, ZooData.VertexInfo> getData(Context context){
        if (vInfo == null) {
            vInfo = ZooData.loadVertexInfoJSON(context, "sample_node_info.json");
        }
        return vInfo;
    }

    //TYPE CONVERTERS FROM VERTEXINFO
    @TypeConverter
    public static List<String> fromJsonToList(String jsonStr) {
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        return new Gson().fromJson(jsonStr, listType);
    }

    @TypeConverter
    public static String fromListToJson(List<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    public static Map<String, ZooData.VertexInfo> loadVertexInfoJSON(Context context, String path) {
        try {
            InputStream inputStream = context.getAssets().open(path);
            Reader reader = new InputStreamReader(inputStream);

            Gson gson = new Gson();
            Type type = new TypeToken<List<ZooData.VertexInfoStore>>(){}.getType();
            List<ZooData.VertexInfoStore> zooData = gson.fromJson(reader, type);

             Map<String, ZooData.VertexInfo> indexedZooData = new HashMap();
             for (ZooData.VertexInfoStore datum : zooData) {
                 indexedZooData.put(datum.id
                         , new VertexInfo(datum.id, datum.kind, datum.name, fromListToJson(datum.tags)));
             }

            return indexedZooData;
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }
}
