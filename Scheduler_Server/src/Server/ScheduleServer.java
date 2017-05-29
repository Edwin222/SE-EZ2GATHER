package Server;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
	
	//constructor
	public ScheduleServer(){		

		this.today = getDateDay();
		Schedule = new ScheduleManager(today);

	}

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


	public String getNotice() {
		return Notice;
	}

	public boolean setNotice(String Notice) {// return if setNoice succeed.
		if (Notice.length() > 30) {
			System.out.println("input less than 30 letters");
			return false;
		} else {
			this.Notice = Notice;
			return true;
		}
	}

	///////////////////////////////// ID part
	///////////////////////////////// Methods////////////////////////////////////////////////////
	public void makeID(String id) {
		Schedule.makeID(id);
		updateCommonSchedule();
	}

	public void deleteID(String id) {
		Schedule.deleteID(id);
		updateCommonSchedule();
	}

	public boolean checkID(String id){
		if(Schedule.isIDexist(id) != -1)
			return true;
		else return false;
	}
	
	public String[] getIdList(){
		return Schedule.getID();
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
			System.out.println("not exist ID.");
		}
	}
	
	public void updateCommonSchedule(){//change arrayList to 2D-array
		commonSchedule = Schedule.updateTable();
	}
	
	public short[][] getCommonSchedule(){
		updateCommonSchedule();
		return this.commonSchedule;
	}
	
	
	public void nextDay(){
		if(getDateDay() != today){
			Schedule.nextDay();
		}
		else
			return;
	}
	
	
	public void Screen(){
		
		for(int j = 0; j < TIMENUM; j++){
		for(int i = 0; i < DATENUM; i++)
			System.out.print(Integer.toBinaryString(commonSchedule[j][i])+" ");
			System.out.println();
		}
	}
	
	public void ShowId(){
		
		for(int i = 0; i < MAXIDNUM; i++)
			if(Schedule.getID()[i] != null)
				System.out.println(Schedule.getID()[i]);
	}
	
	//clean ALL
	   public void cleanAll(){
		   	Schedule.setID(null);
	        Schedule.setcommonSchedule(null);
	        Schedule.setorganizedFixedSchedule(null);
	        Schedule.setorganizedSchedule(null);
	        commonSchedule = null;
	         
	   }
	
	//functions related with file
	   public void saveData(){
		      try {
		         FileOutputStream fp = new FileOutputStream("data.bin");
		         ObjectOutputStream op = new ObjectOutputStream(fp);
		         
		         op.writeObject(Schedule.getID());
		         op.writeObject(Schedule.getcommonSchedule());
		         op.writeObject(Schedule.getorganizedFixedSchedule());
		         op.writeObject(Schedule.getorganizedSchedule());
		         op.writeObject(this.commonSchedule);
		         
		         op.close();
		      } catch(Exception e){
		         e.printStackTrace();
		      }
		   }
		   
	   public void loadData(){
		      try {
		         FileInputStream fp = new FileInputStream("data.bin");
		         ObjectInputStream op = new ObjectInputStream(fp);
		         
		         Schedule.setID((String[]) op.readObject());
		         Schedule.setcommonSchedule((ArrayList<DaySchedule>) op.readObject());
		         Schedule.setorganizedFixedSchedule((short[][]) op.readObject());
		         Schedule.setorganizedSchedule((short[][]) op.readObject());
		         commonSchedule = (short[][]) op.readObject();
		         
		         op.close();
		         
		      } catch(Exception e){
		         e.printStackTrace();
		      }
		   }
		   
	
}

