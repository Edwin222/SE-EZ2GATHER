package common;

import java.io.Serializable;

public class FixedScheduleUnit extends ScheduleUnit implements Serializable {
	
	private Day day;
	
	public FixedScheduleUnit(int timeBegin, int timeEnd, Day day){
		super(timeBegin, timeEnd);
		this.day = day;
	}
	
	public Day getDay(){
		return day;
	}
}
