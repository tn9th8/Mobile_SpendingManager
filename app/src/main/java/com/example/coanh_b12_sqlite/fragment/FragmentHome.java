package com.example.coanh_b12_sqlite.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coanh_b12_sqlite.R;
import com.example.coanh_b12_sqlite.UpdateDeleteActivity;
import com.example.coanh_b12_sqlite.adapter.RecycleViewAdapter;
import com.example.coanh_b12_sqlite.dal.SQLiteHelper;
import com.example.coanh_b12_sqlite.model.Item;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FragmentHome extends Fragment implements RecycleViewAdapter.ItemListener {
    private RecycleViewAdapter adapter;
    private RecyclerView rvRecyclerView;
    private SQLiteHelper db;
    private TextView tvTongTien;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvRecyclerView = view.findViewById(R.id.rvRecycleView);
        tvTongTien = view.findViewById(R.id.tvTongTien);
        adapter = new RecycleViewAdapter();
        db = new SQLiteHelper(getContext());

        Date date = new Date();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
        List<Item> list = db.getByDate(dateformat.format(date));
        adapter.setList(list);
        tvTongTien.setText("Tong Tien: " + tong(list));

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rvRecyclerView.setLayoutManager(manager);
        rvRecyclerView.setAdapter(adapter);
        adapter.setItemListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        Item item = adapter.getItem(position);
        Intent intent = new Intent(getActivity(), UpdateDeleteActivity.class);
        intent.putExtra("item", item);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        Date date = new Date();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");

        List<Item> list = db.getByDate(dateformat.format(date));
        adapter.setList(list);
        //rvRecyclerView.setAdapter(adapter);
        tvTongTien.setText("Tong Tien: " + tong(list));
    }

    private int tong(List<Item> list) {
        int tong = 0;

        for (Item i: list) {
            tong += Integer.parseInt(i.getPrice());
        }
        return tong;
    }
}
