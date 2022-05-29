package com.example.cse110lab5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import java.util.Map;

public class TodoListActivity extends AppCompatActivity {

    //Exposed for testing purposes later....
    public RecyclerView recyclerView;

    private ListViewModel viewModel;
    private SearchView searchText;
    private Button searchButton;
    private ListView listView;

    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> searchResults;
    private ArrayList<String> searchResultsID;
    private ArrayList<String> addedAnimals = new ArrayList<String>();
    private Set<String> addedAnimalsSet = new HashSet<String>();
    private Map<String, ZooData.VertexInfo> nodeData;
    public int numAnimalsSelected = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);    //TODO rename the xml file

        viewModel = new ViewModelProvider(this)
                .get(ListViewModel.class);

        ListAdapter adapter = new ListAdapter();
        adapter.setHasStableIds(true);
        adapter.setOnTextEditedHandler(viewModel::updateText);
        adapter.setOnDeleteBtnClickedHandler(viewModel::deleteItem);    //exercise 4
        viewModel.getListItems().observe(this, adapter::setListItems);

        recyclerView = findViewById(R.id.list_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        //set item ids
        this.searchText = this.findViewById(R.id.search_text);
        this.searchButton = this.findViewById(R.id.search_btn);

        searchButton.setOnClickListener(this::onSearchClicked);

        //populating the app with new zoo database
        ZooData zooData = new ZooData();
        zooData.populateDatabase(this); //changed context to this
        nodeData = zooData.getVertexDatabase();

        //filters nodeData based on search query
        searchResults = new ArrayList<String>(){};
        for (ZooData.VertexInfo vertex : nodeData.values()){
            String nodeName = vertex.getName();
            searchResults.add(nodeName);
        }

        //preparing objects for search query
        listView = findViewById(R.id.search_view);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(arrayAdapter);

        //-------------Add selected items from ListView to addedAnimals array----------
        ListView lv = (ListView) findViewById(R.id.search_view);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                addedAnimalsSet.add(searchResultsID.get(i));
                addedAnimals.add(searchResultsID.get(i));
                Toast.makeText(getApplicationContext(), "Selected: " + searchResults.get(i), Toast.LENGTH_LONG).show();
                numAnimalsSelected++;

                //changed / added for listview display
                String text = searchResultsID.get(i);
                viewModel.createItem(text);
            }
        });

    }

    //passes addedAnimals array to plan for a plan summary
    public void onPlanBtnClicked(View view) {
        Intent intent = new Intent(this, PlanActivity.class);
        intent.putExtra("addedAnimals", addedAnimalsSet.toArray(new String[0]));
        startActivity(intent);
    }

    //https://www.youtube.com/watch?v=M3UDh9mwBd8
    // How to Add Search View in Toolbar in Android Studio | SearchView on Toolbar | Actionbar
    // 5/6/2022
    // copied format and changed filter requirements to account for tags as well
    void onSearchClicked(View view) {
        arrayAdapter.clear();
        String query = searchText.getQuery().toString();
        searchResults = new ArrayList<String>(){};
        searchResultsID = new ArrayList<String>(){};
        //filters nodeData based on search query
        for (ZooData.VertexInfo vertex : nodeData.values()){
            String nodeName = vertex.getName();
            //takes into account tags also
            if (nodeName.contains(query) || vertex.getTags().contains(query)){
                searchResults.add(nodeName);
                searchResultsID.add(vertex.getId());
            }
        }
        arrayAdapter.addAll(searchResults);
    }

    //displays the total amount of exhibits selected
    public void onCountBtnClick(View view) {
        TextView tview = (TextView) findViewById(R.id.countView);
        tview.setText(String.valueOf(addedAnimalsSet.size()));
    }

    public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

        private List<ListItem> listItems = Collections.emptyList();
        private BiConsumer<ListItem, String> onTextEditedHandler;
        private Consumer<ListItem> onDeleteBtnClicked;
        public Map<String, ZooData.VertexInfo> vInfo;

        public void setListItems(List<ListItem> newListItems) {
            this.listItems.clear();
            this.listItems = newListItems;
            notifyDataSetChanged();
        }

        //TODO delete if not in use
        public void setOnTextEditedHandler(BiConsumer<ListItem, String> onTextEdited) {
            this.onTextEditedHandler = onTextEdited;
        }

        //exercise 4
        //TODO expand on this to delete all items, not only one
        public void setOnDeleteBtnClickedHandler(Consumer<ListItem> onDeleteBtnClicked) {
            this.onDeleteBtnClicked = onDeleteBtnClicked;
        }

        @NonNull
        @Override
        public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);   //changed from todo_list_item to list_item

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {
            holder.setListItem(listItems.get(position));
        }

        @Override
        public int getItemCount() {
            return listItems.size();
        }

        @Override
        public long getItemId(int position) {
            return listItems.get(position).id;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView textView;
            private final TextView deleteBtn;   //exercise 4
            private ListItem listItem;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                this.textView = itemView.findViewById(R.id.todo_item_text);
//                this.checkBox = itemView.findViewById(R.id.completed);
                this.deleteBtn = itemView.findViewById(R.id.delete_btn);    //exercise 4

                this.textView.setOnFocusChangeListener((view, hasFocus) -> {
                    if (!hasFocus) {
                        onTextEditedHandler.accept(listItem, textView.getText().toString());
                    }
                });

                //exercise 4
                this.deleteBtn.setOnClickListener(view -> {
                    if (onDeleteBtnClicked == null) return;
                    onDeleteBtnClicked.accept(listItem);
                });
            }

            public ListItem getListItem() {
                return listItem;
            }

            public void setListItem(ListItem todoItem) {
                this.listItem = todoItem;
                this.textView.setText(todoItem.text);
//                this.checkBox.setChecked(todoItem.completed);
            }
        }
    }
}
