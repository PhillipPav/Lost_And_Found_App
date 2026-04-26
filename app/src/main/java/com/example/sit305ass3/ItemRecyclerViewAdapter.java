package com.example.sit305ass3;

import android.content.Context;
import android.media.metrics.Event;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sit305ass3.db.ItemModel;

import java.util.ArrayList;
import java.util.List;

public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.MyViewHolder>
{
    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    List<ItemModel> itemModels;
    public ItemRecyclerViewAdapter(Context context, List<ItemModel> itemModels,
                                   RecyclerViewInterface recyclerViewInterface)
    {
        this.context = context;
        this.itemModels = itemModels;
        this.recyclerViewInterface = recyclerViewInterface;
    }

public void setItemList(List<ItemModel> itemModels)
    {
        this.itemModels = itemModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);

        return new ItemRecyclerViewAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ItemRecyclerViewAdapter.MyViewHolder holder, int position)
    {
        holder.textViewPostType.setText(itemModels.get(position).getPostType());
        holder.textViewTitle.setText(itemModels.get(position).getTitle());
    }

    @Override
    public int getItemCount()
    {
        return itemModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView textViewPostType, textViewTitle;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface)
        {
            super(itemView);

            // TEXT VIEWS
            textViewPostType = itemView.findViewById(R.id.postType);
            textViewTitle = itemView.findViewById(R.id.title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    if (recyclerViewInterface != null)
                    {
                        int position = getAbsoluteAdapterPosition();

                        if (position != RecyclerView.NO_POSITION)
                        {
                            recyclerViewInterface.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
