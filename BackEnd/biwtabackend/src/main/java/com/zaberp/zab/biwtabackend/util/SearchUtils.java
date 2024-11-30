package com.zaberp.zab.biwtabackend.util;

import java.util.ArrayList;

public class SearchUtils {
    public static ArrayList<Object> validateSearchText(String searchText) {
        if (searchText == null || searchText.trim().length() < 3) {
            return new ArrayList<>();
        }
        return null;
    }
}