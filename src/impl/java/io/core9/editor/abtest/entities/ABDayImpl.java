package io.core9.editor.abtest.entities;

import java.util.Date;
import java.util.List;

public class ABDayImpl implements ABDay {

	@SuppressWarnings("unused")
	private Date date;
	private List<ABTimeRange> timeRangesOnDay;

	public ABDayImpl(Date date) {
		this.date = date;
	}


	@Override
	public List<ABTimeRange> getTimeRangesOnDay() {
		return timeRangesOnDay;
	}

	@Override
	public void setTimeRangesOnDay(List<ABTimeRange> timeRangesOnDay) {
		this.timeRangesOnDay = timeRangesOnDay;
	}

}
