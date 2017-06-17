package common;

public enum Day { 
	MON, TUE, WED, THU, FRI, SAT, SUN;
	
	public static Day getNextDay(Day d){
		
		switch(d){
		case MON:
			return TUE;

		case TUE:
			return WED;
			
		case WED:
			return THU;

		case THU:
			return FRI;
			
		case FRI:
			return SAT;

		case SAT:
			return SUN;

		case SUN:
			return MON;

		}
		
		return null;
	}
}

