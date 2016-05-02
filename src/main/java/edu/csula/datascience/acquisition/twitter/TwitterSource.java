package edu.csula.datascience.acquisition.twitter;

import com.google.common.collect.Lists;

import edu.csula.datascience.acquisition.Source;
import edu.csula.datascience.acquisition.model.TwitterResponse;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class TwitterSource implements Source<TwitterResponse> {

	private final String[] searchQuery;
	private String TWITTER_CONSUMER_KEY = "KXjY39ROD2QMa0LUIkEBSnJMM";
	private String TWITTER_CONSUMER_SECRET = "lX27EskdFXqFCYwQCo7zK8c7FT9NgL2YpDox0anq4U9g6SrOKG";
	private String TWITTER_ACCESS_TOKEN = "722849671593402368-Fx0XHOaSsIyCQy0VJ6ZWnZ97TmWJ3CG";
	private String TWITTER_ACCESS_SECRET = "ByBD930AcAK9Ue00PvWXMFQ3o7j4RSRSZXthz0rigzKOk";
	private List<TwitterResponse> responses;
	private TwitterResponse currentResponse;
	TwitterStream twitterStream;
	private long startTime = System.currentTimeMillis();
	private long totalDuration;

	public TwitterSource(String[] query, long duration) {
		this.searchQuery = query;
		this.totalDuration = duration;
		try {
			printQueryString(searchQuery);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		startStream();
	}

	@Override
	public boolean hasNext() {
		long currentTime = System.currentTimeMillis();
		long duration = currentTime - startTime;
		return duration < totalDuration;
	}

	StatusListener listener = new StatusListener() {
		@Override
		public void onStatus(Status status) {
			System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());

			if (responses == null)
				responses = new ArrayList<>();

			currentResponse = new TwitterResponse(status.getId(), status.getFavoriteCount(), status.getRetweetCount(),
					status.getUser().getScreenName(), status.getText(), status.getCreatedAt().toString(),
					status.getSource());
		}

		@Override
		public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
			System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
		}

		@Override
		public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
			System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
		}

		@Override
		public void onScrubGeo(long userId, long upToStatusId) {
			System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
		}

		@Override
		public void onStallWarning(StallWarning warning) {
			System.out.println("Got stall warning:" + warning);
		}

		@Override
		public void onException(Exception ex) {
			ex.printStackTrace();
		}
	};

	@Override
	public Collection<TwitterResponse> next() {
		List<TwitterResponse> list = Lists.newArrayList();
		if (currentResponse != null) {
			list.add(currentResponse);
			currentResponse = null; // reset the currentResponse
		}
		return list;
	}

	public void startStream() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		// cb.setDebugEnabled(true).setOAuthConsumerKey(System.getenv("TWITTER_CONSUMER_KEY"))
		// .setOAuthConsumerSecret(System.getenv("TWITTER_CONSUMER_SECRET"))
		// .setOAuthAccessToken(System.getenv("TWITTER_ACCESS_TOKEN"))
		// .setOAuthAccessTokenSecret(System.getenv("TWITTER_ACCESS_SECRET"));

		// testing purposes, won't be final. will deal with hiding these keys
		// and secrets later
		cb.setDebugEnabled(true).setOAuthConsumerKey(TWITTER_CONSUMER_KEY)
				.setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET).setOAuthAccessToken(TWITTER_ACCESS_TOKEN)
				.setOAuthAccessTokenSecret(TWITTER_ACCESS_SECRET);

		twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
		twitterStream.addListener(listener);

		FilterQuery tweetFilterQuery = new FilterQuery();
		tweetFilterQuery.track(searchQuery);
		tweetFilterQuery.language(new String[] { "en" });
		twitterStream.filter(tweetFilterQuery);
	}

	public void stopStream() {
		twitterStream.shutdown();
	}

	/**
	 * Helper methods
	 */
	public void printText(Collection<TwitterResponse> list) {
		Iterator<TwitterResponse> iterator = list.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next().getText());
		}
	}
	
	public void printQueryString(String[] query) throws InterruptedException {
		System.out.println("Query string: ");
		for (int i = 0; i < query.length; i++) {
			System.out.println(query[i]);
		}
		
		Thread.sleep(5000);
	}
}
