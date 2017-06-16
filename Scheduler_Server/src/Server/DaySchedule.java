package Server;

import java.io.Serializable;

import common.*;

//class 설명
//Day와 time 정보를 가지고 있으며 ScheduleManager애서 ArrayList로 구현 되어있다.
//날짜가 바뀌면 ScheduleManager애있는 nextDay method이용 List내 날짜를 바꾼다.

public class DaySchedule implements Serializable{

	private Day thisDay;

	private short time[] = new short[12];

	//constructor
	public DaySchedule() {
		this(Day.MON);
	}

	public DaySchedule(Day day) {
		thisDay = day;
	}

	public DaySchedule(Day day, short time[]) {
		thisDay = day;
		this.time = time;
	}

	//edit Day
	public Day getDay() {
		return thisDay;
	}

	public void setDay(Day day) {
		thisDay = day;
	}

	//edit personal schedule
	public void setScheduleUnit(short sc,int i) {
		time[i] = sc;
	}
	
	public void setSchedule(short sc[]) {
		time = sc;
	}

	public short[] getSchedule() {
		return time;
	}
}
