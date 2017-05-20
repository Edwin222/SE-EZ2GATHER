package Server;

import common.*;

public class DaySchedule {

	private Day thisDay;

	private short time[] = new short[12];

	///////////////////// constructor////////////////////
	public DaySchedule() {
		thisDay = Day.MON;
	}

	public DaySchedule(Day day) {
		thisDay = day;
	}

	public DaySchedule(Day day, short time[]) {
		thisDay = day;
		this.time = time;
	}
	//////////////////////////////////////////////////////

	/////////// edit Day///////////////////////////
	public Day getDay() {
		return thisDay;
	}

	public void setDay(Day day) {
		thisDay = day;
	}
	//////////////////////////////////////////////

	/////////// create New ID/////////////////////////////////
	public void newID(int i) {// if new ID is created, we should initialize that
								// idx.
		for (int k = 0; k < 12; k++)
			time[k] = (short) (time[k] + (1 << i));
	}

	////////////////////////////////////// edit personal
	////////////////////////////////////// schedule///////////////////////////////////////////////
	public void setSchedule(short sc[]) {
		time = sc;
	}

	public short[] getSchedule() {
		return time;
	}
}

/*
 * ////////////////////////edit Fixed
 * schedule////////////////////////////////////////////////////////////// public
 * void addSchedule(int ID, ScheduleUnit table){//get ID idx as int, and
 * FixedScheduleUnit table.
 * 
 * short idx = 1;
 * 
 * idx = (short) (idx << ID);
 * 
 * for(int i = table.getBegin(); i <= table.getEnd();){
 * 
 * if(time[i]%idx == 1)//check the ID has same time schedule. continue;
 * 
 * else time[i] = (short) (time[i] + idx);
 * 
 * i++; } }
 * 
 * public void DeleteSchedule(int ID, ScheduleUnit table){
 * 
 * short idx = 1;
 * 
 * idx = (short) (idx << ID);
 * 
 * for(int i = table.getBegin(); i <= table.getEnd();){
 * 
 * if(time[i]%idx != 1)// check whether the ID has that time schedule or not.
 * continue;
 * 
 * else time[i] = (short) (time[i] - idx); i++; } }
 * /////////////////////////////////////////////////////////////////////////////
 * //////////////////////////////
 */