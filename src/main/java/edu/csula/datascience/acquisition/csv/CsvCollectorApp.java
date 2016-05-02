package edu.csula.datascience.acquisition.csv;

import edu.csula.datascience.acquisition.model.Movie;
import edu.csula.datascience.utilities.MongoUtilities;

import java.util.ArrayList;
import java.util.Collection;

public class CsvCollectorApp {
    public static void main(String[] args){
        //TODO add file
        String file = "mergedMovieData.csv";
        CsvSource source = new CsvSource(file, true);
        CsvCollector collector = new CsvCollector();

        while (source.hasNext()){
            Collection<Movie> movies = source.next();
            Collection<Movie> mungedMovies = collector.mungee(movies);
            ArrayList<Movie> munged = new ArrayList(mungedMovies);
            if (!mungedMovies.isEmpty() && munged.get(0).getYear() > 2010){
                collector.save(mungedMovies);
            }
            else{
                continue;
            }

        }

        MongoUtilities mongoUtil = new MongoUtilities("movie-data", "csv_files");
        mongoUtil.printDocuments();

    }
}
