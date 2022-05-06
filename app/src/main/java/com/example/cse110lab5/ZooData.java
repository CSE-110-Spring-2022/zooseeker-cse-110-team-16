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

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;
import org.jgrapht.nio.json.JSONImporter;

public class ZooData {

    public String vertexInfoFile = "sample_node_info.json";
    public String edgeInfoFile = "sample_edge_info.json";
    public String graphInfoFile = "sample_zoo_graph.json";

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

        //vertex info getter methods
        public Kind getKind() {
            return kind;
        }

        public String getName() {
            return name;
        }

        public String getTags() {
            return tags;
        }
    }

    public Map<String, ZooData.VertexInfo> vInfo = null;
    public Map<String, ZooData.EdgeInfo> eInfo = null;
    public Graph<String, IdentifiedWeightedEdge> gInfo = null;

    public void populateDatabase(Context context) {
        if (vInfo == null) {
            vInfo = ZooData.loadVertexInfoJSON(context, vertexInfoFile);
        }
        if (eInfo == null) {
            eInfo = ZooData.loadEdgeInfoJSON(context, edgeInfoFile);
        }
        if (gInfo == null) {
            gInfo = ZooData.loadZooGraphJSON(context, graphInfoFile);
        }
    }

    public Map<String, ZooData.VertexInfo> getVertexDatabase(){
        return vInfo;
    }

    public Map<String, ZooData.EdgeInfo> getEdgeDatabase(){
        return eInfo;
    }

    public Graph<String, IdentifiedWeightedEdge> getGraphDatabase() {
        return gInfo;
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

    //READING EDGE INFO
    @Entity(tableName = "edge_info")
    public static class EdgeInfo {
        @PrimaryKey (autoGenerate = true)
        public long idx;

        @NonNull
        public String id;
        public String street;

        EdgeInfo(@NonNull String id, String street) {
            this.id = id;
            this.street = street;
        }

        //edge info getter methods
        public String getStreet() {
            return street;
        }
    }

    public static Map<String, ZooData.EdgeInfo> loadEdgeInfoJSON(Context context, String path) {
        try {
            InputStream inputStream = context.getAssets().open(path);
            Reader reader = new InputStreamReader(inputStream);

            Gson gson = new Gson();
            Type type = new TypeToken<List<ZooData.EdgeInfo>>(){}.getType();
            List<ZooData.EdgeInfo> zooData = gson.fromJson(reader, type);

            Map<String, ZooData.EdgeInfo> indexedZooData = zooData
                    .stream()
                    .collect(Collectors.toMap(v -> v.id, datum -> datum));

            return indexedZooData;
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }

    //creating graph database
    public static Graph<String, IdentifiedWeightedEdge> loadZooGraphJSON(Context context, String path) {
        try {
            // Create an empty graph to populate.
            Graph<String, IdentifiedWeightedEdge> g = new DefaultUndirectedWeightedGraph<>(IdentifiedWeightedEdge.class);

            // Create an importer that can be used to populate our empty graph.
            JSONImporter<String, IdentifiedWeightedEdge> importer = new JSONImporter<>();

            // We don't need to convert the vertices in the graph, so we return them as is.
            importer.setVertexFactory(v -> v);

            // We need to make sure we set the IDs on our edges from the 'id' attribute.
            // While this is automatic for vertices, it isn't for edges. We keep the
            // definition of this in the IdentifiedWeightedEdge class for convenience.
            importer.addEdgeAttributeConsumer(IdentifiedWeightedEdge::attributeConsumer);

            // On Android, you would use context.getAssets().open(path) here like in Lab 5.
            InputStream inputStream = context.getAssets().open(path);
            Reader reader = new InputStreamReader(inputStream);

            // And now we just import it!
            importer.importGraph(g, reader);

            return g;
        } catch (IOException e) {
            e.printStackTrace();
            //might give error when ioexception happens
            return null;
        }
    }
}
