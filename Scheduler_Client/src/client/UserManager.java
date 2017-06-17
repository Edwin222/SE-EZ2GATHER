package client;

import common.*;
import java.util.ArrayList;
import java.io.*;
//1 : 시간이 되는거, 0 : 안되는가

public class UserManager {
	
	private String ID;
	private Day[] findDay;
	private String[] IDlist;
	private int personNum;
	private String notice;
	//constant
	final short BLANK = 0; //no schedule
	final short UNFIXED = 1; //unfixed schedule
	final short FIXED = 2; //fixed schedule
	
	final short avail = 1;
	final short unavail = 0;
	
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
	
	public int getPersonNum(){
		return personNum;
	}
	
	public void setPersonNum(int num){
		personNum = num;
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
			
		} catch(FileNotFoundException e){
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void add_Schedule(ScheduleUnit schedule, int start_pos, int end_pos) {
		
		int begin_time = schedule.getBegin();
		int end_time = schedule.getEnd();
		
		for(int i = begin_time; i<= end_time; i++) {
			
			for(int j = start_pos; j<=end_pos; j++) {
				
				if(temporaryTable[i][j] == BLANK)
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
				
				if(temporaryTable[i][j] == UNFIXED)
					temporaryTable[i][j] = BLANK;
			}
		}
		
		isModified = true;
	}
	
	public void add_FixedSchedule(FixedScheduleUnit schedule, int start_pos, int end_pos) {
		
		for(int i=start_pos; i<=end_pos; i++){
			FixedScheduleUnit newSchedule = new FixedScheduleUnit(schedule.getBegin(), schedule.getEnd(), findDay[i]);
			FixedSchedule.add(newSchedule); //占쏙옙占쏙옙占쏙옙占쏙옙占쌕몌옙臼占� 占쏙옙占싹깍옙
		}
		
		isModified = true;
	}
	
	public void remove_FixedSchedule(FixedScheduleUnit schedule, int start_pos, int end_pos) 
	{	


		int begin_time = schedule.getBegin();
		int end_time = schedule.getEnd();
		
		for(int i=0; i<FixedSchedule.size(); i++) {
			
			FixedScheduleUnit cursor = FixedSchedule.get(i);
						

			if(cursor.getBegin() >= begin_time && cursor.getEnd()<= end_time) {
				for( int j=start_pos ; j <= end_pos; j++) {
				 
					if(cursor.getDay().equals(findDay[j]));
						FixedSchedule.remove(cursor);

				}
			}
		}
		
		isModified = true;
	}

	public void save() {


		personalTable = temporaryTable;
		
		isModified = false;
		
	}
	
	public void Open_Edit()
	{
		temporaryTable = personalTable;
	}
	
	public void cancel_Edit(){
		temporaryTable = personalTable;
		isModified = false;
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
	
	public void setDay(Day today){
		for(int i=0; i< COL ;i++){
			findDay[i] = today;
			today = Day.getNextDay(today);
		}
	}
	
	public Day getday(int today) {
		
		return findDay[today];
		
	}
	public short[][] getPTable()
	{
		return personalTable;
	}
	
	public void setIDlist(String[] _id)
	{
		IDlist = _id;
	}
	
	public String[] getID_list(){
		
		return IDlist;
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
		if((t>>id)%2 == avail)
			return true;
		else
			return false;
	}
	
	public ArrayList<Integer> show_organized_table(int i, int j)
	{

		people = new ArrayList<Integer>();
		
		for(int k=personNum-1; k >= 0; k--)
			{
				if(check_schedule((organizedTable[i][j]), k)){
					people.add(1);
				}
				else {
					people.add(0);
				}
			}

		return people;
	}
	
}
