package com.example.dell.rare.orders.database;

public class OrderContract {
    public OrderContract() {
    }

    public static class OrderEntry {
        public static final String TABLE_NAME = "order_details";

        public static final String BRAND = "phoneBrand";
        public static final String MODEL = "phoneModel";
        public static final String COLOR = "phoneColor";
        public static final String DEFECTS = "phoneDefects";
        public static final String COMPLETED = "completed";
        public static final String ID = "_id";
    }
}
