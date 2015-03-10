package igtools.analyses.toRemove;

import igtools.dictionaries.elsa.IELSAIterator;

import java.util.Map;
import java.util.TreeMap;

public class DistributionsGapCoefficients {

	
	
	public static double KullbackLeibler(Distribution a, Distribution b){
		double ret = 0.0;
		Double bValue;
		for(Map.Entry<Double,Double> entry : a.distribution.entrySet()){
			bValue = b.distribution.get(entry.getKey());
			if(bValue == null){
				//check it, theoretically
				ret += entry.getValue() * Math.log(entry.getValue());
			}
			else{
				ret += entry.getValue() * Math.log(entry.getValue() / bValue);
			}
		}
		return ret * -1.0;
	}
	
	
	
	
	public Distribution estimateGeometric(int[] distances){

        int[][] codists = igtools.analyses.toRemove.DistalRecurrence.co_recurrences_array(distances, false);
        
        double ep = 0.0;
		double n = 0.0;
		
        for(int i=0; i<codists[0].length; i++){
            ep += (double)codists[0][i] * (double)codists[1][i];
            n += (double)codists[1][i];
        }
        ep = n/ep;
        
        TreeMap<Double, Double> distr = new TreeMap<Double, Double>();
        for(int i=0; i<codists[0].length; i++){
        	distr.put((double)codists[0][i], Math.pow(1.0-ep, (double)codists[0][i]) * ep);
        }
        
        Distribution d = new Distribution();
        d.lazyAddDistribution(distr);
        
        return d;
	}
	
	
	
}
