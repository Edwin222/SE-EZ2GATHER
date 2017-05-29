package Server;

import java.util.*;
import common.Day;
import common.FixedScheduleUnit;

public class ScheduleServer {
	public final int DATENUM = 7;
	public final int MAXIDNUM = 8;
	public final int TIMENUM = 12;
	public Date date = new Date();
	

	// Scanner
	Scanner scan = new Scanner(System.in);

	// Notice part instance
	private String Notice;
	private Day today;

	// Schedule part instance
	private ScheduleManager Schedule;
	private short[][] commonSchedule;

	///////////////////////////////////////// Date part
	///////////////////////////////////////// Methods///////////////////////////////////////////////
	public Day getDateDay() {

		Day day = null;
	    Calendar cal = Calendar.getInstance() ;

	    int dayNum = cal.get(Calendar.DAY_OF_WEEK) ;
	    
	     
	    switch(dayNum){
	        case 1:
	            day = Day.SUN;
	            break ;
	        case 2:
	            day = Day.MON;
	            break ;
	        case 3:
	            day = Day.TUE;
	            break ;
	        case 4:
	            day = Day.WED;
	            break ;
	        case 5:
	            day = Day.THU;
	            break ;
	        case 6:
	            day = Day.FRI;
	            break ;
	        case 7:
	            day = Day.SAT;
	            break ;
	             
	    }
	     
	    return day ;
	}
	
	
	///////////////////////////////////////// Notice part
	///////////////////////////////////////// Methods///////////////////////////////////////////////
	
	public ScheduleServer(){		

		this.today = getDateDay();
		Schedule = new ScheduleManager(today);

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
		Schedule.makeID(id);
	}

	public void deleteID(String id) {
		Schedule.deleteID(id);
	}

	public boolean checkID(String id){
		if(Schedule.isIDexist(id) != -1)
			return true;
		else return false;
	}
	
	///////////////////////////////////// Schedule part
	///////////////////////////////////// Methods///////////////////////////////////////////////////////
	public void setcommonSchedule(String id, ArrayList<FixedScheduleUnit> PersonalFixedSchedule, short[][] schedule) {

		int IDidx = Schedule.isIDexist(id);

		if (IDidx != -1){
			Schedule.updateSchedule(PersonalFixedSchedule, schedule, IDidx);
			Schedule.updateCommonList();
			updateCommonSchedule();
		}

		else {
			System.out.println("존재하지 않는 ID입니다.");
		}
	}
	
	public void updateCommonSchedule(){
		commonSchedule = Schedule.updateTable();
	}
	
	public void Screen(){
		updateCommonSchedule();
		
		for(int j = 0; j < TIMENUM; j++){
		for(int i = 0; i < DATENUM; i++)
			System.out.print(Integer.toBinaryString(commonSchedule[i][j])+" ");
			System.out.println();
		}
	}
	
	public void ShowId(){
		
		for(int i = 0; i < MAXIDNUM; i++)
			if(Schedule.getID()[i] != null)
				System.out.println(Schedule.getID()[i]);
	}
	
	public void nextDay(){
		Schedule.nextDay();
	}
	

}
