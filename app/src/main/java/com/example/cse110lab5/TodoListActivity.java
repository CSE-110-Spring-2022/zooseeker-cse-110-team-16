package com.example.cse110lab5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import java.util.Map;

public class TodoListActivity extends AppCompatActivity {
    // TODO: Add a button to go to the plan activity
    // TODO: Pass selected exhibits to the plan activity

    //Exposed for testing purposes later....
    public RecyclerView recyclerView;
    private TodoListViewModel viewModel;
    private SearchView newTodoText;
    private Button addTodoButton;
    private Map<String, ZooData.VertexInfo> nodeData;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> searchResults;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        viewModel = new ViewModelProvider(this)
                .get(TodoListViewModel.class);

        TodoListAdapter adapter = new TodoListAdapter();
        adapter.setHasStableIds(true);
        adapter.setOnTextEditedHandler(viewModel::updateText);
        adapter.setOnDeleteBtnClickedHandler(viewModel::deleteTodo);    //exercise 4
        viewModel.getTodoListItems().observe(this, adapter::setTodoListItems);

        recyclerView = findViewById(R.id.todo_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        //create new todos
        this.newTodoText = this.findViewById(R.id.new_todo_text);
        this.addTodoButton = this.findViewById(R.id.add_todo_btn);

        addTodoButton.setOnClickListener(this::onAddTodoClicked);

        Context context = getApplicationContext();
        ZooData zooData = new ZooData();
        zooData.populateDatabase(context);
        nodeData = zooData.getVertexDatabase();
        //add edge and graph databases if you need them

        searchResults = new ArrayList<String>(){};
        //filters nodeData based on search query
        for (ZooData.VertexInfo vertex : nodeData.values()){
            String nodeName = vertex.getName();
            searchResults.add(nodeName);
        }

        listView = findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
        listView.setAdapter(arrayAdapter);


    }

    //shiv's add click
//    void onAddTodoClicked(View view) {
//        String text = newTodoText.getText().toString();
//        newTodoText.setText("");
//        viewModel.createTodo(text);
//    }

//https://www.youtube.com/watch?v=M3UDh9mwBd8
// How to Add Search View in Toolbar in Android Studio | SearchView on Toolbar | Actionbar
// 5/6/2022
// copied format and changed some things
    void onAddTodoClicked(View view) {
        arrayAdapter.clear();
        String query = newTodoText.getQuery().toString();
        searchResults = new ArrayList<String>(){};
        //filters nodeData based on search query
        for (ZooData.VertexInfo vertex : nodeData.values()){
            String nodeName = vertex.getName();
            if (nodeName.contains(query) || vertex.getTags().contains(query)){
                searchResults.add(nodeName);
            }
        }
        arrayAdapter.addAll(searchResults);
    }

    public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.ViewHolder> {

        private List<TodoListItem> todoItems = Collections.emptyList();
        private BiConsumer<TodoListItem, String> onTextEditedHandler;
        private Consumer<TodoListItem> onDeleteBtnClicked;
        public Map<String, ZooData.VertexInfo> vInfo;

        public void setTodoListItems(List<TodoListItem> newTodoItems) {
            this.todoItems.clear();
            this.todoItems = newTodoItems;
            notifyDataSetChanged();
        }

        public void setOnTextEditedHandler(BiConsumer<TodoListItem, String> onTextEdited) {
            this.onTextEditedHandler = onTextEdited;
        }

        //exercise 4
        public void setOnDeleteBtnClickedHandler(Consumer<TodoListItem> onDeleteBtnClicked) {
            this.onDeleteBtnClicked = onDeleteBtnClicked;
        }

        @NonNull
        @Override
        public TodoListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.todo_list_item, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TodoListAdapter.ViewHolder holder, int position) {
            holder.setTodoItem(todoItems.get(position));
        }

        @Override
        public int getItemCount() {
            return todoItems.size();
        }

        @Override
        public long getItemId(int position) {
            return todoItems.get(position).id;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView textView;
                    private final TextView deleteBtn;   //exercise 4
            private TodoListItem todoItem;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                this.textView = itemView.findViewById(R.id.todo_item_text);
                //            this.checkBox = itemView.findViewById(R.id.completed);
                            this.deleteBtn = itemView.findViewById(R.id.delete_btn);    //exercise 4

                this.textView.setOnFocusChangeListener((view, hasFocus) -> {
                    if (!hasFocus) {
                        onTextEditedHandler.accept(todoItem, textView.getText().toString());
                    }
                });

                //exercise 4
                this.deleteBtn.setOnClickListener(view -> {
                    if (onDeleteBtnClicked == null) return;
                    onDeleteBtnClicked.accept(todoItem);
                });
            }

            public TodoListItem getTodoItem() {
                return todoItem;
            }

            public void setTodoItem(TodoListItem todoItem) {
                this.todoItem = todoItem;
                this.textView.setText(todoItem.text);
//                this.checkBox.setChecked(todoItem.completed);
            }
        }
    }
}
