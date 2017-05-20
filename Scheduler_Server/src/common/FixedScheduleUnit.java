package common;

public class FixedScheduleUnit extends ScheduleUnit {
	
	private Day day;
	
	public FixedScheduleUnit(int timeBegin, int timeEnd, Day day){
		super(timeBegin, timeEnd);
		this.day = day;
	}
	
	public Day getDay(){
		return day;
	}
}
