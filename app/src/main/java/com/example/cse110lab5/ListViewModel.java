package com.example.cse110lab5;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import java.util.List;

public class ListViewModel extends AndroidViewModel {
    public ListViewModel(@NonNull Application application) {
        super(application);
    }
//    private LiveData<List<ListItem>> listItems;
//    private final ListItemDao listItemDao;
//
//    public ListViewModel(@NonNull Application application) {
//        super(application);
//        Context context = getApplication().getApplicationContext();
//        ListDatabase db = ListDatabase.getSingleton(context);
//        listItemDao = db.listItemDao();
//    }
//
//    public LiveData<List<ListItem>> getListItems() {
//        if (listItems == null) {
//            loadUsers();
//        }
//        return listItems;
//    }
//
//    private void loadUsers() {
//        listItems = listItemDao.getAllLive();
//    }
//
//    //TODO delete later if not used
//    public void updateText(ListItem listItem, String newText) {
//        listItem.text = newText;
//        listItemDao.update(listItem);
//    }
//
//    //creating new todos
//    public void createItem(String text) {
//        int endOfListOrder = listItemDao.getOrderForAppend();
//        ListItem newItem = new ListItem(text, endOfListOrder);
//        listItemDao.insert(newItem);
//    }
//
//    //exercise 4
//    public void deleteItem(ListItem listItem) {
//        listItemDao.delete(listItem);
//    }
}

