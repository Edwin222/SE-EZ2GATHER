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

	// Schedule part instance
	private ScheduleManager Schedule;

	// private schedule
	private short schedule[][] = new short[DATENUM][TIMENUM];
	ArrayList<FixedScheduleUnit> PersonalFixedSchedule;

	///////////////////////////////////////// Notice part
	///////////////////////////////////////// Methods///////////////////////////////////////////////
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
	public void makeID() {
		String id = scan.nextLine();
		Schedule.makeID(id);
	}

	public void deleteID() {
		String id = scan.nextLine();
		Schedule.deleteID(id);
	}

	///////////////////////////////////// Schedule part
	///////////////////////////////////// Methods///////////////////////////////////////////////////////
	public void setcommonSchedule(String id) {

		int IDidx = Schedule.isIDexist(id);

		if (IDidx != -1)
			Schedule.updateSchedule(PersonalFixedSchedule, schedule, IDidx);

		else {
			System.out.println("존재하지 않는 ID입니다.");
		}
	}

}
