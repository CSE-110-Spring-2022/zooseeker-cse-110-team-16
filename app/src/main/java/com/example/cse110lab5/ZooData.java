package com.example.cse110lab5;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
//import androidx.room.jarjarred.org.antlr.v4.misc.Graph;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
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

    // json filenames for updating zoo data
    private String vertexInfoFile = "exhibit_info.json";
    private String edgeInfoFile = "trail_info.json";
    private String graphInfoFile = "zoo_graph.json";

    public static enum Kind {
        // The SerializedName annotation tells GSON how to convert
        // from the strings in our JSON to this Enum.
        // *** added new kind "exhibit_group" ms2 ***
        @SerializedName("gate") GATE,
        @SerializedName("exhibit") EXHIBIT,
        @SerializedName("intersection") INTERSECTION,
        @SerializedName("exhibit_group") EXHIBIT_GROUP
    }

    // vertex info object type that is read by gson from json file
    @Entity(tableName = "vertex_info_store")
    public static class VertexInfoStore {

        @PrimaryKey (autoGenerate = true)
        public long idx;

        @NonNull
        public String id;
        public Kind kind;
        public String name;
        public List<String> tags;
        //new info below ms2
        @SerializedName("group_id")
        public String groupId;
        public double lat;
        public double lng;

        VertexInfoStore(@NonNull String id, String groupId, Kind kind, String name, List<String> tags, double lat, double lng) {
            this.id = id;
            this.kind = kind;
            this.name = name;
            this.tags = tags;
            //new info below ms2
            this.groupId = groupId;
            this.lat = lat;
            this.lng = lng;
        }
    }

    // vertex info object type that is stored in database
    @Entity(tableName = "vertex_info")
    public static class VertexInfo {

        @PrimaryKey (autoGenerate = true)
        public long idx;

        @NonNull
        public String id;
        public Kind kind;
        public String name;
        public String tags;
        //new info below ms2
        @SerializedName("group_id")
        public String groupId;
        public double lat;
        public double lng;

        VertexInfo(@NonNull String id, String groupId, Kind kind, String name, String tags, double lat, double lng) {
            this.id = id;
            this.kind = kind;
            this.name = name;
            this.tags = tags;
            //new info below ms2
            this.groupId = groupId;
            this.lat = lat;
            this.lng = lng;
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

        public String getId() {
            return id;
        }

        //return methods for new data ms2
        public String getGroupId() {
            return groupId;
        }

        public double getLat() {
            return lat;
        }

        public double getLng() {
            return lng;
        }
    }

    // edge info object read from json
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

    // load read file data into database
    private Map<String, ZooData.VertexInfo> vInfo = null;
    private Map<String, ZooData.EdgeInfo> eInfo = null;
    private Graph<String, IdentifiedWeightedEdge> gInfo = null;

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

    // database getter methods
    public Map<String, ZooData.VertexInfo> getVertexDatabase(){
        return vInfo;
    }

    public Map<String, ZooData.EdgeInfo> getEdgeDatabase(){
        return eInfo;
    }

    public Graph<String, IdentifiedWeightedEdge> getGraphDatabase() {
        return gInfo;
    }

    // all code below is sourced from given project files
    // https://github.com/CSE-110-Spring-2022/ZooSeeker-Assets
    // https://gist.github.com/DylanLukes/2198a9e4a889e3a86cb6b8b0c8d0e2a5
    // accessed before 5/8/2022
    // Copied code and altered statements to work with our app
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

            for (ZooData.VertexInfoStore item : zooData) {
                System.out.println(item.toString());
            }

            // loads vertexInfoStore object and stores in database as vertexInfo object
             Map<String, ZooData.VertexInfo> indexedZooData = new HashMap();
             for (ZooData.VertexInfoStore datum : zooData) {
                 indexedZooData.put(datum.id
                         , new VertexInfo(datum.id, datum.groupId, datum.kind, datum.name, fromListToJson(datum.tags), datum.lat, datum.lng));
             }

            return indexedZooData;
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyMap();
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
