package com.gorest.curdTest;

import com.gorest.studentinfo.UserSteps;
import com.gorest.testBase.TestBase;
import com.gorest.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class UserCURDTest extends TestBase {
    static String name = "Jay" + TestUtils.getRandomValue();
    static String email = TestUtils.getRandomString() + TestUtils.getRandomValue() + "@gmail.com";
    static String gender = "male";
    static String status = "active";
    static int userId;
    @Steps
    UserSteps userSteps;

    @Title("This will create new user")
    @Test
    public void test001() {
        ValidatableResponse response = userSteps.createUser(name, email, gender, status);
        response.log().all().statusCode(201);
    }
    @Title("Verify if the user was added to the application")
    @Test
    public void test002() {
        HashMap<String, Object> userMap = userSteps.getUserInfoByName(name);
        Assert.assertThat(userMap, hasValue(name));
        userId = (int) userMap.get("id");
    }

    @Title("Update the user information and verify the updated information")
    @Test()
    public void test003() {
        name = name + TestUtils.getRandomValue();
        userSteps.updateStudent(userId, name, email, gender, status);
        HashMap<String, Object> userMap = userSteps.getUserInfoByName(name);
        Assert.assertThat(userMap, hasValue(name));
    }

    @Title("Delete the user and verify if the user is deleted")
    @Test
    public void test004() {
        userSteps.deleteUser(userId);
        userSteps.getUserById(userId);
    }

}