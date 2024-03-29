package com.example.coanh_b12_sqlite.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coanh_b12_sqlite.R;
import com.example.coanh_b12_sqlite.model.Item;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.HomeViewHolder>  {
    private List<Item> list;
    private ItemListener itemListener;

    public RecycleViewAdapter() {
        list = new ArrayList<>();
    }

    public void setList(List<Item> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public Item getItem(int position) {
        return list.get(position);
    }


    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        Item item = list.get(position);
        holder.tvTitle.setText(item.getTitle());
        holder.tvCategory.setText(item.getCategory());
        holder.tvPrice.setText(item.getPrice());
        holder.tvDate.setText(item.getDate());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvTitle, tvCategory, tvPrice, tvDate;

        public HomeViewHolder(@NonNull View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tvTitle);
            tvCategory = view.findViewById(R.id.tvCategory);
            tvPrice = view.findViewById(R.id.tvPrice);
            tvDate = view.findViewById(R.id.tvDate);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemListener != null) {
                itemListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public interface ItemListener {
        void onItemClick(View view, int position);
    }

}
