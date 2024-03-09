package org.example.Services;

import org.example.Model.UserData;

import java.util.HashMap;
import java.util.Map;

public class AccountService {
    private static Map<String, UserData> loginToProfile = new HashMap<>();
    private static Map<String, UserData> sessionIdToProfile = new HashMap<>();

    public static void addNewUser(UserData userProfile) {
        loginToProfile.put(userProfile.getLogin(), userProfile);
    }

    public static UserData getUserByLogin(String login) {
        return loginToProfile.get(login);
    }

    public static UserData getUserBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

    public static void addSession(String sessionId, UserData userProfile) {
        sessionIdToProfile.put(sessionId, userProfile);
    }

    public static void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
    }
}