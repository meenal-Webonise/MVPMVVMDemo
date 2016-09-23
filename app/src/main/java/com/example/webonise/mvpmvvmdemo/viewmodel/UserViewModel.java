package com.example.webonise.mvpmvvmdemo.viewmodel;

import com.example.webonise.mvpmvvmdemo.model.UserModel;

import java.util.List;

/**
 * Created by webonise on 22/9/16.
 */
public class UserViewModel {


    public UserViewModel() {
    }

    /*  method to format model data  */
    public UserModel formatData(UserModel userModel) {

        userModel.setName(formateName(userModel.getName()));


        userModel.setEmail(formateEmail(userModel.getEmail()));

        return userModel;
    }

    /*  method to format list of  model data */
    public List<UserModel> formatListData(List<UserModel> userModels) {


        for (int i = 0; i < userModels.size(); i++) {
            UserModel userModel = userModels.get(i);
            userModel.setName(formateName(userModel.getName()));


            userModel.setEmail(formateEmail(userModel.getEmail()));
            userModels.set(i, userModel);
        }


        return userModels;
    }

    /*  method to apply constraint to name for 3 words*/
    public String formateName(String name) {
        String splitName[] = name.split(" ");

        if (splitName.length > 2) {
            name = ((splitName[0].substring(0, 1).toUpperCase() + splitName[0].substring(1) + " " + splitName[1] + " " + splitName[2]) + "...");
        }
        return name;
    }

    /* method to change email address to lower case*/
    public String formateEmail(String email) {

        return email.toLowerCase();
    }


}
