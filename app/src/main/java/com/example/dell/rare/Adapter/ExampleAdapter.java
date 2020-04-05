package com.example.dell.rare.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dell.rare.R;
import com.example.dell.rare.classes.ExampleItem;
import com.example.dell.rare.classes.ExampleItemVerticle;

import java.util.ArrayList;

// playlist adapter
public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {

    private Context mContext;
    private ArrayList<ExampleItemVerticle> mExampleList;

    public ExampleAdapter(Context context, ArrayList<ExampleItemVerticle> exampleList){
        mContext = context;
        mExampleList = exampleList;
    }


    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_product_verticle,parent,false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        ExampleItemVerticle currentItem = mExampleList.get(position);
        String completed = currentItem.getCompleted();
        String brand = currentItem.getBrand();
        String model = currentItem.getModel();
        String color = currentItem.getColor();
        String defect = currentItem.getDefect();
         
        holder.completed.setText(completed);
        holder.model.setText(model);
        holder.brand.setText(brand);
        holder.color.setText(color);
        holder.defect.setText(defect);
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }


    public class ExampleViewHolder extends RecyclerView.ViewHolder{


        public TextView completed,model,brand,color,defect;
        public View view;
        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            completed = itemView.findViewById(R.id.textView222);
            model = itemView.findViewById(R.id.textView333);
            brand = itemView.findViewById(R.id.textView444);
            color = itemView.findViewById(R.id.textView555);
            defect = itemView.findViewById(R.id.textView666);
            view = itemView.findViewById(R.id.view);
        }
    }
}
