package edu.csula.datascience.acquisition.csv;

/**
 * Mock raw twitter data
 */
public class MockCsvData {
    private int id, year;
    private String title;
    private double rating;

	public MockCsvData(int id, String title, double rating) {
		super();
		this.id = id;
		this.title = title;
		this.rating = rating;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the rating
	 */
	public double getRating() {
		return rating;
	}
	/**
	 * @param rating the rating to set
	 */
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
