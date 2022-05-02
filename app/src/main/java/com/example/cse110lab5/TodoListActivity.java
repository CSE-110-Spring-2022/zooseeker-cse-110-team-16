package com.example.cse110lab5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;


public class TodoListActivity extends AppCompatActivity {
    //Exposed for testing purposes later....
    public RecyclerView recyclerView;

    private TodoListViewModel viewModel;
    private EditText newTodoText;
    private Button addTodoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        viewModel = new ViewModelProvider(this)
                .get(TodoListViewModel.class);

        TodoListAdapter adapter = new TodoListAdapter();
        adapter.setHasStableIds(true);
//        adapter.setOnCheckBoxClickedHandler(viewModel::toggleCompleted);
        adapter.setOnTextEditedHandler(viewModel::updateText);
//        adapter.setOnDeleteBtnClickedHandler(viewModel::deleteTodo);    //exercise 4
        viewModel.getTodoListItems().observe(this, adapter::setTodoListItems);

        recyclerView = findViewById(R.id.todo_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        //create new todos
        this.newTodoText = this.findViewById(R.id.new_todo_text);
        this.addTodoButton = this.findViewById(R.id.add_todo_btn);

        addTodoButton.setOnClickListener(this::onAddTodoClicked);
    }

    //create new todos
    void onAddTodoClicked(View view) {
        String text = newTodoText.getText().toString();
        newTodoText.setText("");
        viewModel.createTodo(text);
    }

    public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.ViewHolder> {

        private List<TodoListItem> todoItems = Collections.emptyList();
        private Consumer<TodoListItem> onCheckBoxClicked;
        private BiConsumer<TodoListItem, String> onTextEditedHandler;
        private Consumer<TodoListItem> onDeleteBtnClicked;

        public void setTodoListItems(List<TodoListItem> newTodoItems) {
            this.todoItems.clear();
            this.todoItems = newTodoItems;
            notifyDataSetChanged();
        }
/*
        public void setOnCheckBoxClickedHandler(Consumer<TodoListItem> onCheckBoxClicked) {
            this.onCheckBoxClicked = onCheckBoxClicked;
        } */

        public void setOnTextEditedHandler(BiConsumer<TodoListItem, String> onTextEdited) {
            this.onTextEditedHandler = onTextEdited;
        }
/*
        //exercise 4
        public void setOnDeleteBtnClickedHandler(Consumer<TodoListItem> onDeleteBtnClicked) {
            this.onDeleteBtnClicked = onDeleteBtnClicked;
        } */

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
    //        private final CheckBox checkBox;
    //        private final TextView deleteBtn;   //exercise 4
            private TodoListItem todoItem;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                this.textView = itemView.findViewById(R.id.todo_item_text);
    //            this.checkBox = itemView.findViewById(R.id.completed);
    //            this.deleteBtn = itemView.findViewById(R.id.delete_btn);    //exercise 4

                this.textView.setOnFocusChangeListener((view, hasFocus) -> {
                    if (!hasFocus) {
                        onTextEditedHandler.accept(todoItem, textView.getText().toString());
                    }
                });
/*
                this.checkBox.setOnClickListener(view -> {
                    if (onCheckBoxClicked == null) return;
                    onCheckBoxClicked.accept(todoItem);
                }); */
/*
                //exercise 4
                this.deleteBtn.setOnClickListener(view -> {
                    if (onDeleteBtnClicked == null) return;
                    onDeleteBtnClicked.accept(todoItem);
                }); */
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