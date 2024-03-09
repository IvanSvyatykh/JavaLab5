package org.example.Services;


public class PathUtilitie {
    public static String createNewUrl(String currentURL, String redirectPath) {
        int lastIndex = currentURL.lastIndexOf("/");
        return currentURL.substring(0, lastIndex) + redirectPath;
    }
}