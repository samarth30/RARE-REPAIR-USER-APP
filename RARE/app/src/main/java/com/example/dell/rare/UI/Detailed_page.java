package com.example.dell.rare.UI;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.rare.R;

import java.util.ArrayList;

public class Detailed_page extends AppCompatActivity {

    Button mOrder;
    TextView mItemSelected;
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();
    String DefectedParts="enter the correct\npart";
    String brand,model,color;
    String NAME,PHONE,DEFECTS;
    TextInputEditText number;
    TextInputEditText name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_page);

        brand = getIntent().getExtras().getString("text_brand");
        model = getIntent().getExtras().getString("text_model");
        color = getIntent().getExtras().getString("text_color");

        number = findViewById(R.id.titleaddnumber);
        name = findViewById(R.id.titleaddname);



        mOrder = (Button) findViewById(R.id.btnOrder);
        mItemSelected = (TextView) findViewById(R.id.tvItemSelected);

        listItems = getResources().getStringArray(R.array.shopping_item);
        checkedItems = new boolean[listItems.length];



        Button next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!number.getText().toString().isEmpty() && !name.getText().toString().isEmpty() &!DefectedParts.toString().isEmpty()) {
                    Intent intent = new Intent(Detailed_page.this, FinalPage.class);
                    intent.putExtra("brand", brand);
                    intent.putExtra("model", model);
                    intent.putExtra("color", color);
                    intent.putExtra("defects", DefectedParts);
                    intent.putExtra("name", name.getText().toString());
                    intent.putExtra("number", number.getText().toString());
                    startActivity(intent);
                }else{
                    Toast.makeText(Detailed_page.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Detailed_page.this);
                mBuilder.setTitle("Items available in a shop");
                mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
//                        if (isChecked) {
//                            if (!mUserItems.contains(position)) {
//                                mUserItems.add(position);
//                            }
//                        } else if (mUserItems.contains(position)) {
//                            mUserItems.remove(position);
//                        }
                        if(isChecked){
                            mUserItems.add(position);
                        }else{
                            mUserItems.remove((Integer.valueOf(position)));
                        }
                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String item = "";
                        for (int i = 0; i < mUserItems.size(); i++) {
                            item = item + listItems[mUserItems.get(i)];
                            if (i != mUserItems.size() - 1) {
                                item = item + ", ";
                            }
                        }
                        DefectedParts = item;
                        mItemSelected.setVisibility(View.VISIBLE);
                        mItemSelected.setText(DefectedParts);
                    }
                });

                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < checkedItems.length; i++) {
                            checkedItems[i] = false;
                            mUserItems.clear();
                            mItemSelected.setText("");
                        }
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }

        });



    }

}