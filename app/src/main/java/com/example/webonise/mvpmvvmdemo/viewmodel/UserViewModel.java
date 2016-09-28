package com.example.webonise.mvpmvvmdemo.viewmodel;

import com.example.webonise.mvpmvvmdemo.model.UserModel;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * User View Model class.
 */
public class UserViewModel {

    public List<UserModel> userModelsFormatted;
    private final String KEY_NAME = "name", KEY_EMAIL = "email", KEY_BODY = "body";

    /**
     *  pattern define to validate email address
     */
    private final Pattern EMAIL_ADDRESS
            = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    /**
     * method to parse user data
     * @param userData user data string(json) as passed from presenter class
     * @return return type is a list of user model
     */
    public List<UserModel> parseUserData(String userData) {
        List<UserModel> userModels = null;
        try {
            userModels = new ArrayList<>();
            userModelsFormatted = new ArrayList<>();
            if (userData != null && userData.length() > 0) {
                //JSONArray jsonArray = new JSONArray(userData);
                Type userDataListType = new TypeToken<ArrayList<UserModel>>() {
                }.getType();
                GsonBuilder gsonBuilder = new GsonBuilder();
                userModels = gsonBuilder.create().fromJson(userData, userDataListType);

                if (userModels != null && userModels.size() > 0) {

                    for (UserModel userModel : userModels) {
                        userModelsFormatted.add(formatData(userModel));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userModels;
    }

    /**
     * method to format model data
     * @param userModel User Model class object to get name and email id to apply formatting
     * @return return formatted object of view model
     */
    private UserModel formatData(UserModel userModel) {

        userModel.setName(formatName(userModel.getName()));
        userModel.setEmail(formatEmail(userModel.getEmail()));

        return userModel;
    }

    /**
     * method to format list of  model data
     * @param userModels list of type User Model class  to get name and email id to apply formatting
     * @return return formatted list of type user model
     */
    public List<UserModel> formatListData(List<UserModel> userModels) {
        for (int i = 0; i < userModels.size(); i++) {
            UserModel userModel = userModels.get(i);
            userModel.setName(formatName(userModel.getName()));
            String email= formatEmail(userModel.getEmail());
            email=emailValidator(email)?email:"";
            userModel.setEmail(email);
            userModels.set(i, userModel);
        }
        return userModels;
    }

    /**
     *  method to apply constraint to name to restrict it to 3 words
     * @param name name to be validated
     * @return return formatted name
     */
    public String formatName(String name) {

        if (checkForEmptyString(name)) {
            String splitName[] = name.split(" ");

            if (splitName.length > 2) {
                name = ((splitName[0].substring(0, 1).toUpperCase() + splitName[0].substring(1) + " " + splitName[1] + " " + splitName[2]) + "...");
            }
            return name;
        } else {
            return "";
        }
    }

    /**
     * method to convert  email address to lower case
     * @param email email to be changed into lowercase
     * @return return email in lower case
     */
    public String formatEmail(String email) {
            return email.toLowerCase();
    }

    /**
     *  method to check email id if valid or not
     * @param email parameter to be validated
     * @return return true or false
     */
    public boolean emailValidator(String email) {

        return checkForEmptyString(email) && email != null && EMAIL_ADDRESS.matcher(email).matches();

    }

    /**
     *  method to check if any string is empty or contains null
     * @param name string to be checked
     * @return return true or false
     */
    public boolean checkForEmptyString(String name) {

        return !(name == null || name.isEmpty() || name.matches(""));

    }


}
