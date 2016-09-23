package com.example.webonise.mvpmvvmdemo.viewmodel;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by webonise on 22/9/16.
 */
public class UserViewModelTest {

    @Test
    public void formateEmail() {
        String email1 = "rahetFatehAli@gmail.Com";
        String email2 = email1.toLowerCase();

        UserViewModel userViewModel = new UserViewModel();
        Assert.assertEquals(email2, userViewModel.formateEmail(email1));


    }

    @Test
    public void formateName() {
        String name1 = "rahet Fateh Ali Khan";
        String name2 = "Rahet Fateh Ali...";

        UserViewModel userViewModel = new UserViewModel();
        Assert.assertEquals(name2, userViewModel.formateName(name1));


    }
}
