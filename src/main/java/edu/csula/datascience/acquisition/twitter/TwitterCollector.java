package edu.csula.datascience.acquisition.twitter;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import edu.csula.datascience.acquisition.csv.Collector;
import edu.csula.datascience.utilities.TweetAnalysis;

import org.bson.Document;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TwitterCollector implements Collector<TwitterResponse, TwitterResponse> {
	MongoClient mongoClient;
	MongoDatabase database;
	MongoCollection<Document> collection;

	public TwitterCollector() {
		// establish database connection to MongoDB
		mongoClient = new MongoClient();

		database = mongoClient.getDatabase("movie-data");

		// select collection by name `tweets`
		collection = database.getCollection("tweets");
	}

	@Override
	public Collection<TwitterResponse> mungee(Collection<TwitterResponse> src) {

		// sets remove duplicates based on the equals() and hashCode() method of
		// TwitterResponse
		Set<TwitterResponse> noDups = new HashSet<TwitterResponse>(src);
//		System.out.println("Calling mungee...");
//		TweetAnalysis.printText(noDups);
		return noDups;
	}

	@Override
	public void save(Collection<TwitterResponse> data) {
		System.out.print("Data size in saved method" + data.size());
		List<Document> documents = data.stream()
				.map(item -> new Document().append("tweetId", item.getId()).append("favCt", item.getFavCt())
						.append("retwtCt", item.getRetwtCt()).append("username", item.getUserName())
						.append("text", item.getText()).append("lang", item.getLang())
						.append("source", item.getSource()))
				.collect(Collectors.toList());

		collection.insertMany(documents);
	}

}
