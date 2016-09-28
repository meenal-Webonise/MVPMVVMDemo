package com.example.webonise.mvpmvvmdemo.presenter;

import android.content.Context;

import com.example.webonise.mvpmvvmdemo.view.UserListActivity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.mock;

/**
 * Test class for Presenter.
 */

public class UserPresenterTest {

    private UserPresenter userPresenter;
    @Mock
    private Context mContext;

    @Before
    public void setUp() {
        UserListActivity userListActivity = mock(UserListActivity.class);
        userPresenter = new UserPresenter(userListActivity);
    }

    @Test
    public void readUserData() {
        userPresenter.readUserData(mContext);
    }
}
