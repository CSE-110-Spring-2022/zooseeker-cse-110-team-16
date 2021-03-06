package com.example.cse110lab5;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity(tableName = "list_items")
public class ListItem {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @NonNull
    public String text;
    public int order;

    ListItem(@NonNull String text, int order) {
        this.text = text;
        this.order = order;
    }

    @Override
    public String toString() {
        return "ListItem{" +
                ", text='" + text + '\'' +
                ", order=" + order +
                '}';
    }

    public String getText() {
        return text;
    }

    //instead of loading from json file load from map object (made in ms1)
    //load empty list
    public static List<ListItem> loadJSON(Context context, String path) {
        return Collections.emptyList();
    }
}
