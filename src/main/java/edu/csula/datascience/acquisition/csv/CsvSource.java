package edu.csula.datascience.acquisition.csv;


import java.io.File;
import java.util.ArrayList;
import java.util.Collection;


import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import edu.csula.datascience.acquisition.Source;
import edu.csula.datascience.acquisition.model.Movie;


public class CsvSource implements Source<Movie>{
    private CsvParser cp1, cp2;
    private final String DELIMITER = ",";
    private String line[];
    private CsvParserSettings settings = new CsvParserSettings();

    public CsvSource(String fileName, boolean hasHeader){
        try {
            settings.getFormat().setLineSeparator("\n");
            File file = new File(ClassLoader.getSystemResource(fileName).toURI());
            cp1 = new CsvParser(settings);
            cp2 = new CsvParser(settings);

            cp1.beginParsing(file);
            cp2.beginParsing(file);

            //skips header of csv file
            if (hasHeader) {
                line = cp1.parseNext();
                line = cp2.parseNext();
            }
            //puts br2 1 line ahead;
            line = cp2.parseNext();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean hasNext(){

        if (line != null){
            return true;
        } else {
            return false;
        }
    }

    public Collection<Movie> next(){
        ArrayList<Movie> movies = new ArrayList();
        int counter = 0;
        String[] tokens = line;
        double rating = 0.0;
        try {
            do{
                counter++;
                line = cp1.parseNext();
                try {
                    rating += Double.parseDouble(line[4]);
                } catch(Exception e){
                }
                tokens = line;

                //check if next entry
                line = cp2.parseNext();
                if (line == null || !line[0].equals(tokens[0])){
                    break;
                }
            } while(hasNext());
        } catch (Exception e){
            e.printStackTrace();
        }
        Movie tempMovie = new Movie();
        tempMovie.setId(Integer.parseInt(tokens[0]));
        tempMovie.setTitle(tokens[1]);
        tempMovie.setRating(rating/(double)counter);
        movies.add(tempMovie);
        return movies;
    }
}
