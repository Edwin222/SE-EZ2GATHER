package client;

import common.*;
import java.util.ArrayList;
import java.io.*;

public class UserManager {
	
	private String ID;
	private Day[] findDay;
	private String[] ID_list;
	private String notice;
	//constant
	final short BLANK = 0; //no schedule
	final short UNFIXED = 1; //unfixed schedule
	final short FIXED = 2; //fixed schedule
	
	final short COL = 7;
	final short ROW = 12;
	
	//table for view
	private ArrayList<FixedScheduleUnit> FixedSchedule;
	ArrayList<Integer> people;
	
	private short[][] organizedTable;
	private short[][] personalTable;

	//table for edit
	private short[][] temporaryTable;
	private boolean isModified;
	
	
	public UserManager(String _ID){
		
		isModified = false;
		FixedSchedule = new ArrayList<FixedScheduleUnit>();
		people = new ArrayList<Integer>();
		
		ID = _ID;
		findDay = new Day[COL];
		
		organizedTable = new short[ROW][COL];
		personalTable = new short[ROW][COL];
		temporaryTable = new short[ROW][COL];
	}
	
	public String getID()
	{
		return ID;
	}
	
	public void setNotice(String _notice)
	{
		notice = _notice;
	}
	public short getPointOTable(int row, int col){
		return organizedTable[row][col];
	}
	
	public short getPointPTable(int row, int col){
		return personalTable[row][col];
	}
	
	public short getPointTTable(int row, int col){
		return temporaryTable[row][col];
	}
	
	public void saveData(){
		try {
			FileOutputStream fp = new FileOutputStream("data.bin");
			ObjectOutputStream op = new ObjectOutputStream(fp);
			
			op.writeObject(FixedSchedule);
			
			op.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void loadData(){
		try {
			FileInputStream fp = new FileInputStream("data.bin");
			ObjectInputStream op = new ObjectInputStream(fp);
			
			FixedSchedule = ( ArrayList<FixedScheduleUnit>) op.readObject();
			
			op.close();
			
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void add_Schedule(ScheduleUnit schedule, int start_pos, int end_pos) {
		
		int begin_time = schedule.getBegin();
		int end_time = schedule.getEnd();
		
		for(int i = begin_time; i<= end_time; i++) {
			
			for(int j = start_pos; j<=end_pos; j++) {
				temporaryTable[i][j] = UNFIXED;
			}
		}
		
		isModified = true;
	}
	
	public void remove_Schedule(ScheduleUnit schedule, int start_pos, int end_pos) {
		
		int begin_time = schedule.getBegin();
		int end_time = schedule.getEnd();
		
		for(int i = begin_time; i<=end_time; i++) {
			
			for(int j = start_pos; j<=end_pos; j++) {
				temporaryTable[i][j] = BLANK;
			}
		}
		
		isModified = true;
	}
	
	public void add_FixedSchedule(FixedScheduleUnit schedule, int start_pos, int end_pos) {
		
		//개인 스케줄목록에 더하기
		int begin_time = schedule.getBegin();
		int end_time = schedule.getEnd();
		
		for(int i = begin_time; i<= end_time; i++) {
			
			for(int j = start_pos; j<=end_pos; j++) {
				temporaryTable[i][j] = FIXED;
			}
		}
		
		FixedSchedule.add(schedule); //고정스케줄목록에 더하기
		isModified = true;
		
	}
	
	public void remove_FixedSchedule(FixedScheduleUnit schedule, int start_pos, int end_pos) 
	{	
		//개인 스케줄목록에 빼기
		int begin_time = schedule.getBegin();
		int end_time = schedule.getEnd();
		
		for(int i=0;i<FixedSchedule.size();i++){
			FixedScheduleUnit cursor = FixedSchedule.get(i);
			
			if(cursor.getDay().equals(schedule.getDay())){
				int median = (begin_time+end_time)/2;
				
				if(median >= cursor.getBegin() && median <= cursor.getEnd()){
					
					//find col
					int schedulePos = 0;
					for(int j=0;j<COL;j++){
						if(findDay[j].equals(schedule.getDay())){
							schedulePos = j;
							break;
						}
					}
					
					for(int j=cursor.getBegin(); j <= cursor.getEnd(); j++ )
						temporaryTable[j][schedulePos] = BLANK;
					
					FixedSchedule.remove(cursor);
						
				}
				
			}
		}
		
		isModified = true;
	}
	
	public void save() { //변경된 스케줄테이블을 현재 테이블에 덮어씌운다

		personalTable = temporaryTable;
		
		isModified = false;
		
	}
	
	public void Open_Edit()
	{
		temporaryTable = personalTable;
	}
	
	public boolean Check_Edited()
	{
		return isModified;
	}
	
	public void renewal(Day d, short[][] newPTable, short[][] newOTable)
	{
		Day temp = d;
		for(int i=0; i< COL ;i++){
			findDay[i] = temp;
			temp = Day.getNextDay(temp);
		}
		
		personalTable = newPTable;
		organizedTable = newOTable;
	}
	
	public short[][] getPTable()
	{
		return personalTable;
	}
	
	public void setIDlist(String[] _id)
	{
		ID_list = _id;
	}
	
	public void setOTable(short[][] _OTable)
	{
		organizedTable = _OTable;
	}
	
	public ArrayList<FixedScheduleUnit> getFixedSchedule()
	{
		return FixedSchedule;
	}
	
	private boolean check_schedule(short t, int id)
	{
		if((t>>id)%2 == 1)
			return true;
		else
			return false;
	}
	
	public ArrayList<Integer> show_organized_table(int i, int j)
	{
		for(int k=0; k<ID_list.length; k++)
			{
				if(check_schedule((organizedTable[i][j]), k))
					people.add(k);
			}
		return people;
	}
}
