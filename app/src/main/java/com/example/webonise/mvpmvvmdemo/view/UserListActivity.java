package com.example.webonise.mvpmvvmdemo.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.webonise.mvpmvvmdemo.R;
import com.example.webonise.mvpmvvmdemo.model.UserModel;
import com.example.webonise.mvpmvvmdemo.presenter.UserPresenter;

import java.util.ArrayList;
import java.util.List;


/*
*
*   Activity class to show user data recieved from server.
*
* */
public class UserListActivity extends AppCompatActivity implements UserView {

    private ListView listViewUser;
    private Context mContext;
    private UserListAdapter userListAdapter;
    private UserPresenter userPresenter;
    private ProgressDialog mProgressDialog;
    private View loadMoreView;
    private List<UserModel> userModelsList;
    private List<UserModel> userModelsListLoadMore;
    private int lastIndex = 0;
    private int incrementIndex = 0;
    private Button loadMoreButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            initView();
            userPresenter = new UserPresenter(mContext, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /* method to initiliase view*/
    private void initView() {
        try {
            mContext = this;
            userModelsListLoadMore = new ArrayList<>();
            listViewUser = (ListView) findViewById(R.id.listViewUser);
            loadMoreView = ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_layout, null, false);
            listViewUser.addFooterView(loadMoreView);
            loadMoreButton = (Button) loadMoreView.findViewById(R.id.buttonLoadMore);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setDataOnList(List<UserModel> userModels) {
        try {
            if (Utility.getInstance().isOnline(mContext)) {
                incrementIndex = 10;
            } else {
                incrementIndex = 2;
            }
            userModelsList = new ArrayList<>();
            userModelsList.addAll(userModels);
            userModelsListLoadMore.addAll(userModels.subList(lastIndex, lastIndex + incrementIndex));
            userListAdapter = new UserListAdapter(mContext, userModelsListLoadMore);
            listViewUser.setAdapter(userListAdapter);


            if (userModels.size() > 0) {
                loadMoreView.setVisibility(View.VISIBLE);
                loadMoreButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Load more data for reyclerview
                        showProgressDialog();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                //Load data
                                if (userModelsListLoadMore.size() < userModelsList.size()) {
                                    lastIndex = lastIndex + incrementIndex;
                                    userModelsListLoadMore.addAll(userModelsList.subList(lastIndex, lastIndex + incrementIndex));
                                }
                                userListAdapter.notifyDataSetChanged();
                                dismissProgressDailog();
                            }
                        }, 2000);
                    }
                });
            } else {
                loadMoreView.setVisibility(View.GONE);
            }

            dismissProgressDailog();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void showProgressDialog() {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = new ProgressDialog(mContext);
                mProgressDialog.setMessage(mContext.getString(R.string.fetchData));
                mProgressDialog.setCancelable(false);
                mProgressDialog.setCanceledOnTouchOutside(false);
            }

            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void displayMessage(String message) {
        try {
            dismissProgressDailog();
            Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  method to dismiss the dialog if is showing
     *
     */
     private void dismissProgressDailog() {
        try {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
