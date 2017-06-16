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
	private ArrayList<DaySchedule> commonSchedule;
	private short organizedFixedSchedule[][] = new short[TIMENUM][DATENUM];
	private short organizedSchedule[][] = new short[TIMENUM][DATENUM];

	// constructor
	public ScheduleManager(Day today) {
		commonSchedule = new ArrayList<DaySchedule>();
		initializing(today);
	}

	public ScheduleManager(String id[],Day today) {  
		commonSchedule = new ArrayList<DaySchedule>();
		ID = id;
		initializing(today);
	}

	/**************************************************************/
	/* initializing                       					      */
	/* input : Day type 변수                   							  */
	/* process : 현재날짜의 Day type을 받아 commonSchdule을 생성                  */
	/* return : none                     						  */
	/**************************************************************/
	public void initializing(Day today) {	// check today's day and initialize depends on it.
		int i;
		
		Day[] day = new Day[7];
		
		day[0] = Day.MON;
		day[1] = Day.TUE;
		day[2] = Day.WED;
		day[3] = Day.THU;
		day[4] = Day.FRI;
		day[5] = Day.SAT;
		day[6] = Day.SUN;
		
		for(i = 0;i < 7; i++)
			if(day[i] == today)
				break;
		
		for(int k = 0;k < 7; k++)	
			commonSchedule.add(new DaySchedule(day[(i+k)%7]));
			
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
			ID[IDNUM] = null;
			
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
	public void updateSchedule(ArrayList<FixedScheduleUnit> fsc, short sc[][], int IDidx) {
																						   													 
		updateFixedSchedule(fsc, IDidx);

		for (int i = 0; i < TIMENUM; i++)
			for (int j = 0; j < DATENUM; j++)
				if (sc[i][j] != 0)
					organizedSchedule[i][j] = (short) (cleanID(organizedSchedule[i][j],IDidx) + (short) (1 << IDidx));
				else
					organizedSchedule[i][j] = (short) (cleanID(organizedSchedule[i][j],IDidx));

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
		int Fsize;
		
		if(fsc != null)
		 Fsize = fsc.size();
		
		else
			return;
		
		for (int j = 0; j < TIMENUM; j++)
			for (int k = 0; k < DATENUM; k++)
				organizedFixedSchedule[j][k] = (short) (cleanID(organizedFixedSchedule[j][k],IDidx));
		
		for (int i = 0; i < Fsize; i++)
			switch (fsc.get(i).getDay()) {
			case MON:
				fillFixedSchedule(fsc.get(i), 0, IDidx);
				break;
			case TUE:
				fillFixedSchedule(fsc.get(i), 1, IDidx);
				break;
			case WED:
				fillFixedSchedule(fsc.get(i), 2, IDidx);
				break;
			case THU:
				fillFixedSchedule(fsc.get(i), 3, IDidx);
				break;
			case FRI:
				fillFixedSchedule(fsc.get(i), 4, IDidx);
				break;
			case SAT:
				fillFixedSchedule(fsc.get(i), 5, IDidx);
				break;
			case SUN:
				fillFixedSchedule(fsc.get(i), 6, IDidx);
				break;
			default:
				break;
			}
	}

	/************************************************************************************/
	/* fillFixedSchedule	       		   					   					   		*/
	/* input : FixedScheduleUnit, 요일 idx, id idx						 				*/
	/* process : 	FixedScheduleUnit의 내용을 읽어 해당 id애다가 넣어준다.		  				*/
	/* 				scheduleTable이용하여 현재 scheduleTable update.							*/
	/* 				update된 FixedSchedule, Schedule을 이용하여 commonList update.			*/
	/* return : none										  							*/
	/************************************************************************************/
	private void fillFixedSchedule(FixedScheduleUnit f, int Dayidx, int IDidx) {
		for (int i = f.getBegin(); i < f.getEnd() + 1; i++){
			organizedFixedSchedule[i][Dayidx] = (short) (cleanID(organizedFixedSchedule[i][Dayidx],IDidx) + (short) (1 << IDidx));
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

		  if(isFilledTime(sc,id))
			  return (short) (sc-(1<<id));
		  else
			  return sc;
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
			switch (commonSchedule.get(i).getDay()) {
			case MON:
				commonSchedule.get(i).setScheduleUnit(organizedSchedule[j][0],j);
				break;
			case TUE:
				commonSchedule.get(i).setScheduleUnit(organizedSchedule[j][1],j);
				break;
			case WED:
				commonSchedule.get(i).setScheduleUnit(organizedSchedule[j][2],j);
				break;
			case THU:
				commonSchedule.get(i).setScheduleUnit(organizedSchedule[j][3],j);
				break;
			case FRI:
				commonSchedule.get(i).setScheduleUnit(organizedSchedule[j][4],j);
				break;
			case SAT:
				commonSchedule.get(i).setScheduleUnit(organizedSchedule[j][5],j);
				break;
			case SUN:
				commonSchedule.get(i).setScheduleUnit(organizedSchedule[j][6],j);
				break;
			default:
				break;
			}
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