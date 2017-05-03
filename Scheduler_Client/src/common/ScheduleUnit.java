package common;

public class ScheduleUnit {
	private int timeBegin;
	private int timeEnd;
	
	public ScheduleUnit(){
		this(0,0);
	}
	
	public ScheduleUnit(int timeBegin, int timeEnd){
		setTimeBegin(timeBegin);
		setTimeEnd(timeEnd);
	}
	
	public int getBegin(){
		return timeBegin;
	}
	
	public int getEnd(){
		return timeEnd;
	}
	
	private int timeValidation(int t){
		if(t < 0)
			return 0;
		
		if(t > 11)
			return 11;
		
		return -1;
	}
	
	private void setTimeBegin(int t){
		int trigger = timeValidation(t);
		
		if(trigger < 0){
			timeBegin = t;
			
		} else {
			timeBegin = trigger;
		}
		
	}
	
	private void setTimeEnd(int t){
		int trigger = timeValidation(t);

		if(trigger < 0){
			timeEnd = t;
			
		} else {
			timeEnd = trigger;
		}
	}
}
