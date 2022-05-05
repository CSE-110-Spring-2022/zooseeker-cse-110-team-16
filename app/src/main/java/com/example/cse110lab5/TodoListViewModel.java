package com.example.cse110lab5;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import java.util.List;

public class TodoListViewModel extends AndroidViewModel {
    private LiveData<List<TodoListItem>> todoListItems;
    private final TodoListItemDao todoListItemDao;

    public TodoListViewModel(@NonNull Application application) {
        super(application);
        Context context = getApplication().getApplicationContext();
        ListDatabase db = ListDatabase.getSingleton(context);
        todoListItemDao = db.todoListItemDao();
    }

    public LiveData<List<TodoListItem>> getTodoListItems() {
        if (todoListItems == null) {
            loadUsers();
        }
        return todoListItems;
    }

    private void loadUsers() {
        todoListItems = todoListItemDao.getAllLive();
    }

    public void updateText(TodoListItem todoListItem, String newText) {
        todoListItem.text = newText;
        todoListItemDao.update(todoListItem);
    }

    //creating new todos
    public void createTodo(String text) {
        int endOfListOrder = todoListItemDao.getOrderForAppend();
        TodoListItem newItem = new TodoListItem(text, false, endOfListOrder);
        todoListItemDao.insert(newItem);
    }

    //exercise 4
    public void deleteTodo(TodoListItem todoListItem) {
        todoListItemDao.delete(todoListItem);
    }
}

