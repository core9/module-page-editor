package io.core9.editor.abtest;

import java.util.Date;
import java.util.List;

public class ABDayImpl implements ABDay {

	private Date date;
	private List<ABTimeRange> timeRangesOnDay;

	public ABDayImpl(Date date) {
		this.date = date;
	}

	@Override
	public void setTimeRanges(List<ABTimeRange> timeRangesOnDay) {
		this.timeRangesOnDay = timeRangesOnDay;
	}

}
