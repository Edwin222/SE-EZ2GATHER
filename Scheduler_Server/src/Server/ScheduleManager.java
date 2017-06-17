package Server;

import java.util.*;
import common.*;

public class ScheduleManager {

	public final int DATENUM = 7;
	public final int MAXIDNUM = 8;
	public final int TIMENUM = 12;

	// IDpart instance
	private String ID[] = new String[MAXIDNUM];

	// common schedule
	private Day today;
	private Day[] days;
	private ArrayList<DaySchedule> commonSchedule;
	private short organizedFixedSchedule[][] = new short[TIMENUM][DATENUM];
	private short organizedSchedule[][] = new short[TIMENUM][DATENUM];

	// constructor
	public ScheduleManager(Day today) {
		
		commonSchedule = new ArrayList<DaySchedule>();
		this.today = today;
		initializing();
	}

	public ScheduleManager(String id[],Day today) {  
		commonSchedule = new ArrayList<DaySchedule>();
		ID = id;
		this.today = today;
		initializing();
	}

	/**************************************************************/
	/* initializing                       					      */
	/* input : Day type 변수                   							  */
	/* process : 현재날짜의 Day type을 받아 commonSchdule을 생성                  */
	/* return : none                     						  */
	/**************************************************************/
	public void initializing() {	// check today's day and initialize depends on it.

		days = new Day[7];
		Day cursor = today;
		
		for(int i=0;i<7;i++){
			days[i] = cursor;
			cursor = Day.getNextDay(cursor);
		}
		
		for(int k = 0;k < 7; k++)	
			commonSchedule.add(new DaySchedule(days[k]));
			
	}
 
	/**************************************************************/
	/* isIDexist         		           					      */
	/* input : id string값                   							  */
	/* process : id를 받아 현재 idList내애 있는지 확인	                  */
	/* return : idList애 있으면 id의 idx값, 없으면 -1반환				  */
	/**************************************************************/
	public int isIDexist(String id) {// check the ID is exist and return idx. if not, return -1;
										
		for (int i = 0; i < MAXIDNUM; i++) {
			if(ID[i] == null)
				continue;
			if (ID[i].equals(id))
				return i;
		}
		return -1;
	}

	/**************************************************************/
	/* Emptyidx		       		           					      */
	/* input : none                   							  */
	/* process : idList애 비어있는 idx가 있는지 확인	                  */
	/* return : 비어있는 idx가 있으면 idx값, 없으면 -1반환				  */
	/**************************************************************/
	public int Emptyidx() {// check the IDarr and find empty space idx. if it isn't exist, return -1;
						
		for (int i = 0; i < MAXIDNUM; i++) {
			if (ID[i]==null)
				return i;
		}
		return -1;
	}

	/************************************************************************************/
	/* makeID		       		           					   					   		*/
	/* input : id String값                   							 					 	*/
	/* process : 	길이가 10자 이상이거나 idList애 이미 id가 존재하면  err출력후 리턴				  	*/
	/* 				아니면 idList애 id를 추가하고 organizedSchedule, organizedFixedSchedule,   */
	/* 				commonSchedule의 id idx애 1값을 넣어준다.				 					*/
	/* return : none										  							*/
	/************************************************************************************/
	public void makeID(String id) {// Check the ID is exist and add ID in blank idx
				

		if (isIDexist(id) != -1) {
			System.out.println("ID is already exist.");
			return;
		}
		
		if (id.length() >= 10) {
			System.out.println("input less than 10 letters");
			return;
		}

		int i = Emptyidx();
		
		if (i != -1) {
			ID[i] = id;

			for (int j = 0; j < TIMENUM; j++)// initializing for ID in commonFixedSchedule.
				for (int k = 0; k < DATENUM; k++){
					organizedSchedule[j][k] = (short) (organizedFixedSchedule[j][k] + (1 << i));
					organizedFixedSchedule[j][k] = (short) (organizedFixedSchedule[j][k] + (1 << i));
				}
				
		} else
			System.out.println("cannot make ID anymore.");

		System.out.printf("ID registered : %s \n", id);
		updateCommonList();
	}
	
