package com.example.cse110lab5;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ListItemDao {
    @Insert
    long insert(ListItem listItem);

    @Insert
    List<Long> insertAll(List<ListItem> listItem);

    @Query("SELECT * FROM 'list_items' WHERE 'id'=:id")
    ListItem get(long id);

    @Query("SELECT * FROM 'list_items' ORDER BY 'order'")
    List<ListItem> getAll();

    @Update
    int update(ListItem listItem);

    @Delete
    int delete(ListItem listItem);

    @Query("SELECT * FROM 'list_items' ORDER BY 'order'")
    LiveData<List<ListItem>> getAllLive();

    //creating new todos
    @Query("SELECT 'order' + 1 FROM 'list_items' ORDER BY 'order' DESC LIMIT 1")
    int getOrderForAppend();
}
