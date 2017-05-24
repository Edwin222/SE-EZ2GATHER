package Server;

import java.util.*;
import common.Day;
import common.FixedScheduleUnit;

public class ScheduleServer {
	public final int DATENUM = 7;
	public final int MAXIDNUM = 8;
	public final int TIMENUM = 12;

	// Scanner
	Scanner scan = new Scanner(System.in);

	// Notice part instance
	private String Notice;
	private Day today;

	// Schedule part instance
	private ScheduleManager Schedule = new ScheduleManager();
	private short[][] commonSchedule;

	// private schedule
	private short[][] schedule = new short[DATENUM][TIMENUM];
	ArrayList<FixedScheduleUnit> PersonalFixedSchedule;

	///////////////////////////////////////// Notice part
	///////////////////////////////////////// Methods///////////////////////////////////////////////
	
	public ScheduleServer(){		
		for(int i = 0; Schedule.getToday() != today; i++)
			Schedule.nextDay();
	}
	
	public ScheduleServer(Day today){
		this.today = today;
		
		for(int i = 0; Schedule.getToday() != today; i++)
			Schedule.nextDay();
	}
	
	public String getNotice() {
		return Notice;
	}

	public boolean setNotice(String Notice) {// return if setNoice succeed.
		if (Notice.length() > 30) {
			System.out.println("30자를 초과하셨습니다. 다시 입력하여 주십시오");
			return false;
		} else {
			this.Notice = Notice;
			return true;
		}
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////

	///////////////////////////////// ID part
	///////////////////////////////// Methods////////////////////////////////////////////////////
	public void makeID(String id) {
		//String id = scan.nextLine();
		Schedule.makeID(id);
	}

	public void deleteID(String id) {
		//String id = scan.nextLine();
		Schedule.deleteID(id);
	}

	public boolean checkID(String id){
		if(Schedule.isIDexist(id) != -1)
			return true;
		else return false;
	}
	
	//public String IDList(){
	//	return Schedule.getID();
	//}
	///////////////////////////////////// Schedule part
	///////////////////////////////////// Methods///////////////////////////////////////////////////////
	public void setcommonSchedule(String id) {

		int IDidx = Schedule.isIDexist(id);

		if (IDidx != -1){
			Schedule.updateSchedule(PersonalFixedSchedule, schedule, IDidx);
			Schedule.updateCommonList();
		}

		else {
			System.out.println("존재하지 않는 ID입니다.");
		}
	}
	
	public void setCommonSchedule(){
		commonSchedule = Schedule.updateTable();
	}

}
