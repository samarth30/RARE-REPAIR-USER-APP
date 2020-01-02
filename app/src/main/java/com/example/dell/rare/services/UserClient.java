package com.example.dell.rare.services;

import com.example.dell.rare.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserClient {
    @POST("")
    Call<User> createAccount(@Body User user);
}
