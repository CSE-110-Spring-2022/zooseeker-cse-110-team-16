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

import java.util.Map;


public class TodoListActivity extends AppCompatActivity {
    //Exposed for testing purposes later....
    public RecyclerView recyclerView;

    private EditText newTodoText;
    private Button addTodoButton;
    public Map<String, ZooData.VertexInfo> vInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        TodoListAdapter adapter = new TodoListAdapter();
        adapter.setHasStableIds(true);
    }

    //create new todos
    void onAddTodoClicked(View view) {
        String text = newTodoText.getText().toString();
        newTodoText.setText("");
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

        public void setOnTextEditedHandler(BiConsumer<TodoListItem, String> onTextEdited) {
            this.onTextEditedHandler = onTextEdited;
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
            private TodoListItem todoItem;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                this.textView = itemView.findViewById(R.id.todo_item_text);

                this.textView.setOnFocusChangeListener((view, hasFocus) -> {
                    if (!hasFocus) {
                        onTextEditedHandler.accept(todoItem, textView.getText().toString());
                    }
                });
            }

            public TodoListItem getTodoItem() {
                return todoItem;
            }

            public void setTodoItem(TodoListItem todoItem) {
                this.todoItem = todoItem;
                this.textView.setText(todoItem.text);
            }
        }
    }
}