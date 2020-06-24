package com.example.todolist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

//displays data from model into a row in rview
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder>{

    public interface OnLongClickListener {
        void onItemLongClicked(int position);
    }

    List<String> items;
    OnLongClickListener longClickListener;


    public ItemsAdapter(List<String> items, OnLongClickListener longClickListener) {
        this.items = items;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //use layout inflater
        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent,false);
        return new ViewHolder(todoView);
    }
    //bind data to view holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // grab item at position and bind item to specified view holder
        String item;
        item = items.get(position);
        holder.bind(item);
    }
    //how many
    @Override
    public int getItemCount() {
        return items.size();
    }

    //container to provide easy access to the views making up the list
    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvitem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvitem = itemView.findViewById(android.R.id.text1);
        }
        // update the view inside the view holder
        public void bind(String item){
            tvitem.setText(item);
            tvitem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    longClickListener.onItemLongClicked(getAdapterPosition());
                    return true;
                }
            });
        }

    }
}
