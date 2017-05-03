package Server;

import java.util.*;


public class ScheduleServer {

	//Scanner
	Scanner scan = new Scanner(System.in);
	
	//IDpart instance
	private final int MAXIDNUM = 6;
	private String ID[] = new String[MAXIDNUM];
	private int cur_idnum;
	
	//Notice part instance
	private String Notice;
	
	//Schedule part instance
	private ScheduleManager Schedule;
	
	//ID part Methods
	public int isIDexist(String id){
		for(int i = 0; i < cur_idnum ; i++){
			if(ID[i] == id)
				return i;
		}
		return -1;
	}	
	
	public void makeID(String id){
		if(isIDexist(id) != -1){
			System.out.println("�̹� �����ϴ� ID�Դϴ�.");
			return;
		} 
		if(cur_idnum < MAXIDNUM){
			ID[cur_idnum++] = id;
		}
		else 
			System.out.println("���̻� ID�� ������ �� �����ϴ�.");
	}
	
	public void deleteID(String id){
		int IDNUM = isIDexist(id);
		if(IDNUM == -1){
			System.out.println("�������� �ʴ� ID�Դϴ�.");
			return;
		}
		else{
			for(;IDNUM < MAXIDNUM; IDNUM++)
				ID[IDNUM] = ID[IDNUM+1];
		}
	}
	
	//Notice part Methods
	public String getNotice(){
		return Notice;
	}
	
	public void setNotice(String Notice){
		this.Notice = Notice;
	}
	
	//Schedule part Methods
	
	
}
