package Server;

import java.util.*;
import common.*;

public class ScheduleManager {

	public final int DATENUM = 7;
	public final int MAXIDNUM = 8;
	public final int TIMENUM = 12;
	// Object instance

	// IDpart instance
	private String ID[] = new String[MAXIDNUM];

	// common schedule
	private ArrayList<DaySchedule> commonSchedule = new ArrayList<DaySchedule>();
	private short organizedFixedSchedule[][] = new short[DATENUM][TIMENUM];
	private short organizedSchedule[][] = new short[DATENUM][TIMENUM];

	// constructor
	public ScheduleManager() {
		initializing();
	}

	public ScheduleManager(String id[]) {  
		ID = id;
		initializing();
	}

	//////////////////////////////////////// initializing/////////////////////////////////////////////////////////
	public void initializing() {
			commonSchedule.add(new DaySchedule(Day.MON));
			commonSchedule.add(new DaySchedule(Day.TUE));
			commonSchedule.add(new DaySchedule(Day.WED));
			commonSchedule.add(new DaySchedule(Day.THU));
			commonSchedule.add(new DaySchedule(Day.FRI));
			commonSchedule.add(new DaySchedule(Day.SAT));
			commonSchedule.add(new DaySchedule(Day.SUN));
	}

	////////////////////////////////////// ID part
	////////////////////////////////////// Methods///////////////////////////////////////////////////////
	public int isIDexist(String id) {// check the ID is exist and return idx. if
										// not, return -1;
		for (int i = 0; i < MAXIDNUM; i++) {
			if (ID[i] == id)
				return i;
		}
		return -1;
	}

	public int Emptyidx() {// check the IDarr and find empty space idx. if it
							// isn't exist, return -1;
		for (int i = 0; i < MAXIDNUM; i++) {
			if (ID[i] == null)
				return i;
		}
		return -1;
	}

	public void makeID(String id) {// Check the ID is exist and add ID in blank
				// idx.

		if (isIDexist(id) != -1) {
			System.out.println("�̹� �����ϴ� ID�Դϴ�.");
			return;
		}
		
		int i = Emptyidx();
		
		if (i != -1) {
			ID[i] = id;
/*
			for (int j = 0; j < DATENUM; j++)// initializing for ID in
												// commonFixedSchedule.
				for (int k = 0; k < TIMENUM; k++){
					organizedFixedSchedule[j][k] = (short) (organizedFixedSchedule[j][k] + (1 << i));
				}
	*/			
		} else
			System.out.println("���̻� ID�� ������ �� �����ϴ�.");
	}
	

	public void deleteID(String id) {
		int IDNUM = isIDexist(id);
		if (IDNUM == -1) {
			System.out.println("�������� �ʴ� ID�Դϴ�.");
			return;
		} else {
			ID[IDNUM] = null;
			
			// delete existing elements.
			for(int i = 0; i < DATENUM; i++)
				for(int j = 0 ; j < TIMENUM; j++){
					organizedSchedule[i][j] = cleanID(organizedSchedule[i][j],IDNUM);
					organizedFixedSchedule[i][j] = cleanID(organizedFixedSchedule[i][j],IDNUM);
					updateCommonList();
				}
					
			
			
		}
	}
	
