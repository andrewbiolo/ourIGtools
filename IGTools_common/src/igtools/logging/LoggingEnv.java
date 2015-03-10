package igtools.logging;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class LoggingEnv{
	private static LoggingEnv env = new LoggingEnv();
	private static final SimpleDateFormat defaultTimeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	
	private Set<ILogger> loggers = new HashSet<ILogger>();
	
	private LoggingEnv(){
		loggers.add(new ConsoleLogger());
	}
	
	
	public static String defaultTime(){
		return "["+ defaultTimeFormat.format(new Date())+"] ";
	}
	
	
	public static void addLogger(ILogger logger){
		env.loggers.add(logger);
	}
	
	public static void log(String s) {
		for(ILogger l : env.loggers){
			l.log(s);
		}
	}

	public static void log(Exception e) {
		for(ILogger l : env.loggers){
			l.log(e);
		}
	}
	
}
