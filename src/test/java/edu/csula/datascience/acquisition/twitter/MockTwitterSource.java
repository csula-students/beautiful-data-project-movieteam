package edu.csula.datascience.acquisition.twitter;

import com.google.common.collect.Lists;

import edu.csula.datascience.acquisition.Source;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * A mock source to provide data
 */
public class MockTwitterSource implements Source<MockTwitterData> {
    int index = 0;
    private final String text = "Hi @Harry_Styles, "
    		+ "I hope you're having a day as lovely as you are. "
    		+ "Thank you for being you. Mind following me? "
    		+ "All my love x —212,546";

    @Override
    public boolean hasNext() {
        return index < 1;
    }

    @Override
    public Collection<MockTwitterData> next() {
        return Lists.newArrayList(
            new MockTwitterData(
            		Long.valueOf("725439389501902848"),
            		0,
            		1,
            		"hesbless",
            		text,
            		"Wed Apr 27 14:40:01 PDT 2016",
            		"<a href=\"http://twitter.com/download/iphone\""
            		+ " rel=\"nofollow\">Twitter for iPhone</a>"),
            new MockTwitterData(
            		Long.valueOf("725439389501902848"),
            		0,
            		1,
            		"hesbless",
            		text,
            		"Wed Apr 27 14:40:01 PDT 2016",
            		"<a href=\"http://twitter.com/download/iphone\""
            		+ " rel=\"nofollow\">Twitter for iPhone</a>"),
            new MockTwitterData(
            		Long.valueOf("725439389501902849"),
            		0,
            		1,
            		"hesbless",
            		text,
            		"Wed Apr 27 14:40:01 PDT 2016",
            		"<a href=\"http://twitter.com/download/iphone\""
            		+ " rel=\"nofollow\">Twitter for iPhone</a>"),
            new MockTwitterData(
            		Long.valueOf("725439389501902849"),
            		0,
            		1,
            		"darkserith",
            		"Hei!!!!!",
            		"Wed Apr 27 14:40:01 PDT 2016",
            		"<a href=\"http://twitter.com/download/iphone\""
            		+ " rel=\"nofollow\">Twitter for iPhone</a>"),
            new MockTwitterData(
            		Long.valueOf("725439389501902850"),
            		0,
            		1,
            		"tim_cook",
            		"HI!!!!!!",
            		"Wed Apr 27 14:40:01 PDT 2016",
            		"<a href=\"http://twitter.com/download/iphone\""
            		+ " rel=\"nofollow\">Twitter for iPhone</a>")            
        );
    }
}
