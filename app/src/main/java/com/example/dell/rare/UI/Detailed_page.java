package com.example.dell.rare.UI;

import android.content.DialogInterface;
import android.content.Intent;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.rare.R;
import com.santalu.maskedittext.MaskEditText;

import java.util.ArrayList;

public class Detailed_page extends AppCompatActivity {

    Button mOrder;
    TextView mItemSelected;
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();
    String DefectedParts="enter\nthe defects";
    String brand,model,color;
    String NAME,PHONE,DEFECTS;
    MaskEditText number;
    TextInputEditText name;
    TextInputLayout number_layout;
    TextInputLayout name_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_page);

        brand = getIntent().getExtras().getString("text_brand");
        model = getIntent().getExtras().getString("text_model");
        color = getIntent().getExtras().getString("text_color");

//        number = findViewById(R.id.titleaddnumber);
//        name = findViewById(R.id.titleaddname);
        number_layout = findViewById(R.id.text_input_number);
        name_layout = findViewById(R.id.text_input_name);


        mOrder = (Button) findViewById(R.id.btnOrder);
        mItemSelected = (TextView) findViewById(R.id.tvItemSelected);

        listItems = getResources().getStringArray(R.array.shopping_item);
        checkedItems = new boolean[listItems.length];



        Button next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!DefectedParts.equals("enter\nthe defects")) {
                    SharedPrefManager sharedPrefManager = new SharedPrefManager(Detailed_page.this);
                    String number = sharedPrefManager.loadEmail();

                        Intent intent = new Intent(Detailed_page.this, FinalPage.class);
                        intent.putExtra("brand", brand);
                        intent.putExtra("model", model);
                        intent.putExtra("color", color);
                        intent.putExtra("defects", DefectedParts);
                        intent.putExtra("name", "asdfg");
                        intent.putExtra("number",  number);
                        startActivity(intent);

                }else {
                    if (DefectedParts.equals("enter\nthe defects")) {
                        Toast.makeText(Detailed_page.this, "Please click on the defected parts button/n to choose defected parts", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Detailed_page.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                    }
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

    private boolean isValidPhoneNumber(CharSequence phoneNumber) {
        if (!TextUtils.isEmpty(phoneNumber)) {
            return Patterns.PHONE.matcher(phoneNumber).matches();
        }
        return false;
    }

    private Boolean validateNumber(){
        String number = number_layout.getEditText().getText().toString().trim();

        if(number.isEmpty()){
            number_layout.setError("Field can't be empty");
            return false;
        }else if(number.length() < 10){
            number_layout.setError("Phone number not valid");
            return false;
        }else if(number.length() >=11){
            number_layout.setError("Phone number not valid");
            return false;
        }
        else{
            return true;
        }
    }

    private Boolean validateName(){
        String name = name_layout.getEditText().getText().toString().trim();

        if(name.isEmpty()){
            name_layout.setError("Field can't be empty");
            return false;
        }else{
            return true;
        }
    }

}