package org.example.Services;

import org.example.Model.UserData;

import java.util.HashMap;
import java.util.Map;

public class AccountService {
    private static final Map<String, UserData> loginToProfile = new HashMap<String, UserData>() {{
        put("ivan", new UserData("ivan", "123", "Zlativansvyat@mail.ru"));
    }};

    public static void addNewUser(UserData userProfile) {
        loginToProfile.put(userProfile.getLogin(), userProfile);
    }

    public static UserData getUserByLogin(String login) {
        return loginToProfile.get(login);
    }
}