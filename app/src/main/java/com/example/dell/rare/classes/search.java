package com.example.dell.rare.classes;

public class search {
    String suggestion;
    String brand,model,color1,color2,color3,color4,color5;
    String image;

    public search(String suggestion,String brand ,String model, String color1, String color2, String color3, String color4, String color5, String image) {
        this.suggestion = suggestion;
        this.model = model;
        this.color1 = color1;
        this.color2 = color2;
        this.color3 = color3;
        this.color4 = color4;
        this.color5 = color5;
        this.brand = brand;
        this.image = image;
    }


    public search(String model, String image) {
        this.model = model;
        this.image = image;
    }

    public search(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor1() {
        return color1;
    }

    public void setColor1(String color1) {
        this.color1 = color1;
    }

    public String getColor2() {
        return color2;
    }

    public void setColor2(String color2) {
        this.color2 = color2;
    }

    public String getColor3() {
        return color3;
    }

    public void setColor3(String color3) {
        this.color3 = color3;
    }

    public String getColor4() {
        return color4;
    }

    public void setColor4(String color4) {
        this.color4 = color4;
    }

    public String getColor5() {
        return color5;
    }

    public void setColor5(String color5) {
        this.color5 = color5;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
