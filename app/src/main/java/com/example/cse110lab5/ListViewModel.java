package com.example.cse110lab5;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import java.util.List;

public class ListViewModel extends AndroidViewModel {
    private LiveData<List<ListItem>> listItems;
    private final ListItemDao listItemDao;

    public ListViewModel(@NonNull Application application) {
        super(application);
        Context context = getApplication().getApplicationContext();
        ListDatabase db = ListDatabase.getSingleton(context);
        listItemDao = db.listItemDao();
    }

    public LiveData<List<ListItem>> getListItems() {
        if (listItems == null) {
            loadUsers();
        }
        return listItems;
    }

    private void loadUsers() {
        listItems = listItemDao.getAllLive();
    }

    //creating new todos
    public void createItem(String text) {
        int endOfListOrder = listItemDao.getOrderForAppend();
        ListItem newItem = new ListItem(text, endOfListOrder);
        listItemDao.insert(newItem);
    }
}

