package com.example.webonise.mvpmvvmdemo.presenter;

import android.content.Context;

import com.example.webonise.mvpmvvmdemo.R;
import com.example.webonise.mvpmvvmdemo.model.UserModel;
import com.example.webonise.mvpmvvmdemo.view.UserApi;
import com.example.webonise.mvpmvvmdemo.view.UserView;
import com.example.webonise.mvpmvvmdemo.view.Utility;
import com.example.webonise.mvpmvvmdemo.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * User Presenter class to read user Data and set it on List
 */
public class UserPresenter {
    //Root URL of our web service
    private static final String ROOT_URL = "https://jsonplaceholder.typicode.com/";
    private List<UserModel> userModels;
    private UserViewModel userViewModel;
    private UserView mView;
    private final String FILE_NAME = "user_data.txt";

    /*  constructor to initialise object*/
    public UserPresenter(UserView mView) {
        try {
            this.userViewModel = new UserViewModel();
            this.mView = mView;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * method to read user data either via server or using file from asset
     * @param mContext context of calling activity
     */
    public void readUserData(final Context mContext) {
        try {

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
                    String userData = Utility.getInstance(mContext).loadData(FILE_NAME);
                    parseUserData(userData, mContext);
                    mView.displayMessage(mContext.getString(R.string.no_network));
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
            mView.displayMessage(mContext.getString(R.string.no_network));
        }
    }

    /**
     * method to parse user data
     * @param userData string of data(json) as loaded from asset
     * @param mContext context of calling activity
     */
    private void parseUserData(String userData, Context mContext) {
        try {
            if (userModels == null) {
                userModels = new ArrayList<>();
            }
            if (userData != null && !userData.isEmpty()) {
                userModels.addAll(userViewModel.parseUserData(userData));
                if (userModels != null && userModels.size() > 0) {
                    mView.setDataOnList(userViewModel.userModelsFormatted);
                } else {
                    mView.displayMessage(mContext.getString(R.string.error_msg));
                }
            } else {
                mView.displayMessage(mContext.getString(R.string.error_msg));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
