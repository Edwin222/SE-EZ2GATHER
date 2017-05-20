package client;

import common.FixedScheduleUnit;
import common.ScheduleUnit;
import java.util.ArrayList;

enum Day { MON, TUE, WED, THU, FRI, SAT, SUN }

public class UserManager {
	
	public static final short BLANK = 0; //no schedule
	public static final short UNFIXED = 1; //unfixed schedule
	public static final short FIXED = 2; //fixed schedule

	private String ID;
	private Day lastDay;
	
	
	ArrayList<FixedScheduleUnit> FixedSchedule = new ArrayList<>();
	private ScheduleUnit Schedule;
	private Client client;
	
	public void add_Schedule(ScheduleUnit schedule, int start_pos, int end_pos) {
		
		int begin_time = schedule.getBegin();
		int end_time = schedule.getEnd();
		
		for(int i = begin_time; i< end_time; i++) {
			
			for(int j = start_pos; j<end_pos; j++) {
				client.MySchedule.table[i][j] = UNFIXED;
			}
		}
		
	}
	
	public void remove_Schedule(ScheduleUnit schedule, int start_pos, int end_pos) {
		
		int begin_time = schedule.getBegin();
		int end_time = schedule.getEnd();
		
		for(int i = begin_time; i< end_time; i++) {
			
			for(int j = start_pos; j<end_pos; j++) {
				client.MySchedule.table[i][j] = BLANK;
			}
		}
		
	}
	
	public void add_FixedSchedule(FixedScheduleUnit schedule, int start_pos, int end_pos) {
		
		//���� �����ٸ�Ͽ� ���ϱ�
		int begin_time = schedule.getBegin();
		int end_time = schedule.getEnd();
		
		for(int i = begin_time; i< end_time; i++) {
			
			for(int j = start_pos; j<end_pos; j++) {
				client.MySchedule.table[i][j] = FIXED;
			}
		}
		
		FixedSchedule.add(schedule); //���������ٸ�Ͽ� ���ϱ�
		
	}
	
	public void remove_FixedSchedule(FixedScheduleUnit schedule, int start_pos, int end_pos) 
	{	
		//���� �����ٸ�Ͽ� ����
		int begin_time = schedule.getBegin();
		int end_time = schedule.getEnd();
		
		for(int i = begin_time; i< end_time; i++) {
			
			for(int j = start_pos; j<end_pos; j++) {
				client.MySchedule.table[i][j] = BLANK;
			}
		}
		
		FixedSchedule.remove(schedule); //���������ٸ�Ͽ��� ����
	}
	
	public Day next_Day() {
		
		return lastDay;
	}
	
	public short[][] save(short[][] newtable) { //����� ���������̺��� ���� ���̺� ������

		client.MySchedule.table = newtable;
		
		return client.MySchedule.table;
	}
	
	public void ModifySchedule()
	{
		short[][] temp = new short[12][7];
		
		temp = client.MySchedule.table;
		//����. ������°� �ɴ���� ������ ����
		
		//���� ������ save(temp);
	}

}
