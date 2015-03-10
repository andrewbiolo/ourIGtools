package igtools.common.util;

public class AvgSD {

	
	public static double avg(double a[]){
		double avg = 0.0;
		for(int i=0; i<a.length; i++){
			avg += a[i];
		}
		return avg / ((double)a.length);
	}
	
	public static double avg(double a[], double thr){
		double avg = 0.0;
		double count = 0.0;
		for(int i=0; i<a.length; i++){
			if(a[i] > thr){
				avg += a[i];
				count++;
			}
		}
		return avg / count;
	}
	
	
	public static double sd(double[] a, double avg, double thr){
		double sd = 0.0;
		for(int i=0; i<a.length; i++){
			if(a[i] > thr){
				sd += (a[i] - avg) * (a[i] - avg);
			}
		}
		return Math.sqrt(sd / ((double) a.length));
	}
	public static double sd(double[] a, double avg){
		double sd = 0.0;
		for(int i=0; i<a.length; i++){
			sd += (a[i] - avg) * (a[i] - avg);
		}
		return Math.sqrt(sd / ((double) a.length));
	}
	public static double sd(double[] a){
		double avg = avg(a);
		return sd(a,avg);
	}
	
	//TODO others
}
