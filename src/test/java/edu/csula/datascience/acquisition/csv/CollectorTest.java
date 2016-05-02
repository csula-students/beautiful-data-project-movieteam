package edu.csula.datascience.acquisition.csv;

import com.google.common.collect.Lists;

import edu.csula.datascience.acquisition.Collector;
import edu.csula.datascience.acquisition.Source;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * A test case to show how to use Collector and Source
 */
public class CollectorTest {
    private Collector<MovieModel, MovieModel> collector;
    private Source<MovieModel> source;
   

    @Before
    public void setup() {
        collector = new MockCsvCollector();
        source = new MockCsvSource();
    }

    @Test
    public void mungee() throws Exception {
//    	System.out.println(source.next().size());
    	List<MovieModel> expected = Lists.newArrayList(
                new MovieModel(1, "Toy Story ", 4.5, 1995),
                new MovieModel(2, "Force Awakens ", 4.0, 2016),
                new MovieModel(3, "Guardians of the Galaxy ", 3.5, 2015),
                new MovieModel(5, "we are (cool) ", 3.0, 2000),
                new MovieModel(8, "testing ", 3.0, 2015)
        );

        int expectedIndex = 0;

        do {
            List<MovieModel> list = (List<MovieModel>)collector.mungee(source.next());
            if (list.size() == 0){
                continue;
            }
            Assert.assertEquals(list.get(0).getId(), expected.get(expectedIndex).getId());
            Assert.assertEquals(list.get(0).getTitle(), expected.get(expectedIndex).getTitle());
            Assert.assertEquals(list.get(0).getRating(), expected.get(expectedIndex).getRating(), .01);
            Assert.assertEquals(list.get(0).getYear(), expected.get(expectedIndex).getYear());
            expectedIndex++;
        } while(source.hasNext());
    }
}