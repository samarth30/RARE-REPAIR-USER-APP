package com.example.dell.rare.classes;

import android.widget.ImageView;

public class ExampleItem {
    String title;
    int imageView;

    public ExampleItem(String title, int imageView) {
        this.title = title;
        this.imageView = imageView;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageView() {
        return imageView;
    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }
}
