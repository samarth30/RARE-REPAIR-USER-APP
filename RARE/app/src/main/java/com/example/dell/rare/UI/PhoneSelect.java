package com.example.dell.rare.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dell.rare.Adapter.AutoCompleteCountryAdapter;
import com.example.dell.rare.R;
import com.example.dell.rare.classes.CountryItem;

import java.util.ArrayList;
import java.util.List;

public class PhoneSelect extends AppCompatActivity {

    ArrayList<String> users;
    ArrayList<String> colors;
    ArrayList<String> model;
    Spinner spinner1,spinner2,spinner3;

    AutoCompleteTextView brand_auto,model_auto,color_auto;
    List<CountryItem> countryList;
    List<CountryItem> modelList;
    List<CountryItem> colorList;

    String text_brand,text_model,text_color;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_select);

        /// view on which you want to apply autosuggestion


        brand_auto = findViewById(R.id.brand);
        model_auto = findViewById(R.id.model);
        color_auto = findViewById(R.id.color);

        /// autosuggestion code
        fillBrandList();

        AutoCompleteCountryAdapter brandSuggestion = new AutoCompleteCountryAdapter(this,countryList);

        brand_auto.setAdapter(brandSuggestion);



        brand_auto.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){
//                newsAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        modelList = new ArrayList<>();


        model_auto.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        String brandSelected =  brand_auto.getText().toString();
                        if(brandSelected.equals("mi")){
                            modelList.clear();
                            parsemi();
                            AutoCompleteCountryAdapter modelSuggestion = new AutoCompleteCountryAdapter(PhoneSelect.this,modelList);
                            model_auto.setAdapter(modelSuggestion);
                        }else if(brandSelected.equals("samsung")){
                             modelList.clear();
                             parsesamsung();
                            AutoCompleteCountryAdapter modelSuggestion = new AutoCompleteCountryAdapter(PhoneSelect.this,modelList);
                            model_auto.setAdapter(modelSuggestion);
                        }else if(brandSelected.equals("sony")){
                            modelList.clear();
                            parseSony();
                            AutoCompleteCountryAdapter modelSuggestion = new AutoCompleteCountryAdapter(PhoneSelect.this,modelList);
                            model_auto.setAdapter(modelSuggestion);
                        }else if(brandSelected.equals("oppo")){
                            modelList.clear();
                            parseOppo();
                            AutoCompleteCountryAdapter modelSuggestion = new AutoCompleteCountryAdapter(PhoneSelect.this,modelList);
                            model_auto.setAdapter(modelSuggestion);
                        }else if(brandSelected.equals("realme")){
                            modelList.clear();
                            parseRealme();
                            AutoCompleteCountryAdapter modelSuggestion = new AutoCompleteCountryAdapter(PhoneSelect.this,modelList);
                            model_auto.setAdapter(modelSuggestion);
                        }else if(brandSelected.equals("apple")){
                            modelList.clear();
                            parseApple();
                            AutoCompleteCountryAdapter modelSuggestion = new AutoCompleteCountryAdapter(PhoneSelect.this,modelList);
                            model_auto.setAdapter(modelSuggestion);
                        }else if(brandSelected.equals("nokia")){
                            modelList.clear();
                            parseNokia();
                            AutoCompleteCountryAdapter modelSuggestion = new AutoCompleteCountryAdapter(PhoneSelect.this,modelList);
                            model_auto.setAdapter(modelSuggestion);
                        }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){
//                newsAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        colorList = new ArrayList<>();
        parseColorMain();
        AutoCompleteCountryAdapter colorSuggestion = new AutoCompleteCountryAdapter(this,colorList);

        color_auto.setAdapter(colorSuggestion);


        color_auto.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){
//                newsAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Button next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                text_brand = brand_auto.getText().toString();
                text_color = color_auto.getText().toString();
                text_model = model_auto.getText().toString();

                if(TextUtils.isEmpty(brand_auto.getText().toString())){
                    Toast.makeText(PhoneSelect.this, "please fill the brand name", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(model_auto.getText().toString())){
                    Toast.makeText(PhoneSelect.this, "please fill the model name", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(color_auto.getText().toString())){
                    Toast.makeText(PhoneSelect.this, "Fill the color of you phone", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(PhoneSelect.this, Detailed_page.class);
                    intent.putExtra("text_brand", text_brand);
                    intent.putExtra("text_model", text_model);
                    intent.putExtra("text_color", text_color);
                    startActivity(intent);
                }
            }
        });

    }

