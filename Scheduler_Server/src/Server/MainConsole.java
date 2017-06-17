package Server;

import java.util.Scanner;
import java.util.StringTokenizer;


public class MainConsole {
	
	public static void main(String args[]){

		boolean isModified = false;
		NetServer.getInstance().openServer();
		
		Scanner scan = new Scanner(System.in);
		StringTokenizer st;

		String command;
		String order;
		String name = "";
		
		NetServer.getInstance().getServer().loadData();
		
		while(true){
			command = scan.nextLine();
			st = new StringTokenizer(command);
			
			order = st.nextToken();
			
			switch(order){
			case "add" :
				isModified = true;
				name = st.nextToken();
				NetServer.getInstance().getServer().makeID(name); break;
			case "delete" :
				isModified = true;
				name = st.nextToken();
				NetServer.getInstance().getServer().deleteID(name); break;
			case "notice" :
				isModified = true;
				name = st.nextToken();
				while(st.hasMoreTokens()){
					name = name + " " + st.nextToken();
				}
				NetServer.getInstance().getServer().setNotice(name); break;
			case "printID" : NetServer.getInstance().getServer().ShowId(); break;
			case "help" : 	System.out.println("add <IDNAME> : add ID to IDLIST");
							System.out.println("delete <IDNAME> : delete ID from IDLIST");
							System.out.println("notice <NOTICE CONTENT> : set NOTICE");
							System.out.println("printID : print ID names in IDLIST ");
							break;
			case "exit" : 
				if(isModified){
					System.out.print("Do you save your Data?(Y/N):");
					String cmd = scan.next();
					
					if(cmd.equals("Y")){
						NetServer.getInstance().getServer().saveData();
					}
				}
				NetServer.getInstance().closeServer();
				return;
			case "save":
				isModified = false;
				NetServer.getInstance().getServer().saveData(); break;
			case "screen" : NetServer.getInstance().getServer().Screen(); break;
			default :
				System.out.println("잘못된 입력입니다.");
			}
			
			if(order.equals("exit")) break;
		}
		
		return ;
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
