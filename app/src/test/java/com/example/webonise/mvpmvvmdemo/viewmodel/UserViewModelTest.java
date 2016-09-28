package com.example.webonise.mvpmvvmdemo.viewmodel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * test class for ViewModel class.
 */
public class UserViewModelTest {

    private UserViewModel userViewModel;

    @Before
    public void setUp() {
        userViewModel = new UserViewModel();
    }

    @Test
    public void formatEmail() {
        String email1 = "rahetFatehAli@gmail.Com";
        String email2 = email1.toLowerCase();
        Assert.assertEquals(email2, userViewModel.formatEmail(email1));
    }

    @Test
    public void formatName() {
        String name1 = "rahet Fateh Ali Khan";
        String name2 = "Rahet Fateh Ali...";
        Assert.assertEquals(name2, userViewModel.formatName(name1));
    }

    @Test
    public void checkForEmptyString() {
        Assert.assertFalse(userViewModel.checkForEmptyString(""));
        Assert.assertTrue(userViewModel.checkForEmptyString("meenal"));
    }


    @Test
    public void emailValidatorCorrectEmail() {
        Assert.assertTrue(userViewModel.emailValidator("meenal@email.com"));
        Assert.assertTrue(userViewModel.emailValidator("name_surname@gmail.com"));
    }

    @Test
    public void emailValidatorEmailSubDomain() {
        Assert.assertTrue(userViewModel.emailValidator("meenal@gmail.co.uk"));
    }

    @Test
    public void emailValidatorInvalidEmailNoTld() {
        Assert.assertFalse(userViewModel.emailValidator("meenal@gmail"));
    }

    @Test
    public void emailValidatorInvalidEmailDoubleDot() {
        Assert.assertFalse(userViewModel.emailValidator("meenal@gmail..com"));
    }

    @Test
    public void emailValidatorInvalidEmailNoUsername() {
        Assert.assertFalse(userViewModel.emailValidator("@gmail.com"));
    }

    @Test
    public void emailValidatorEmptyString() {
        Assert.assertFalse(userViewModel.emailValidator(""));
    }

    @Test
    public void emailValidatorNullEmail() {
        Assert.assertFalse(userViewModel.emailValidator(null));
    }
}