	/********************************************************************************************/
	/* deleteID		       		           					   					   				*/
	/* input : id String값                   							 					 			*/
	/* process :	idList애 해당 id가 있는지 확인 후 없으면 err출력후 리턴				  					*/
	/* 				없으면 idList애서 해당 id delete후  organizedSchedule,organizedFixedScheduleTable	*/
	/* 				commonSchedule의 해당 idx의 1값을 delete한다. 										*/
	/* return : none										  									*/
	/********************************************************************************************/
	public void deleteID(String id) {
		int IDNUM = isIDexist(id);
		if (IDNUM == -1) {
			System.out.println("not exist ID.");
			return;
		} else {
			//ID[IDNUM] = null;
			for (int i = IDNUM; i < ID.length - 1; i++) {
				ID[i] = ID[i + 1];
			}
			// delete existing elements.
			for(int i = 0; i < TIMENUM; i++)
				for(int j = 0 ; j < DATENUM; j++){
					organizedSchedule[i][j] = cleanID(organizedSchedule[i][j],IDNUM);
					organizedFixedSchedule[i][j] = cleanID(organizedFixedSchedule[i][j],IDNUM);
				}
		}
		updateCommonList();
		
		System.out.printf("ID deleted : %s \n", id);
	}

	
	//get&set function
	public void setID(String[] id){
		this.ID = id;
	}
	
	public String[] getID(){
		return this.ID;
	}
	
	public void setorganizedSchedule(short[][] organizedSchedule){
		this.organizedSchedule = organizedSchedule;
	}
	
	public short[][] getorganizedSchedule(){
		return this.organizedSchedule;
	}
	
	public void setorganizedFixedSchedule(short[][] organizedFixedSchedule){
		this.organizedFixedSchedule = organizedFixedSchedule;
	}
	
	public short[][] getorganizedFixedSchedule(){
		return this.organizedFixedSchedule;
	}
	
	public void setcommonSchedule(ArrayList<DaySchedule> commonSchedule){
		this.commonSchedule = commonSchedule;
	}
	
	public ArrayList<DaySchedule> getcommonSchedule(){
		return this.commonSchedule;
	}
	
	
	/************************************************************************************/
	/* updateSchedule		       		   					   					   		*/
	/* input : FixedScheduleUnit의 List, short형의 scheduleTable, 변경할 id의 idx			 	*/
	/* process : 	FixedScheduleUnitList는 updateFixedSchedule 호출하여 update	.		  	*/
	/* 				scheduleTable이용하여 현재 scheduleTable update.							*/
	/* 				update된 FixedSchedule, Schedule을 이용하여 commonList update.			*/
	/* return : none										  							*/
	/************************************************************************************/
	public void updateSchedule(ArrayList<FixedScheduleUnit> fsc, short sc[][], int IDidx, int personNum) {
																						   													 
		updateFixedSchedule(fsc, (personNum -1 - IDidx));

		for (int i = 0; i < TIMENUM; i++)
			for (int j = 0; j < DATENUM; j++)
				if (sc[i][j] == 0)
					organizedSchedule[i][j] = (short) (cleanID(organizedSchedule[i][j],(personNum -1 - IDidx)) + (short) (1 << (personNum -1 - IDidx)));
				else
					organizedSchedule[i][j] = (short) (cleanID(organizedSchedule[i][j],(personNum -1 - IDidx)));

		
		updateCommonList();
	}
	/************************************************************************************************/
	/* updateFixedSchedule		       		   					   					   				*/
	/* input : FixedScheduleUnit의 List, 변경할 id의 idx			 										*/
	/* process : 	FixedScheduleUnit List크기 확인 후 organizedFixedSchedule의 해당 id idx의 값을 모두 지운다.	*/
	/* 				그후 FixedScheduleUnit하나하나를 읽어오면서 해당 내용을 organizedFixedUnit애 업대이트			*/
	/* return : none										  										*/
	/************************************************************************************************/
	private void updateFixedSchedule(ArrayList<FixedScheduleUnit> fsc, int IDidx) {
		
		for(int i=0;i<fsc.size();i++){
			FixedScheduleUnit target = fsc.get(i);
			
			for(int j=0;j<DATENUM;j++){
				if(target.getDay() == days[j]){
					
					for(int k=target.getBegin(); k <= target.getEnd(); k++){
						organizedFixedSchedule[k][j] = (short) (cleanID(organizedFixedSchedule[k][j],IDidx));
					}
					
				}
			}
			
		}
		
		
	}

	/************************************************************************************/
	/* isFilledTime	       		   					   					   				*/
	/* input : short형 인자, id의 idx										 				*/
	/* process :	shot형 인자애서 id의 idx애 값이 있는지 확인	  								*/
	/* return : 값이 있으면 true, 없으면 false					  							*/
	/************************************************************************************/
	  private boolean isFilledTime(short t, int id)
	  { 
		  if( (t>>id)%2 == 1) 
			  return true; 
		  
		  else 
			  return false; 
	  }
	  
