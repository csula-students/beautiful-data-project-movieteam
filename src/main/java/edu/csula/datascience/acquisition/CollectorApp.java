package edu.csula.datascience.acquisition;

import edu.csula.datascience.acquisition.csv.CsvCollector;
import edu.csula.datascience.acquisition.csv.CsvSource;
import edu.csula.datascience.acquisition.model.Movie;
import edu.csula.datascience.acquisition.model.TwitterResponse;
import edu.csula.datascience.acquisition.twitter.TwitterCollector;
import edu.csula.datascience.acquisition.twitter.TwitterSource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CollectorApp {
	
	public static void main(String[] args) throws InterruptedException {		
		CollectorApp app = new CollectorApp();
		app.startMovieCollection();
	}
	
	public void startMovieCollection() {
		String file = "mergedMovieData.csv";
		CsvSource source = new CsvSource(file, true);
		CsvCollector collector = new CsvCollector();
		List<String> mungedMoviesTitles = new ArrayList<>(); //store a bunch of munged movies

		while (source.hasNext()) {
			Collection<Movie> movies = source.next(); //returns exactly one movie
			List<Movie> mungedMovies = new ArrayList(collector.mungee(movies));//list with exactly one thing, mungeed.
			if (!mungedMovies.isEmpty() && mungedMoviesTitles.size() < 400) {
				System.out.println("SIZE OF NON-SAVED MUNGED " + mungedMovies.size() + " and year is " + mungedMovies.get(0).getYear());
				Movie mungedMovie = mungedMovies.get(0);
				if (mungedMovie.getYear() >= 2015) {
					System.out.println("SIZE OF SAVED MUNGED " + mungedMovies.size() + " and year is " + mungedMovies.get(0).getYear());
					collector.save(mungedMovies); //save it in the collection 'csv_files'
					mungedMoviesTitles.add('#' + mungedMovie.getHashtagTitle());
				}
			}						
		}
		
		if (mungedMoviesTitles.size() != 0) startTwitterStream(mungedMoviesTitles);
	}
	
	private void startTwitterStream(List<String> mungedMoviesTitles) {
		long streamDuration = 7200000; //listen for 2 hours
		
		//this will contain an array of movie titles, each of which the TwitterStream API will listen for.
		String[] searchQueries = mungedMoviesTitles.toArray(new String[mungedMoviesTitles.size()]);
		System.out.println("munged movie titles size: " + searchQueries.length);
		TwitterSource source = new TwitterSource(searchQueries, streamDuration);
		TwitterCollector collector = new TwitterCollector();
		
		Set<TwitterResponse> initResponses = new HashSet<TwitterResponse>();
		
		while (source.hasNext()) {
			Collection<TwitterResponse> tweets = source.next();
			if (tweets.size() != 0) {
				initResponses.addAll(tweets);
			}
		}
		
		source.stopStream();
		Collection<TwitterResponse> cleanedTweets = collector.mungee(initResponses); //mungee all the tweets we got
		collector.save(cleanedTweets); //then save the collection		
	}
}
