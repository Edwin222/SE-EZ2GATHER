package Server;

import java.util.Scanner;
import java.util.StringTokenizer;


public class MainConsole {
	
	public static void main(String args[]){

		ScheduleServer s = new ScheduleServer();
		Scanner scan = new Scanner(System.in);
		StringTokenizer st;

		String command;
		String order;
		String name = null;
		
		while(true){
			command = scan.nextLine();
			st = new StringTokenizer(command);
			
			order = st.nextToken();
			
			
			switch(order){
			case "add" :
				name = st.nextToken();
				s.makeID(name); break;
			case "delete" :
				name = st.nextToken();
				s.deleteID(name); break;
			case "notice" :
				while(st.hasMoreTokens()){
					name = name + " " + st.nextToken();
				}
				s.setNotice(name); break;
			case "printID" : s.ShowId(); break;
			case "help" : 	System.out.println("add <IDNAME> : add ID to IDLIST");
							System.out.println("delete <IDNAME> : delete ID from IDLIST");
							System.out.println("notice <NOTICE CONTENT> : set NOTICE");
							System.out.println("printID : print ID names in IDLIST ");
			default :
				System.out.println("잘못된 입력입니다.");
			}
		}
	}
}

/*
switch(order){
case "checkID" : if(s.checkID(name)) System.out.println(name); else System.out.println("check"); break;
case "checkNT" : System.out.println(s.getNotice()); break;
case "set" : s.setcommonSchedule(name, k2, k1); break;
case "show" : s.Screen(); break;
case "printID" : s.ShowId(); break;
case "nextDay" : s.nextDay(); break;
case "save" : s.saveData(); break;
case "clean" : s.cleanAll(); break;
case "load" : s.loadData(); break;
case "IDnum" : System.out.println(s.personNum()); break;
default :System.out.println("적절하지 않은 명령어 입니다.");
}
*/
