package com.example.webonise.mvpmvvmdemo.view;

import com.example.webonise.mvpmvvmdemo.model.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * User Api to defing method to get user detail list.
 */
public interface UserApi {

    /*Retrofit get annotation with our URL
       And our method that will return us the list of users
    */
    @GET("/comments")
    public Call<List<UserModel>> getUser();
}
