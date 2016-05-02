package edu.csula.datascience.acquisition.csv;

import edu.csula.datascience.acquisition.twitter.MockTwitterData;
import edu.csula.datascience.acquisition.twitter.TwitterModel;

/**
 * A simple model for testing
 */
public class MovieModel {
	private int id, year;
    private String title, hashtagTitle;
    private double rating;

    public MovieModel(){
    }

    public MovieModel(int movieID, String movieTitle){
        id = movieID;
        title = movieTitle;
        if (title != null) {
            hashtagTitle = "#" + title.replaceAll("\\s", "");
        }
    }

    public MovieModel(int movieID, String movieTitle, double rating, int year){
        id = movieID;
        title = movieTitle;
        hashtagTitle = "#" + title.replaceAll("\\s","");
        this.rating = rating;
        this.year = year;
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
        hashtagTitle = "#" + title.replaceAll("\\s","");
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
	
	public static MovieModel build(MockCsvData data) {
		return new MovieModel(data.getId(), data.getTitle());
	}
	
}
