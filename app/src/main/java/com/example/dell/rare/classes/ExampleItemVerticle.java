package com.example.dell.rare.classes;

public class ExampleItemVerticle {
    String completed,brand,color,model,defect,id;

    public ExampleItemVerticle(String completed, String brand, String color, String model, String defect, String id) {
        this.completed = completed;
        this.brand = brand;
        this.color = color;
        this.model = model;
        this.defect = defect;
        this.id = id;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDefect() {
        return defect;
    }

    public void setDefect(String defect) {
        this.defect = defect;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
