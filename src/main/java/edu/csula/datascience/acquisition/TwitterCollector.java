package edu.csula.datascience.acquisition;

import com.google.common.collect.Lists;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import org.bson.Document;

import twitter4j.JSONObject;
import twitter4j.Status;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class TwitterCollector implements Collector<Status, Status> {
    MongoClient mongoClient;
    MongoDatabase database;
    MongoCollection<Document> collection;
    
    Collection<Status> cleanStatus;
    public TwitterCollector() {
        // establish database connection to MongoDB
        mongoClient = new MongoClient();

        database = mongoClient.getDatabase("movie-data");

        // select collection by name `tweets`
        collection = database.getCollection("tweets");
    }
    @Override
    public Collection<Status> mungee(Collection<Status> src) {
    	
//    	List<Status> finalSrc = (List<Status>) src;   	
//    	//filter out duplicate tweets by the same user. we don't 
//    	//want to consider a tweet to be popular just cause someone tweeted it a million times.
//    	for (Status status : finalSrc) {
//    		if (finalSrc.indexOf(status) >= 1) {
//    			
//    		}
//    	}
//    	
//        return finalSrc;
    	
    	List<Status> listOfStatus = Lists.newArrayList();
    	for(Status srcStatus: src)
    	{
    		listOfStatus.add(srcStatus); 
    	}
    	for(Status status: listOfStatus)
    	{
    		System.out.println("removed"+status); //just to check on the console
    		listOfStatus.remove(status);
    		for(Status statusCompare: listOfStatus)	
    		{
    			
    			if(statusCompare.getUser().getName().equals(status.getUser().getName()))
    			if(statusCompare.getText().equals(status.getText()))
    			{
    					
    			}
    			else
    			{
    				cleanStatus.add(statusCompare);
    			}
    				
    		}
    	}	
    	   	
    	return cleanStatus;
    }

    /**
     * James <--- I added item.getFavoriteCount() and item.getRetweetCount() b/c maybe
     *  the amt of times someomeone favorites a tweet or retweets a tweet about a movie matter?
     */
    @Override
    public void save(Collection<Status> data) {
        List<Document> documents = data.stream()
            .map(item -> new Document()
                .append("tweetId", item.getId())
                .append("favCt", item.getFavoriteCount())
                .append("retwtCt", item.getRetweetCount())
                .append("username", item.getUser().getName())
                .append("text", item.getText())
                .append("lang", item.getLang())
                .append("source", item.getSource()))
            .collect(Collectors.toList());

        collection.insertMany(documents);
    }
    
    /**
     * helper methods
     */
    public static void displayData() {
    	
    }
}
