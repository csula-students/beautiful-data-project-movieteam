package edu.csula.datascience.utilities;

import java.util.Collection;
import java.util.Iterator;

import edu.csula.datascience.acquisition.model.TwitterResponse;

public class TweetAnalysis {
	/**
	 * Helper methods
	 */
	public static void printText(Collection<TwitterResponse> list) {
		System.out.println("List size: " + list.size());
		Iterator<TwitterResponse> iterator = list.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next().getText());
		}
	}
}
