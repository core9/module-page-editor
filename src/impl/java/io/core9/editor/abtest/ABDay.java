package io.core9.editor.abtest;

import java.util.List;

public interface ABDay {

	void setTimeRangesOnDay(List<ABTimeRange> timeRangesOnDay);

	List<ABTimeRange> getTimeRangesOnDay();

}
