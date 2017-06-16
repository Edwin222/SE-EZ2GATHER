package Server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
	private ScheduleManager Schedule;
	private short[][] commonSchedule;
	
	//constructor
	public ScheduleServer(){		

		this.today = getDateDay();
		Schedule = new ScheduleManager(today);

	}
	
	/****************************************************************************************/
	/* getDateDay    					   													*/
	/* input : none														 					*/
	/* process :	오늘날짜정보를 이용하여 Day type을 찾기.											*/
	/* return : 오늘날짜의 Day type								  								*/
	/****************************************************************************************/
	private Day getDateDay() {// find today.

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
	
	public short[][] getCommonSchedule(){
		updateCommonSchedule();
		return this.commonSchedule;
	}
	
	public String[] getIdList(){
		return Schedule.getID();
	}
	
	public String getNotice() {
		return this.Notice;
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

	/************************************************************************************/
	/* makeID    					   													*/
	/* input : String id														 		*/			
	/* process : 	ScheduleManager의 makeID사용하여 ID생성 후 updateCommonSchedule이용하여		*/
	/* 				update된 내용 유지.														*/
	/* return : none										  							*/	
	/************************************************************************************/
	public void makeID(String id) {
		Schedule.makeID(id);
		updateCommonSchedule();
	}

	/************************************************************************************/
	/* deleteID    					   													*/
	/* input : String id														 		*/			
	/* process : 	ScheduleManager의 deleteID사용하여 ID생성 후 updateCommonSchedule이용하여	*/
	/* 				update된 내용 유지.														*/
	/* return : none										  							*/	
	/************************************************************************************/
	public void deleteID(String id) {
		Schedule.deleteID(id);
		updateCommonSchedule();
	}
	
	/************************************************************************************/
	/* personNum    					   												*/
	/* input : none																 		*/			
	/* process : 	Schedule의 idList이용하여 등록된 client 수 확인								*/
	/* return : 등록된 client 수 반환							  							*/	
	/************************************************************************************/
	public int personNum(){
		int num = 0;
		for(int i = 0; i < MAXIDNUM ; i++)
			if(Schedule.getID()[i] != null)
				num++;
		return num;
	}
	
	/********************************************************************************************/
	/* setcommonSchedule				   														*/
	/* input : String id, FixedScheduleUnit의 arrayList, short형 2-D scheduleTable				*/			
	/* process : id를 확인하여 id가 등록 되어 있으면 Schedule내의 organized,organizedFixed,commonSchedule	*/
	/*			 을 update해 준다음  updateCommonSchedule이용하여 update된 내용 유지						*/
	/* return : none										  									*/	
	/********************************************************************************************/
	public void setcommonSchedule(String id, ArrayList<FixedScheduleUnit> PersonalFixedSchedule, short[][] schedule) {

		int IDidx = Schedule.isIDexist(id);

		if (IDidx != -1){
			Schedule.updateSchedule(PersonalFixedSchedule, schedule, IDidx);
			updateCommonSchedule();
		}

		else {
			System.out.println("not exist ID.");
		}
	}
	
	/****************************************************************************************/
	/* updateCommonSchedule				   													*/
	/* input : none																			*/			
	/* process : ScheduleManager의 updateTable이용하여 현재 class의 commonSchedule정보 update		*/
	/* return : none										  								*/	
	/****************************************************************************************/
	public void updateCommonSchedule(){//change arrayList to 2D-array
		commonSchedule = Schedule.updateTable();
	}
	
	/****************************************************************************************/
	/* nextDay				   																*/
	/* input : none																			*/			
	/* process : 현재날짜를 검사 후 요일 다르면 	같은 요일이 나올 때 까지 ScheduleManager의 nextDay 호출.		*/
	/* return : none										  								*/	
	/****************************************************************************************/
	public void nextDay(){
		Day tmpDay = getDateDay();
		
		while(tmpDay != today){
		
			this.today = tomorrow(today);
			Schedule.nextDay();
			
		}
			
			return;
	}
	
	public Day tomorrow(Day today){
		switch(today){
		case MON : return Day.TUE;
		case TUE : return Day.WED;
		case WED : return Day.THU;
		case THU : return Day.FRI;
		case FRI : return Day.SAT;
		case SAT : return Day.SUN;
		case SUN : return Day.MON;
		}
		
		return today;
	}
	
	/********************************************************************************************/
	/* saveData		   																			*/
	/* input : none																				*/			
	/* process : Schedule의 IDList, organized,organizedFixed,commonSchedule arrayList정보, 		*/
	/* 			 class의 commonSchedule을 file로 저장												*/
	/* return : none										  									*/	
	/********************************************************************************************/
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
		   
	/********************************************************************************************/
	/* loadData		   																			*/
	/* input : none																				*/			
	/* process : 저장된 IDList, organized,organizedFixed,commonSchedule arrayList정보,				*/
	/* 			 class의 commonSchedule을 file로부터 불러내어 setting.									*/
	/* return : none										  									*/	
	/********************************************************************************************/
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
		         
		      } 
		      catch(FileNotFoundException e){
		      }
		      catch(Exception e){
			     e.printStackTrace();
		      }
		   }
	   
	   /*
	    * methods for check if methods are successfully operated. 
	    */
		public void Screen(){ // print current Table
			
			for(int j = 0; j < TIMENUM; j++){
			for(int i = 0; i < DATENUM; i++)
				System.out.print(Integer.toBinaryString(commonSchedule[j][i])+" ");
				System.out.println();
			}
		}
		
		public void ShowId(){ //print current ID list.
			
			for(int i = 0; i < MAXIDNUM; i++)
				if(Schedule.getID()[i] != null)
					System.out.println(Schedule.getID()[i]);
		}
		
	    public void cleanAll(){
			   	Schedule.setID(null);
		        Schedule.setcommonSchedule(null);
		        Schedule.setorganizedFixedSchedule(null);
		        Schedule.setorganizedSchedule(null);
		        commonSchedule = null;
		}

		public boolean checkID(String id){
				if(Schedule.isIDexist(id) != -1)
					return true;
				else return false;
		}
			
}

