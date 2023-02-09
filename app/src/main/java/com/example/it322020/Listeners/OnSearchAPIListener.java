package com.example.it322020.Listeners;

import com.example.it322020.Models.SearchAPIResponse;

public interface OnSearchAPIListener {
    void onResponse(SearchAPIResponse response);
    void onError(String message);
}
