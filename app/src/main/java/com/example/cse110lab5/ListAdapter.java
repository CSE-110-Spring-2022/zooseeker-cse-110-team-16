package com.example.cse110lab5;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

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
//
//    //TODO delete if not in use
//    public void setOnTextEditedHandler(BiConsumer<ListItem, String> onTextEdited) {
//        this.onTextEditedHandler = onTextEdited;
//    }
//
//    //exercise 4
//    //TODO expand on this to delete all items, not only one
//    public void setOnDeleteBtnClickedHandler(Consumer<ListItem> onDeleteBtnClicked) {
//        this.onDeleteBtnClicked = onDeleteBtnClicked;
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);   //changed from todo_list_item to list_item

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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
//        private final TextView deleteBtn;   //exercise 4
        private ListItem listItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.item_text);
//                this.checkBox = itemView.findViewById(R.id.completed);
//            this.deleteBtn = itemView.findViewById(R.id.delete_btn);    //exercise 4
//
//            this.textView.setOnFocusChangeListener((view, hasFocus) -> {
//                if (!hasFocus) {
//                    onTextEditedHandler.accept(listItem, textView.getText().toString());
//                }
//            });
//
//            //exercise 4
//            this.deleteBtn.setOnClickListener(view -> {
//                if (onDeleteBtnClicked == null) return;
//                onDeleteBtnClicked.accept(listItem);
//            });
        }

        public ListItem getListItem() {
            return listItem;
        }

        public void setListItem(ListItem listItem) {
            this.listItem = listItem;
            this.textView.setText(listItem.text);
//                this.checkBox.setChecked(todoItem.completed);
        }
    }
}
