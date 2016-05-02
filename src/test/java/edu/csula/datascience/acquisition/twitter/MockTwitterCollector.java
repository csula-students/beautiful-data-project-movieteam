package edu.csula.datascience.acquisition.twitter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import edu.csula.datascience.acquisition.Collector;

/**
 * A mock implementation of collector for testing
 */
public class MockTwitterCollector implements Collector<TwitterModel, MockTwitterData> {
    @Override
    public Collection<TwitterModel> mungee(Collection<MockTwitterData> src) {
        // in your example, you might need to check src.hasNext() first
        Collection<TwitterModel> newSrc = src
            .stream()
            .filter(data -> data.getText() != null)
            .map(TwitterModel::build)
            .collect(Collectors.toList());
        
        Set<TwitterModel> noDups = new HashSet<TwitterModel>(newSrc);
		return noDups;
    }

    @Override
    public void save(Collection<TwitterModel> data) {
    }
}
