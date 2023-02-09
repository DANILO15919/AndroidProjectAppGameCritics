package com.example.it322020.Models;

public class DetailAPIResponse {

    String name="";
    String metacritic="";
    String background_image="";
    String description_raw="";
    String ratings_count="";

    public String getRatings_count() {
        return ratings_count;
    }

    public void setRatings_count(String ratings_count) {
        this.ratings_count = ratings_count;
    }


    public String getDescription_raw() {
        return description_raw;
    }

    public void setDescription_raw(String description_raw) {
        this.description_raw = description_raw;
    }


    public String getMetacritic() {
        return metacritic;
    }

    public void setMetacritic(String metacritic) {
        this.metacritic = metacritic;
    }

    public String getBackground_image() {
        return background_image;
    }

    public void setBackground_image(String background_image) {
        this.background_image = background_image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