	/************************************************************************************/
	/* cleanID	       		   					   					   					*/
	/* input : short형 인자, id의 idx										 				*/
	/* process :	shot형 인자애서 id의 idx애 값이 있는지 확인후 있으면 지우고 없으면 그대로 반환			*/
	/* return : update된 short형 인자							  							*/
	/************************************************************************************/ 
	  private short cleanID(short sc,int id){ 
		  short result = sc;
		  result = (short) (result >> 1);
		  for (int i = 0; i < id; i++)
			  if(isFilledTime(sc,i))
				  result =  (short) (result | (1<<i));
		 // else
			//  return sc;
		  return result;
	  }
	 

	/************************************************************************************/
	/* updateCommonList	       					   					   					*/
	/* input : none														 				*/
	/* process :	현재 organizedSchedule을 받아 commonSchedule의 List update해준다.			*/
	/* return : none										  							*/
	/************************************************************************************/
	public void updateCommonList() {//find commonSchedule's day and set proper table.
		for (int i = 0; i < DATENUM; i++)
			for(int j = 0; j < TIMENUM; j++)
				commonSchedule.get(i).setScheduleUnit((short) (organizedSchedule[j][i] & organizedFixedSchedule[j][i]), j);
	}

	/************************************************************************************/
	/* updateCommonTable    					   					   					*/
	/* input : none														 				*/
	/* process :	현재 ArrayList type의 commonSchedule을 short형 2D Table로 변환				*/
	/* return : 변환된 short형 2D table							  							*/
	/************************************************************************************/
	public short[][] updateTable() {// let the commonSchedule's schedule copy in tmptable and return to make updatedSchedule(edited day.)
		
		short tmptable[][] = new short[TIMENUM][DATENUM];

		for (int i = 0; i < TIMENUM; i++)
			for (int j = 0; j < DATENUM; j++)
			tmptable[i][j] = commonSchedule.get(j).getSchedule()[i];

		return tmptable;
	}
	
	/****************************************************************************************/
	/* nextDay    					   					   									*/
	/* input : none														 					*/
	/* process :	commonSchedule의 마지막요일을 지운 후 맨처음 요일로 생성								*/
	/* 				마지막요일이 가지고있던 FixedSchedule 정보이용하여 맨처음요일의 정보 update				*/
	/* return : none										  								*/
	/****************************************************************************************/
	public void nextDay(){
		
		for(int i = 0; i < TIMENUM ; i++)
		switch(commonSchedule.get(6).getDay()){
		
		case MON : commonSchedule.add(0,new DaySchedule(Day.MON));
		commonSchedule.get(0).setScheduleUnit(organizedFixedSchedule[i][0],i);
		commonSchedule.remove(7);
		break;
		
		case TUE : commonSchedule.add(0,new DaySchedule(Day.TUE));
		commonSchedule.get(0).setScheduleUnit(organizedFixedSchedule[i][1],i);
		commonSchedule.remove(7);
		break;
		
		case WED : commonSchedule.add(0,new DaySchedule(Day.WED));
		commonSchedule.get(0).setScheduleUnit(organizedFixedSchedule[i][2],i);
		commonSchedule.remove(7);
		break;
		
		case THU : commonSchedule.add(0,new DaySchedule(Day.THU));
		commonSchedule.get(0).setScheduleUnit(organizedFixedSchedule[i][3],i);
		commonSchedule.remove(7);
		break;
		
		case FRI : commonSchedule.add(0,new DaySchedule(Day.FRI));
		commonSchedule.get(0).setScheduleUnit(organizedFixedSchedule[i][4],i);
		commonSchedule.remove(7);
		break;
		
		case SAT : commonSchedule.add(0,new DaySchedule(Day.SAT));
		commonSchedule.get(0).setScheduleUnit(organizedFixedSchedule[i][5],i);
		commonSchedule.remove(7);
		break;
		
		case SUN : commonSchedule.add(0,new DaySchedule(Day.SUN));
		commonSchedule.get(0).setScheduleUnit(organizedFixedSchedule[i][6],i);
		commonSchedule.remove(7);
		break;
		}
		
		
	}
	
	public Day getToday(){
		return commonSchedule.get(0).getDay();
	}
	
	public Day getLastday(){
		return commonSchedule.get(6).getDay();
	}

}