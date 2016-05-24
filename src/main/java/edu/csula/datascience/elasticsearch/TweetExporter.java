package edu.csula.datascience.elasticsearch;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.bson.Document;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import com.google.gson.Gson;
import com.mongodb.client.MongoCursor;

import edu.csula.datascience.elasticsearch.model.Movie;
import edu.csula.datascience.utilities.MongoUtilities;

public class TweetExporter extends Exporter {
	private final static String indexName = "beautiful-movie-team-data";
	private final static String typeName = "tweets";
	private List<Movie> movies;
	private List<Tweet> tweets = new ArrayList<>();

	public TweetExporter(String clusterName, List<Movie> movies) {
		super(clusterName);
		this.movies = movies;
	}

	@Override
	public void exportToES() {
		int tweetCounter = 0;
		int documentCounter = 0;
		MongoUtilities mongo = new MongoUtilities("movie-data", "tweets");
		MongoCursor<Document> cursor = mongo.getCollection().find().iterator();
		bulkProcessor = BulkProcessor.builder(client, new BulkProcessor.Listener() {
			@Override
			public void beforeBulk(long executionId, BulkRequest request) {
			}

			@Override
			public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
			}

			@Override
			public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
				System.out.println("Facing error while importing data to elastic search");
				failure.printStackTrace();
			}
		}).setBulkActions(10000).setBulkSize(new ByteSizeValue(1, ByteSizeUnit.GB))
				.setFlushInterval(TimeValue.timeValueSeconds(5)).setConcurrentRequests(1)
				.setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3)).build();

		while (cursor.hasNext()) {
			
			Document document = cursor.next();
			documentCounter++;
			if (validateDocument(document)) {
//				System.out.println("Document #: " + documentCounter + " analyzed.");
				String tweetTxt = document.getString("text");
				for (Movie movie : movies) {
					String hashTitle = movie.getHashTitle();
					String movieTitle = movie.getTitle();
					if (tweetTxt.contains(hashTitle) || tweetTxt.contains(movieTitle)) {
						tweetCounter++;
						Tweet tweet = new Tweet(document.getString("username"), document.getString("text"),
								movieTitle, hashTitle, movie.getRating(), document.getString("date"));
						tweets.add(tweet); //add a tweet
						System.out.println("Tweet #: " + tweetCounter + " added to tweets list; " + tweet.text) ;
					}
				}
//				insertObjAsJson(tweet);
			}
		}
		
		if (tweets.size() != 0) {
			addSentimentForMovie();
		}
		//insert list into insertObjAsJson method
	}
	
//	/**
//	 * Takes a collection of Tweet objects and inserts each of them into elastic search.
//	 * @param tweets
//	 * 		list of tweets to be inserted
//	 */
//	public void insertTweets(List<Tweet> tweets) {
//		for (Tweet tweet : tweets) {
//			insertObjAsJson(tweet);
//		}
//		System.out.println("Tweets list size: " + tweets.size());
//	}
	
	public void addSentimentForMovie()
	{
		 try {
			List<String> positiveWords = Files.readAllLines(Paths.get(ClassLoader.getSystemResource("positive-words.txt").toURI()));
			List<String> negativeWords = Files.readAllLines(Paths.get(ClassLoader.getSystemResource("negative-words.txt").toURI()));
		
			for(Movie movie: movies)
			{
				int positiveCounter = 0;
				int negativeCounter = 0;
				int tweetCounter = 0;
				double sentiment = 0.0;
				for(Tweet tweet: tweets){
					if(tweet.title.equals(movie.getTitle()))
					{
						String []tweetArray = tweet.text.split("\\s");
						tweetCounter++;
						for(String positiveWord: positiveWords)
						{
							for(int i=0; i<tweetArray.length; i++)
							if(tweetArray[i].contains(positiveWord)){
								positiveCounter++;
							}
						}
						for(String negativeWord: negativeWords)
						{
							for(int i=0; i<tweetArray.length; i++)
							if(tweetArray[i].contains(negativeWord)){
								negativeCounter++;
							}
						}
					}	
				}
				if(tweetCounter != 0)
				{
					sentiment = (double) (positiveCounter - negativeCounter) / tweetCounter;
				}
				System.out.println("Number of tweets for movie:"+movie.getTitle()+" is "+tweetCounter);
				System.out.println("Number of Positive words of tweets for movie:"+movie.getTitle()+" is "+positiveCounter);
				System.out.println("Number of Negative words of tweets for movie:"+movie.getTitle()+" is "+negativeCounter);
				System.out.println("Sentiment for movie:"+movie.getTitle()+ "is :" +sentiment);
				
				movie.setSentiment(sentiment);
			}
			
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Takes an object, casts it to Tweet, and inserts that Tweet as JSON into ElasticSearch
	 */
	@Override
	public void insertObjAsJson(Object object) {
		if (object != null && object instanceof Tweet) {
			Tweet tweet = (Tweet) object;
			bulkProcessor.add(new IndexRequest(indexName, typeName).source(new Gson().toJson(tweet)));
			System.out.println("Tweet record inserted into elastic search.");
		}
	}
	
	@Override
	public boolean validateDocument(Document document) {
		boolean docValid = false;
		docValid = (validateValue(document.getString("username")) && validateValue(document.getString("text"))
				&& validateValue(document.getString("date")));

		return docValid;
	}

	private class Tweet {
		final String user;
		final String text;
		final String title;
		final String hashTitle;
		final double rating;
		final String date;

		public Tweet(String user, String text, String title, String hashTitle,
				double rating, String date) {
			this.user = user;
			this.text = text;
			this.title = title;
			this.hashTitle = hashTitle;
			this.rating = rating;
			// Need parseDate to convert to a format of 'YY-MM-DD' for elastic
			// search
			this.date = parseDate(date);
		}

		public String parseDate(String date) {
			Locale dateLocale = Locale.US;
			DateTimeFormatter inFormatter = DateTimeFormatter.ofPattern("E MMM dd HH:mm:ss z yyyy", dateLocale);
			TemporalAccessor tempDate = inFormatter.parse(date);
			DateTimeFormatter outFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
			return outFormatter.format(tempDate);
		}
	}
}