package edu.csula.datascience.acquisition.twitter;

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
	private Collector<TwitterModel, MockTwitterData> collector;
	private Source<MockTwitterData> source;
	private final String text = "Hi @Harry_Styles, " + "I hope you're having a day as lovely as you are. "
			+ "Thank you for being you. Mind following me? " + "All my love x —212,546";

	@Before
	public void setup() {
		collector = new MockTwitterCollector();
		source = new MockTwitterSource();
	}

	@Test
	public void mungee() throws Exception {

		List<TwitterModel> list = new ArrayList<>(collector.mungee(source.next()));

		List<TwitterModel> expectedList = Lists
				.newArrayList(
						new TwitterModel(Long.valueOf("725439389501902848"), 0, 1, "hesbless", text,
								"Wed Apr 27 14:40:01 PDT 2016",
								"<a href=\"http://twitter.com/download/iphone\""
										+ " rel=\"nofollow\">Twitter for iPhone</a>"),
						new TwitterModel(Long.valueOf("725439389501902849"), 0, 1, "darkserith", "Hei!!!!!",
								"Wed Apr 27 14:40:01 PDT 2016",
								"<a href=\"http://twitter.com/download/iphone\""
										+ " rel=\"nofollow\">Twitter for iPhone</a>"),
						new TwitterModel(Long.valueOf("725439389501902850"), 0, 1, "tim_cook", "HI!!!!!!",
								"Wed Apr 27 14:40:01 PDT 2016", "<a href=\"http://twitter.com/download/iphone\""
										+ " rel=\"nofollow\">Twitter for iPhone</a>"));

		Assert.assertEquals(3, list.size());

		for (int i = 0; i < 3; i++) {
			Assert.assertEquals(expectedList.get(i).getId(), list.get(i).getId());
			Assert.assertEquals(expectedList.get(i).getFavCt(), list.get(i).getFavCt());
			Assert.assertEquals(expectedList.get(i).getRetwtCt(), list.get(i).getRetwtCt());
			Assert.assertEquals(expectedList.get(i).getUserName(), list.get(i).getUserName());
			Assert.assertEquals(expectedList.get(i).getText(), list.get(i).getText());
			Assert.assertEquals(expectedList.get(i).getDate(), list.get(i).getDate());
			Assert.assertEquals(expectedList.get(i).getSource(), list.get(i).getSource());
		}
	}
}