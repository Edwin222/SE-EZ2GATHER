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
		for (int i = 0; i < DATENUM; i++)
			commonSchedule.add(new DaySchedule());
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
			System.out.println("이미 존재하는 ID입니다.");
			return;
		}
		int i = Emptyidx();
		if (i != -1) {
			ID[i] = id;

			for (int j = 0; j < DATENUM; j++)// initializing for ID in
												// commonFixedSchedule.
				for (int k = 0; k < TIMENUM; k++)
					organizedFixedSchedule[j][k] = (short) (organizedFixedSchedule[j][k] + 1 << i);
		} else
			System.out.println("더이상 ID를 생성할 수 없습니다.");
	}

	public void deleteID(String id) {
		int IDNUM = isIDexist(id);
		if (IDNUM == -1) {
			System.out.println("존재하지 않는 ID입니다.");
			return;
		} else {
			ID[IDNUM] = null;
			// delete existing elements.
		}
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////

	////////////////////////////////////// Update
	////////////////////////////////////// commonTable////////////////////////////////////////////////////////
	public void updateSchedule(ArrayList<FixedScheduleUnit> fsc, short sc[][], int IDidx) { // get
																							// ID
																							// idx
																							// and
																							// sc
																							// table.
		updateFixedSchedule(fsc, IDidx);

		for (int i = 0; i < DATENUM; i++)
			for (int j = 0; j < TIMENUM; j++)
				if (sc[i][j] != 0)
					organizedSchedule[i][j] = (short) (1 << IDidx);
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

	private void fillFixedSchedule(FixedScheduleUnit f, int Dayidx, int IDidx) {
		for (int i = f.getBegin(); i < f.getEnd() + 1; i++)
			organizedFixedSchedule[0][i] = (short) (1 << IDidx);
	}
	/*
	 * private boolean isFilledTime(short t, int id){ if(t>>id == 1) return
	 * true; else return false; }
	 */
	///////////////////////////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////// Update common
	/////////////////////////////////// List////////////////////////////////////////////
	public void updateCommonList(short sc[][]) {////////////// find
												////////////// commonSchedule's
												////////////// day and set
												////////////// proper table.
		for (int i = 0; i < 7; i++)
			switch (commonSchedule.get(i).getDay()) {
			case MON:
				commonSchedule.get(i).setSchedule(sc[0]);
				break;
			case TUE:
				commonSchedule.get(i).setSchedule(sc[1]);
				break;
			case WED:
				commonSchedule.get(i).setSchedule(sc[2]);
				break;
			case THU:
				commonSchedule.get(i).setSchedule(sc[3]);
				break;
			case FRI:
				commonSchedule.get(i).setSchedule(sc[4]);
				break;
			case SAT:
				commonSchedule.get(i).setSchedule(sc[5]);
				break;
			case SUN:
				commonSchedule.get(i).setSchedule(sc[6]);
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

}
