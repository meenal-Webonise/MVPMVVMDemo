package com.example.webonise.mvpmvvmdemo.view;

import com.example.webonise.mvpmvvmdemo.model.UserModel;

import java.util.List;

/**
 * Created by webonise on 22/9/16.
 */
public interface UserView {
    void setDataOnList(List<UserModel> userModels);

    void showProgressDialog();

    void displayMessage(String string);
}
