package com.example.webonise.mvpmvvmdemo.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.webonise.mvpmvvmdemo.R;
import com.example.webonise.mvpmvvmdemo.model.UserModel;
import com.example.webonise.mvpmvvmdemo.presenter.UserPresenter;

import java.util.ArrayList;
import java.util.List;


/*
*
*   Activity class to show user data received from server.
*
* */
public class UserListActivity extends AppCompatActivity implements UserView {

    private RecyclerView recyclerViewUser;
    private Context mContext;
    private UserListAdapter userListAdapter;
    private ProgressDialog mProgressDialog;
    private List<UserModel> userModelsList;
    private List<UserModel> userModelsListLoadMore;
    private int lastIndex = 0;
    private final int incrementIndex = 10;
    private final int SECS = 3000;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        UserPresenter userPresenter = new UserPresenter(this);
        userPresenter.readUserData(this);
    }

    /**
     * method to initialise view
     */
    private void initView() {
        mContext = this;
        userModelsListLoadMore = new ArrayList<>();
        recyclerViewUser = (RecyclerView) findViewById(R.id.recyclerViewUser);
        recyclerViewUser.setLayoutManager(new LinearLayoutManager(mContext));
        handler = new Handler();
    }

    @Override
    public void setDataOnList(List<UserModel> userModels) {
        userModelsList = new ArrayList<>();
        userModelsList.addAll(userModels);
        userModelsListLoadMore.addAll(userModels.subList(lastIndex, lastIndex + incrementIndex));
        userListAdapter = new UserListAdapter(mContext, userModelsListLoadMore, recyclerViewUser);
        recyclerViewUser.setAdapter(userListAdapter);
        if (userModels.size() > 0) {
            userListAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    //add null , so the adapter will check view_type and show progress bar at bottom
                    userModelsListLoadMore.add(null);
                    userListAdapter.notifyItemInserted(userModelsListLoadMore.size() - 1);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (userModelsListLoadMore.size() < userModelsList.size()) {
                                //   remove progress item
                                userModelsListLoadMore.remove(userModelsListLoadMore.size() - 1);
                                userListAdapter.notifyItemRemoved(userModelsListLoadMore.size());
                                lastIndex = lastIndex + incrementIndex;
                                userModelsListLoadMore.addAll(userModelsList.subList(lastIndex, lastIndex + incrementIndex));
                                userListAdapter.notifyDataSetChanged();
                                userListAdapter.setLoaded();
                            } else {
                                //   remove progress item
                                userModelsListLoadMore.remove(userModelsListLoadMore.size() - 1);
                                userListAdapter.notifyItemRemoved(userModelsListLoadMore.size());
                                userListAdapter.notifyDataSetChanged();
                                userListAdapter.setLoaded();
                            }

                        }
                    }, SECS);

                }
            });
        }
        dismissProgressDialog();

    }

    @Override
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setMessage(mContext.getString(R.string.fetchData));
            mProgressDialog.setCancelable(false);
            mProgressDialog.setCanceledOnTouchOutside(false);
        }
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    @Override
    public void displayMessage(String message) {
        dismissProgressDialog();
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }

    /**
     * method to dismiss the dialog if is showing
     */
    private void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
