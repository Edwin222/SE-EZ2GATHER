package client;

public class Client {
	
	public static String ID;
	public static ScheduleTable MySchedule;
	
	Client(String _ID)
	{
		ID = _ID;
	}
	
	Client(ScheduleTable _MySchedule)
	{
		MySchedule = _MySchedule;
	}
	
	public void setClientID(String _ID)
	{
		ID = _ID;
	}
	
	public String getClientID()
	{
		return ID;
	}
	
	public void setScheduleTable(ScheduleTable _MySchedule)
	{
		MySchedule.table = _MySchedule.table;
	}
	
	public int[][] getScheduleTable() 
	{
		return MySchedule.table;
	}
}
