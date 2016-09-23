package com.example.webonise.mvpmvvmdemo.presenter;

import android.content.Context;
import android.util.Log;

import com.example.webonise.mvpmvvmdemo.R;
import com.example.webonise.mvpmvvmdemo.model.UserModel;
import com.example.webonise.mvpmvvmdemo.view.UserApi;
import com.example.webonise.mvpmvvmdemo.view.UserView;
import com.example.webonise.mvpmvvmdemo.view.Utility;
import com.example.webonise.mvpmvvmdemo.viewmodel.UserViewModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by webonise on 22/9/16.
 */
public class UserPresenter {
    //Root URL of our web service
    public static final String ROOT_URL = "https://jsonplaceholder.typicode.com/";

    private Context mContext;

    private List<UserModel> userModels;
    private UserViewModel userViewModel;
    private UserView mView;

    /*  constructor to initliase object*/
    public UserPresenter(Context mContext, UserView mView) {
        try {
            this.mContext = mContext;
            userViewModel = new UserViewModel();
            this.mView = mView;
            readUserData();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * method to read user data either via server or using file from asset
     */
    private void readUserData() {

        try {
            if (Utility.getInstance().isOnline(mContext)) {

                mView.showProgressDialog();
                //Creating a rest adapter
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(ROOT_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                UserApi request = retrofit.create(UserApi.class);
                Call<List<UserModel>> call = request.getUser();


                call.enqueue(new Callback<List<UserModel>>() {
                    @Override
                    public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                        try {
                            Log.i("TAG", "Response " + response + " list " + call);
                            if (userModels == null) {
                                userModels = new ArrayList<>();
                            }

                            userModels.addAll(response.body());
                            mView.setDataOnList(userViewModel.formatListData(userModels));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<UserModel>> call, Throwable t) {
                        mView.displayMessage(mContext.getString(R.string.no_network));
                    }
                });
            } else {
                String userData = loadData("user_data.txt");
                parseUserData(userData);
                mView.displayMessage(mContext.getString(R.string.no_network));
            }

        } catch (Exception e) {
            e.printStackTrace();
            mView.displayMessage(mContext.getString(R.string.no_network));
        }


    }

    /*
    *  method to parse user data
    * */
    public void parseUserData(String userData) {

        try {
            if (userModels == null) {
                userModels = new ArrayList<>();
            }

            List<UserModel> userModelsFormatted = new ArrayList<>();
            if (userData != null && userData.length() > 0) {
                JSONArray jsonArray = new JSONArray(userData);

                if (jsonArray != null && jsonArray.length() > 0) {

                    for (int i = 0; i < jsonArray.length(); i++) {
                        UserModel userModel = new UserModel();
                        userModel.setName(jsonArray.optJSONObject(i).optString("name"));
                        userModel.setBody(jsonArray.optJSONObject(i).optString("body"));
                        userModel.setEmail(jsonArray.optJSONObject(i).optString("email"));
                        userModels.add(userModel);
                        userModelsFormatted.add(userViewModel.formatData(userModel));

                    }
                    mView.setDataOnList(userModels);


                } else {
                    mView.displayMessage(mContext.getString(R.string.error_msg));
                }
            } else {
                mView.displayMessage(mContext.getString(R.string.error_msg));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /*
    *  read model data from file saved in assets if offline
    * */
    public String loadData(String inFile) {
        String tContents = "";

        try {
            InputStream stream = mContext.getAssets().open(inFile);

            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            tContents = new String(buffer);
        } catch (IOException e) {
            // Handle exceptions here
        }

        return tContents;

    }


    public List<UserModel> getUserModels() {

        if (userModels == null) {
            userModels = new ArrayList<>();
        }
        return userModels;
    }

}
