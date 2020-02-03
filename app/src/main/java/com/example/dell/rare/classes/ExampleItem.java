package com.example.dell.rare.classes;

import android.widget.ImageView;

public class ExampleItem {
    String title;
    String imageView;

    public ExampleItem(String title, String imageView) {
        this.title = title;
        this.imageView = imageView;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageView() {
        return imageView;
    }

    public void setImageView(String imageView) {
        this.imageView = imageView;
    }
}
