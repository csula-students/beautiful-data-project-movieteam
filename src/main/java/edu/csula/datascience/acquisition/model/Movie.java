package edu.csula.datascience.acquisition.model;

public class Movie {
    private int id, year;
    private String title, hashtagTitle;
    private double rating;

    public Movie(){
    }

    public Movie(int movieID, String movieTitle){
        id = movieID;
        title = movieTitle;
        if (movieTitle != null) {
            hashtagTitle = title.replaceAll("\\s", "");
        }
        year = 0;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        if (title != null)
            hashtagTitle = title.replaceAll("\\s","");
    }

    public String getHashtagTitle() {
        return hashtagTitle;
    }

    public void setHashtagTitle(String hashtagTitle) {
        this.hashtagTitle = hashtagTitle;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getYear(){
        return year;
    }

    public void setYear(int year){
        this.year = year;
    }


}
