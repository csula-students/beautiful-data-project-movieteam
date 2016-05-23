package edu.csula.datascience.elasticsearch;

import java.util.List;
import edu.csula.datascience.elasticsearch.MovieExporter.Movie;

public class ExportESApp {

	public static void main(String[] args) {
		
		//pass your cluster name to the constructor
		MovieExporter movieExp = new MovieExporter("darkserith");
		movieExp.exportToES();
		List<Movie> movies = movieExp.getMovies();
		
		//pass in your cluster name to the constructor
		TweetExporter tweetExp = new TweetExporter("darkserith", movies);
		tweetExp.exportToES();
	}	
}
