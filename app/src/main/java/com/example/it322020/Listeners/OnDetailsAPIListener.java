package com.example.it322020.Listeners;

import com.example.it322020.Models.DetailAPIResponse;

public interface OnDetailsAPIListener {
    void onResponse(DetailAPIResponse response);
    void onError(String message);
}