//    Intent intent = new Intent(PhoneSelect.this, Detailed_page.class);
//                    intent.putExtra("text_brand", text_brand);
//                    intent.putExtra("text_model", text_model);
//                    intent.putExtra("text_color", text_color);
//    startActivity(intent);
    private void parseColorMain(){
        modelList.add(new CountryItem("red",R.color.colorPrimaryDark));
        modelList.add(new CountryItem("blue",R.color.colorPrimaryDark));
        modelList.add(new CountryItem("green",R.color.colorPrimaryDark));
        modelList.add(new CountryItem("voilet",R.color.colorPrimaryDark));
        modelList.add(new CountryItem("red",R.color.colorPrimaryDark));
        modelList.add(new CountryItem("red",R.color.colorPrimaryDark));
        modelList.add(new CountryItem("red",R.color.colorPrimaryDark));

    }

    private void parseNokia() {
        modelList.add(new CountryItem("nokia X",R.drawable.meal));
        modelList.add(new CountryItem("nokia X",R.drawable.sg));
        modelList.add(new CountryItem("nokia X",R.drawable.flower));
        modelList.add(new CountryItem("nokia X",R.drawable.payment));
        modelList.add(new CountryItem("nokia X",R.drawable.payment));
    }

    private void parseApple() {
        modelList.add(new CountryItem("iphone X",R.drawable.driver));
        modelList.add(new CountryItem("iphone X",R.drawable.meal));
        modelList.add(new CountryItem("iphone X",R.drawable.sg));
        modelList.add(new CountryItem("iphone X",R.drawable.flower));
        modelList.add(new CountryItem("iphone X",R.drawable.payment));
        modelList.add(new CountryItem("iphone X",R.drawable.payment));
    }

    private void parseRealme() {
        modelList.add(new CountryItem("realme one",R.drawable.driver));
        modelList.add(new CountryItem("realme one",R.drawable.meal));
        modelList.add(new CountryItem("realme one",R.drawable.sg));
        modelList.add(new CountryItem("realme one",R.drawable.flower));
        modelList.add(new CountryItem("realme one",R.drawable.payment));
        modelList.add(new CountryItem("realme one",R.drawable.payment));
    }

    private void parseOppo() {
        modelList.add(new CountryItem("oppo f2",R.drawable.payment));
        modelList.add(new CountryItem("oppo f2",R.drawable.payment));
        modelList.add(new CountryItem("oppo f2",R.drawable.payment));
        modelList.add(new CountryItem("oppo f2",R.drawable.sg));
        modelList.add(new CountryItem("oppo f2",R.drawable.meal));
        modelList.add(new CountryItem("oppo f2",R.drawable.payment));
        modelList.add(new CountryItem("oppo f2",R.drawable.flower));
        modelList.add(new CountryItem("oppo f2",R.drawable.meal));
        modelList.add(new CountryItem("oppo f2",R.drawable.payment));
    }

    private void parseSony() {
        modelList.add(new CountryItem("sony experia",R.drawable.meal));
        modelList.add(new CountryItem("sony experia",R.drawable.meal));
        modelList.add(new CountryItem("sony experia",R.drawable.sg));
        modelList.add(new CountryItem("sony experia",R.drawable.flower));
        modelList.add(new CountryItem("sony experia",R.drawable.driver));
        modelList.add(new CountryItem("sony experia",R.drawable.flower));
        modelList.add(new CountryItem("sony experia",R.drawable.driver));
        modelList.add(new CountryItem("sony experia",R.drawable.payment));

    }

    private void parsemi() {

        modelList.add(new CountryItem("redmi note 3",R.drawable.meal));
        modelList.add(new CountryItem("redmi note 3",R.drawable.driver));
        modelList.add(new CountryItem("redmi note 3",R.drawable.meal));
        modelList.add(new CountryItem("redmi note 3",R.drawable.sg));
        modelList.add(new CountryItem("redmi note 3",R.drawable.driver));
        modelList.add(new CountryItem("redmi note 3",R.drawable.driver));
    }

    private void parsesamsung(){
        modelList.add(new CountryItem("samsung j2",R.drawable.driver));
        modelList.add(new CountryItem("samsung j2",R.drawable.meal));
        modelList.add(new CountryItem("samsung j2",R.drawable.meal));
        modelList.add(new CountryItem("samsung j2",R.drawable.payment));
        modelList.add(new CountryItem("samsung j2",R.drawable.sg));
        modelList.add(new CountryItem("samsung j2",R.drawable.driver));
        modelList.add(new CountryItem("samsung j2",R.drawable.flower));

    }
    private void fillBrandList(){
        countryList = new ArrayList<>();
        countryList.add(new CountryItem("samsung",R.drawable.sg));
        countryList.add(new CountryItem("realme",R.drawable.payment));
        countryList.add(new CountryItem("oppo",R.drawable.meal));
        countryList.add(new CountryItem("mi",R.drawable.driver));
        countryList.add(new CountryItem("nokia",R.drawable.flower));
        countryList.add(new CountryItem("apple",R.drawable.sg));
        countryList.add(new CountryItem("sony",R.drawable.driver));
    }




    private void parseModel() {
        model.add("select the model");
     model.add("mi a1");
     model.add("samsung j2");
     model.add("realme 1");
     model.add("one plus");
     model.add("one plus 6t");
     model.add("apple i phone x");
    }

    private void parseColor() {
        colors.add("select the color");
        colors.add("red");
        colors.add("blue");
        colors.add("black");
        colors.add("baby pink");
        colors.add("blue");
    }


    private void parsePhone() {
        users.add("select the brand");
      users.add("samsung");
      users.add("mi");
      users.add("realme");
      users.add("oppo");
      users.add("one plus");
      users.add("apple");
    }

}
//        spinner1 = findViewById(R.id.phone_select);
//        spinner2 = findViewById(R.id.select_model);
//        spinner3 = findViewById(R.id.select_color);
//
//        users = new ArrayList<>();
//        colors = new ArrayList<>();
//        model = new ArrayList<>();
//
//        parsePhone();
//        parseModel();
//        parseColor();
//
//        text_color = colors.get(0);
//        text_model = model.get(0);
//        text_brand = users.get(0);
//
//        ArrayAdapter<String> adapter_phone = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, users);
//        spinner1.setAdapter(adapter_phone);
//
//        ArrayAdapter<String> adapter_model = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, model);
//        adapter_model.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner2.setAdapter(adapter_model);
//
//        ArrayAdapter<String> adapter_color = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, colors);
//        spinner3.setAdapter(adapter_color);
//
//        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
//                text_brand = users.get(position);
//            }
//
//            @Override
//            public void onNothingSelected(android.widget.AdapterView<?> parent) {
//
//            }
//        });
//
//        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
//                text_model = model.get(position);
//            }
//
//            @Override
//            public void onNothingSelected(android.widget.AdapterView<?> parent) {
//
//            }
//        });
//
//        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
//                text_color = colors.get(position);
//            }
//
//            @Override
//            public void onNothingSelected(android.widget.AdapterView<?> parent) {
//
//            }
//        });