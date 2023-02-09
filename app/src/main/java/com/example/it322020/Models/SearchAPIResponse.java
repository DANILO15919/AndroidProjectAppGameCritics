package com.example.it322020.Models;

import java.util.List;

public class SearchAPIResponse {

    List<SearchArrayObject> results = null;

    public List<SearchArrayObject> getGames() {
        return results;
    }

    public void setGames(List<SearchArrayObject> games) {
        this.results = games;
    }


}