	public String[] getID(){
		return this.ID;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////// Update
	////////////////////////////////////// commonTable////////////////////////////////////////////////////////
	public void updateSchedule(ArrayList<FixedScheduleUnit> fsc, short sc[][], int IDidx) {// get
																								// ID
																								// idx
																								// and
																								// sc
																								// table.
																								// update organizedFixedSchedule and organizedSchedule.
		updateFixedSchedule(fsc, IDidx);

		for (int i = 0; i < DATENUM; i++)
			for (int j = 0; j < TIMENUM; j++)
				if (sc[i][j] != 0)
					organizedSchedule[i][j] = (short) (cleanID(organizedSchedule[i][j],IDidx) + (short) (1 << IDidx));
	}

	private void updateFixedSchedule(ArrayList<FixedScheduleUnit> fsc, int IDidx) {
		int Fsize = fsc.size();
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

	private void fillFixedSchedule(FixedScheduleUnit f, int Dayidx, int IDidx) {// get FixedSchedule and 
		for (int i = f.getBegin(); i < f.getEnd() + 1; i++){
			
			organizedFixedSchedule[Dayidx][i] = (short) (cleanID(organizedFixedSchedule[Dayidx][i],IDidx) + (short) (1 << IDidx));
		}
	}
	
	  private boolean isFilledTime(short t, int id)
	  { 
		  if( (t>>id)%2 == 1) 
			  return true; 
		  
		  else 
			  return false; 
	  }
	  
	  private short cleanID(short sc,int id){ 

		  if(isFilledTime(sc,id))
			  return (short) (sc-(1<<id));
		  else
			  return sc;
	  }
	 
	///////////////////////////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////// Update common
	/////////////////////////////////// List////////////////////////////////////////////
	public void updateCommonList() {////////////// find
												////////////// commonSchedule's
												////////////// day and set
												////////////// proper table.
		for (int i = 0; i < 7; i++)
			switch (commonSchedule.get(i).getDay()) {
			case MON:
				commonSchedule.get(i).setSchedule(organizedSchedule[0]);
				break;
			case TUE:
				commonSchedule.get(i).setSchedule(organizedSchedule[1]);
				break;
			case WED:
				commonSchedule.get(i).setSchedule(organizedSchedule[2]);
				break;
			case THU:
				commonSchedule.get(i).setSchedule(organizedSchedule[3]);
				break;
			case FRI:
				commonSchedule.get(i).setSchedule(organizedSchedule[4]);
				break;
			case SAT:
				commonSchedule.get(i).setSchedule(organizedSchedule[5]);
				break;
			case SUN:
				commonSchedule.get(i).setSchedule(organizedSchedule[6]);
				break;
			default:
				break;
			}
	}

	//////////////////////////////// make Updated table(List -> new
	//////////////////////////////// Table)///////////////////////////////////////////////////////////
	public short[][] updateTable() {// let the commonSchedule's schedule copy in
									// tmptable and return to make
									// updatedSchedule(edited day.)
		short tmptable[][] = new short[DATENUM][TIMENUM];

		for (int i = 0; i < 7; i++)
			tmptable[i] = commonSchedule.get(i).getSchedule();

		return tmptable;
	}
	
	///////////////////////////// make commonSchedule next day
	//////////////////////////////////////////////////////////////////////////////////////////////////
	public void nextDay(){
		
		switch(commonSchedule.get(6).getDay()){
		
		case MON : commonSchedule.add(0,new DaySchedule(Day.MON));
		commonSchedule.get(0).setSchedule(organizedFixedSchedule[0]);
		commonSchedule.remove(7);
		break;
		
		case TUE : commonSchedule.add(0,new DaySchedule(Day.TUE));
		commonSchedule.get(0).setSchedule(organizedFixedSchedule[1]);
		commonSchedule.remove(7);
		break;
		
		case WED : commonSchedule.add(0,new DaySchedule(Day.WED));
		commonSchedule.get(0).setSchedule(organizedFixedSchedule[2]);
		commonSchedule.remove(7);
		break;
		
		case THU : commonSchedule.add(0,new DaySchedule(Day.THU));
		commonSchedule.get(0).setSchedule(organizedFixedSchedule[3]);
		commonSchedule.remove(7);
		break;
		
		case FRI : commonSchedule.add(0,new DaySchedule(Day.FRI));
		commonSchedule.get(0).setSchedule(organizedFixedSchedule[4]);
		commonSchedule.remove(7);
		break;
		
		case SAT : commonSchedule.add(0,new DaySchedule(Day.SAT));
		commonSchedule.get(0).setSchedule(organizedFixedSchedule[5]);
		commonSchedule.remove(7);
		break;
		
		case SUN : commonSchedule.add(0,new DaySchedule(Day.SUN));
		commonSchedule.get(0).setSchedule(organizedFixedSchedule[6]);
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
