package edu.csula.datascience.elasticsearch;

import java.util.ArrayList;
import java.util.List;

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
import edu.csula.datascience.utilities.MongoUtilities;

public class MovieExporter extends Exporter {
	private final static String indexName = "beautiful-movie-team-data";
	private final static String typeName = "movies";
	private List<Movie> movies = new ArrayList<>();

	public MovieExporter(String clusterName) {
		super(clusterName);
	}

	@Override
	public void exportToES() {
		MongoUtilities mongo = new MongoUtilities("movie-data", "csv_files");
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
		}).setBulkActions(400).setBulkSize(new ByteSizeValue(1, ByteSizeUnit.GB))
				.setFlushInterval(TimeValue.timeValueSeconds(5)).setConcurrentRequests(1)
				.setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3)).build();

		int counter = 0;

		while (cursor.hasNext() && counter < 400) {
			Document document = cursor.next();
			if (validateDocument(document)) {
				Movie movie = new Movie(document.getInteger("movieID"), document.getString("title"),
						document.getString("hashtagTitle"), document.getDouble("rating"), document.getInteger("year"));
				movies.add(movie);
				insertObjAsJson(movie);
			}
			counter++; // our tweets are based on exactly 400 movies.
		}
	}

	@Override
	public void insertObjAsJson(Object object) {
		if (object != null && object instanceof Movie) {
			Movie movie = (Movie) object;
			bulkProcessor.add(new IndexRequest(indexName, typeName).source(new Gson().toJson(movie)));
			System.out.println("Movie record inserted into elastic search.");
		}
	}

	@Override
	public boolean validateDocument(Document document) {
		boolean docValid = false;
		docValid = validateValue(document.getString("title"));
		return docValid;
	}
	
	static class Movie {
		final int movieId;
		final String title;
		final String hashTitle;
		final double rating;
		final int year;

		public Movie(int movieId, String title, String hashTitle, double rating, int year) {
			this.movieId = movieId;
			// titles were stored as "title " in mongo
			this.title = title;
			this.hashTitle = hashTitle;
			this.rating = rating;
			this.year = year;
		}
	}
	
	public List<Movie> getMovies() {
		return movies;
	}
}
