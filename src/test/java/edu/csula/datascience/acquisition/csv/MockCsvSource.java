package edu.csula.datascience.acquisition.csv;

import com.google.common.collect.Lists;

import edu.csula.datascience.acquisition.Source;
import edu.csula.datascience.acquisition.model.Movie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

/**
 * A mock source to provide data
 */
public class MockCsvSource implements Source<MovieModel> {
    int index = 0;
    ArrayList<MockCsvData> csvData = new ArrayList();

    public MockCsvSource(){
        csvData.add(new MockCsvData(1, "Toy Story (1995)", 4));
        csvData.add(new MockCsvData(1, "Toy Story (1995)", 5));
        csvData.add(new MockCsvData(2, "Force Awakens (2016)", 4));
        csvData.add(new MockCsvData(3, "Guardians of the Galaxy (2015)", 2));
        csvData.add(new MockCsvData(3, "Guardians of the Galaxy (2015)", 5));
        csvData.add(new MockCsvData(4, "testiÃ±g (3000)", 4));
        csvData.add(new MockCsvData(5, "we are (cool) (2000)", 3));
        csvData.add(new MockCsvData(6, "        ", 3));
        csvData.add(new MockCsvData(7, null, 3));
        csvData.add(new MockCsvData(8, "testing (2015)", 3));
    }
    @Override
    public boolean hasNext() {
        return index < csvData.size();
    }
    
    //simulate the csv data
    @Override
    public Collection<MovieModel> next() {
        double averageRating = 0.0;
        int count = 0;
        ArrayList<MovieModel> movies = new ArrayList();
        MockCsvData temp = null;
        do{
            temp = csvData.get(index);

            //for the average
            count++;
            averageRating += temp.getRating();

            index++;
        } while(hasNext() && temp.getId() == csvData.get(index).getId());
        MovieModel thisMovieModel = new MovieModel(temp.getId(), temp.getTitle());
        thisMovieModel.setRating(averageRating/(double)count);
        movies.add(thisMovieModel);
        return movies;
    }
}
