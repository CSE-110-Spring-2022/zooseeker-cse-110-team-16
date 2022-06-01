package com.example.cse110lab5;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    private final TextView textView;
    private ListItem listItem;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        this.textView = itemView.findViewById(R.id.item_text);
    }

    public ListItem getListItem() {
        return listItem;
    }

    public void setListItem(ListItem listItem) {
        this.listItem = listItem;
        this.textView.setText(listItem.text);
    }

}
