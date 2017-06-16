package Server;

import java.io.Serializable;

import common.*;

//class ����
//Day�� time ������ ������ ������ ScheduleManager�ּ� ArrayList�� ���� �Ǿ��ִ�.
//��¥�� �ٲ�� ScheduleManager���ִ� nextDay method�̿� List�� ��¥�� �ٲ۴�.

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
