package edu.csula.datascience.acquisition.csv;

import java.util.ArrayList;
import java.util.Collection;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.csula.datascience.acquisition.Collector;

/**
 * A mock implementation of collector for testing
 */
public class MockCsvCollector implements Collector<MovieModel, MovieModel> {

	public MockCsvCollector(){
	}

	@Override
	public Collection<MovieModel> mungee(Collection<MovieModel> src) {

		ArrayList<MovieModel> srcArray = new ArrayList(src);
		ArrayList<MovieModel> finalMovieEntry = new ArrayList();
		double averageRating = srcArray.get(0).getRating();
		String title = srcArray.get(0).getTitle();
		int year = 0;


		if (isValidTitle(title)) {
			//regex found on stackoverflow
			Matcher m = Pattern.compile("\\(([^)]+)\\)").matcher(title);

			while(m.find()) {
				try {
					year = Integer.parseInt(m.group(1));
				} catch (Exception e){
					//if there is a parenthesis in the title
				}
			}
			title = replaceLast(title, "\\(.*?\\) ?", "");
			MovieModel theMovie = new MovieModel(srcArray.get(0).getId(), title);

			theMovie.setRating(averageRating);
			theMovie.setYear(year);
			finalMovieEntry.add(theMovie);
		}

		return finalMovieEntry;
	}

	@Override
	public void save(Collection<MovieModel> data) {
	}

	// regex helper methods from StackOverflow
	private static boolean isValidTitle(String title) {
		boolean valid = ((title != null) && title.matches("^[a-zA-Z0-9_() ]*$")
				&& (!title.replaceAll("\\s+", "").isEmpty()));
		return valid;
	}

	private static String replaceLast(String text, String regex, String replacement) {
		return text.replaceFirst("(?s)(.*)" + regex, "$1" + replacement);
	}
}
