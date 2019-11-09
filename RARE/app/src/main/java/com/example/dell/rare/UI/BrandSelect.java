package com.example.dell.rare.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.dell.rare.Adapter.BrandAdapter;
import com.example.dell.rare.R;
import com.example.dell.rare.classes.CountryItem;
import com.example.dell.rare.classes.ExampleItem;

import java.util.ArrayList;

public class BrandSelect extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ExampleItem> list;
    BrandAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_select);
        list = new ArrayList<>();
        parseData();
        recyclerView = findViewById(R.id.recyclerViewBrand);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        adapter = new BrandAdapter(this,list);
        recyclerView.setAdapter(adapter);
    }

    private void parseData() {
        list.add(new ExampleItem("samsung",R.drawable.meal));
        list.add(new ExampleItem("Realme",R.drawable.flower));
        list.add(new ExampleItem("Nokia",R.drawable.driver));
        list.add(new ExampleItem("Sony",R.drawable.meal));
        list.add(new ExampleItem("Geonie",R.drawable.driver));
        list.add(new ExampleItem("MI",R.drawable.flower));
        list.add(new ExampleItem("One Plus",R.drawable.meal));
        list.add(new ExampleItem("VIVO",R.drawable.bookfive));
        list.add(new ExampleItem("samsung",R.drawable.meal));
        list.add(new ExampleItem("Realme",R.drawable.flower));
        list.add(new ExampleItem("Nokia",R.drawable.driver));
        list.add(new ExampleItem("Sony",R.drawable.meal));
        list.add(new ExampleItem("Geonie",R.drawable.driver));
        list.add(new ExampleItem("MI",R.drawable.flower));
        list.add(new ExampleItem("One Plus",R.drawable.meal));
        list.add(new ExampleItem("VIVO",R.drawable.bookfive));
    }


}
