package igtools.logging;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConsoleLogger implements ILogger{
	SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	public ConsoleLogger(){
	}
	
	private String time(){
		return "["+ dt.format(new Date())+"] ";
	}

	@Override
	public void log(String s) {
		System.out.println(time() + s);
	}

	@Override
	public void log(Exception e) {
		System.out.println(time() + e);
		e.printStackTrace();
	}

}
