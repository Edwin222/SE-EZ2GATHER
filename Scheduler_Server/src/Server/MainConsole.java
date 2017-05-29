package Server;

import java.util.ArrayList;
import java.util.Scanner;

import common.Day;
import common.FixedScheduleUnit;

public class MainConsole {
	
	public static void main(String args[]){

		ScheduleServer s = new ScheduleServer();
		Scanner scan = new Scanner(System.in);
		String order;
		String name;
		short[][] k1 = {{1,0,0,1,0,1,1},{1,0,0,1,0,1,1},{1,0,0,1,0,1,1},{1,0,0,0,1,1,1},{1,0,0,1,0,1,1},{1,0,0,1,0,1,1},{1,0,0,1,0,1,1},
				{1,0,0,1,0,1,1},{1,0,0,1,0,1,1},{1,0,0,1,0,1,1},{1,0,0,1,0,0,1},{1,0,0,1,0,1,1}};
		ArrayList<FixedScheduleUnit> k2 = new ArrayList<FixedScheduleUnit>();
		k2.add(new FixedScheduleUnit(0,2,Day.MON));
		k2.add(new FixedScheduleUnit(2,8,Day.TUE));
		k2.add(new FixedScheduleUnit(4,9,Day.SAT));
		k2.add(new FixedScheduleUnit(4,6,Day.MON));
		
		while(true){
			order = scan.next();
			name = scan.next();
			
			switch(order){
			
			case "add" : s.makeID(name); break;
			case "delete" : s.deleteID(name); break;
			case "notice" : s.setNotice(name); break;
			case "checkID" : if(s.checkID(name)) System.out.println(name); else System.out.println("check"); break;
			case "checkNT" : System.out.println(s.getNotice()); break;
			case "set" : s.setcommonSchedule(name, k2, k1); break;
			case "show" : s.Screen(); break;
			case "showID" : s.ShowId(); break;
			case "nextDay" : s.nextDay(); break;
			case "save" : s.saveData(); break;
			case "clean" : s.cleanAll(); break;
			case "load" : s.loadData(); break;
			default :System.out.println("적절하지 않은 명령어 입니다.");
			}
		}

	}
	
	

}
