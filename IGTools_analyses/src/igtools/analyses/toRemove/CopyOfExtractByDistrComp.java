package igtools.analyses.toRemove;

import java.util.Map;
import java.util.TreeMap;

import igtools.analyses.random.Random;
import igtools.common.nucleotide.B3Nucleotide;
import igtools.common.sequence.B3Sequence;
import igtools.common.util.Pair;
import igtools.dictionaries.elsa.IELSAIterator;
import igtools.dictionaries.elsa.INELSA;
import igtools.dictionaries.elsa.NELSA;

public class CopyOfExtractByDistrComp {

	public enum DISTR_TYPE{
		LENGTH, 			// k
		MULTIPLICITY, 		// m
		COMPOSED, 			// k + m
		COMBINED				// (k,m)
	};
	
	
	private B3Sequence iSeq;
	private INELSA iNelsa;
	
	private B3Sequence randSeq;
	private INELSA randNelsa;
	
	//private DISTR_TYPE dtype = DISTR_TYPE.COMPOSED;
	
	
	//private TreeMap<Integer,Integer> k_minMult; 
	////private int[][] k_minMult; //example  a[0][0] = k  a[0][1] = min multiplicity 
	
	
	public  CopyOfExtractByDistrComp(B3Sequence iSeq, INELSA iNelsa){
		this.iSeq = iSeq;
		this.iNelsa = iNelsa;
		
		//this.dtype = dtype;
		//this.k_minMult = k_minMult;
		
	}
	
	public void generateRandomSequence(){
		if(randSeq == null){
			System.out.println("Generating random sequence...");
			randSeq = Random.generateUniform(iSeq.length());
			System.out.println("Reputting Ns on it...");
			for(int i=0; i<iSeq.length(); i++){
				if(iSeq.getB3(i) == B3Nucleotide.N_CODE)
					randSeq.setB3(i, B3Nucleotide.N_CODE);
			}
		}
		if(randNelsa == null){
			System.out.println("Computing its NELSA...");
			randNelsa = new NELSA(randSeq);
			System.out.println("done.");
		}
	}
	
	
	
	public interface WordListener{
		public void selected(B3Nucleotide[] word);
	}
	
	
	private void normalizeByK(double[] values, double k){
		for(int i=0; i<values.length; i++){
			values[i] /= k;
		}
	}
	
	
	
	
	private TreeMap<Integer,Distribution> byLengthDistr = new TreeMap<Integer, Distribution>();
	private TreeMap<Integer,Distribution> byMultDistr = new TreeMap<Integer, Distribution>();
	private TreeMap<Pair<Integer,Integer>,Distribution> combinedDistr = new TreeMap<Pair<Integer,Integer>, Distribution>();
	
	
	public Distribution getCachedByLengthDistr(INELSA nelsa, Integer k, boolean normalizeByK, boolean normalizeByMax){
		Distribution distr = byLengthDistr.get(k);
		if(distr == null){
			distr = getByLengthDistr(nelsa, k, normalizeByK, normalizeByMax);
			byLengthDistr.put(k, distr);
		}
		return distr;
	}
	public Distribution getByLengthDistr(INELSA nelsa, Integer k, boolean normalizeByK, boolean normalizeByMax){
		//Distribution distr = byLengthDistr.get(k);
		//if(distr == null){
			Distribution distr = new Distribution();
			IELSAIterator it = nelsa.begin(k);
			while(it.next()){
				double[] dists = DistalRecurrence.double_proper_codistances(it);
				if(normalizeByK)
					normalizeByK(dists, k);
				distr.lazyAddDistribution(dists);
			}
			distr.flushLazyOperations();
			if(normalizeByMax)
				distr.normalizeByMax();
			//byLengthDistr.put(k, distr);
		//}
		return distr;
	}
	
	
	public Distribution getCachedByMultDistr(INELSA nelsa, Integer m, Double deltaPerc, boolean normalizeByK, boolean normalizeByMax){
		Distribution distr = byMultDistr.get(m);
		if(distr == null){
			distr = getByMultDistr(nelsa, m, deltaPerc, normalizeByK, normalizeByMax);
			byMultDistr.put(m, distr);
		}
		return distr;
	}
	public Distribution getByMultDistr(INELSA nelsa, Integer m, Double deltaPerc, boolean normalizeByK, boolean normalizeByMax){
		//Distribution distr = byMultDistr.get(m);
		//if(distr == null){
			double m_low = m-(m*deltaPerc);
			double m_high = m+(m*deltaPerc);
			Distribution distr = new Distribution();
			int k = 1;
			boolean found = true;
			while(found == true){
				IELSAIterator it = nelsa.begin(k);
				Double it_m;
				while(it.next()){
					it_m = (double)it.multiplicity();
					if(it_m>=m_low && it_m<=m_high){
						found = true;
						double[] dists = DistalRecurrence.double_proper_codistances(it);
						if(normalizeByK)
							normalizeByK(dists, k);
						distr.lazyAddDistribution(dists);
					}
				}
			}
			distr.flushLazyOperations();
			if(normalizeByMax)
				distr.normalizeByMax();
			//byMultDistr.put(k, distr);
		//}
		return distr;
	}
	
	public Distribution getCachedCombinedDistr(INELSA nelsa, Integer k, Integer m, Double deltaPerc, boolean normalizeByK, boolean normalizeByMax){
		Distribution distr = combinedDistr.get(new Pair<Integer,Integer>(k,m));
		if(distr == null){
			distr = getCombinedDistr(nelsa, k, m, deltaPerc, normalizeByK, normalizeByMax);
			combinedDistr.put(new Pair<Integer,Integer>(k,m), distr);
		}
		return distr;
	}
	public Distribution getCombinedDistr(INELSA nelsa, Integer k, Integer m, Double deltaPerc, boolean normalizeByK, boolean normalizeByMax){
		//Distribution distr = combinedDistr.get(new Pair<Integer,Integer>(k,m));
		//if(distr == null){
			double m_low = m-(m*deltaPerc);
			double m_high = m+(m*deltaPerc);
			Distribution distr = new Distribution();
			IELSAIterator it = nelsa.begin(k);
			Double it_m;
			while(it.next()){
				it_m = (double)it.multiplicity();
				if(it_m>=m_low && it_m<=m_high){
					double[] dists = DistalRecurrence.double_proper_codistances(it);
					if(normalizeByK)
						normalizeByK(dists, k);
					distr.lazyAddDistribution(dists);
				}
			}
			distr.flushLazyOperations();
			if(normalizeByMax)
				distr.normalizeByMax();
			//combinedDistr.put(new Pair<Integer,Integer>(k,m), distr);
		//}
		return distr;
	}
	
	
	public Distribution getDistribution(IELSAIterator it, boolean normalizeByK, boolean normalizeByMax){
		double[] dists = DistalRecurrence.double_proper_codistances(it);
		if(normalizeByK)
			normalizeByK(dists, (double)it.k());
		Distribution itDistr = new Distribution();
		itDistr.lazyAddDistribution(dists);
		if(normalizeByMax)
			itDistr.normalizeByMax();
		return itDistr;
	}
	
	public void run(WordListener listener, DISTR_TYPE dtype, TreeMap<Integer,Integer> k_minMult, boolean normalizeByK, boolean normalizeByMax){
		generateRandomSequence();
		
		byLengthDistr.clear();
		byMultDistr.clear();
		combinedDistr.clear();
		
		
		for(Map.Entry<Integer,Integer> entry : k_minMult.entrySet()){
			int k = entry.getKey();
			int minMult = entry.getValue();
			
			
			int m;
			IELSAIterator it = iNelsa.begin(k);
		
			while(it.next()){
				m = it.multiplicity();
				if(m >= minMult){
					
					//get it distance distribution
					Distribution itDistr = getDistribution(it, normalizeByK, normalizeByMax);
					
					//get rand distance distribution  for  k
					//get rand distance distribution  for  m
					//get rand distance distribution  for  (k,m)
					
					Distribution lengthDistr = null;
					Distribution multDistr = null;
					Distribution combinedDistr = null;
					
					
					if(dtype == DISTR_TYPE.LENGTH || dtype == DISTR_TYPE.COMPOSED){
						lengthDistr = getCachedByLengthDistr(randNelsa, k, normalizeByK, normalizeByMax);
					}
					if(dtype == DISTR_TYPE.MULTIPLICITY || dtype == DISTR_TYPE.COMPOSED){
						multDistr = getCachedByMultDistr(randNelsa, m, 0.0, normalizeByK, normalizeByMax);
					}
					if(dtype == DISTR_TYPE.COMBINED){
						combinedDistr = getCachedCombinedDistr(randNelsa, k, m, 0.0, normalizeByK, normalizeByMax);
					}
					
					//compare
					Double compValue = 0.0;
					
					if(dtype == DISTR_TYPE.LENGTH){
						compValue = simpleCompare(itDistr, lengthDistr);
					}
					else if(dtype == DISTR_TYPE.MULTIPLICITY){
						compValue = simpleCompare(itDistr, multDistr);
					}
					else if(dtype == DISTR_TYPE.COMPOSED){
						compValue = composedCompare(itDistr, lengthDistr, multDistr);
					}
					if(dtype == DISTR_TYPE.COMBINED){
						compValue = simpleCompare(itDistr, combinedDistr);
					}
					
					
				}
			}
		}
		
	}
	
	
	public double simpleCompare(Distribution a, Distribution b){
		double ret = 0.0;
		
		Double bValue;
		for(Map.Entry<Double,Double> entry : a.distribution.entrySet()){
			bValue = b.distribution.get(entry.getKey());
			if(bValue == null){
				//what?
			}
			else{
				ret += entry.getValue() * Math.log(entry.getValue() / bValue);
			}
		}
		
		return ret;
	}
	
	
	public double composedCompare(Distribution a, Distribution b, Distribution c){
		double ret = 0.0;
		
		Double bValue;
		Double cValue;
		
		for(Map.Entry<Double,Double> entry : a.distribution.entrySet()){
			bValue = b.distribution.get(entry.getKey());
			cValue = c.distribution.get(entry.getKey());
			
			if(bValue == null){
				//what?
			}
			else{
				ret += entry.getValue() * Math.log(entry.getValue() / bValue);
			}
			
			if(cValue == null){
				//what?
			}
			else{
				ret += entry.getValue() * Math.log(entry.getValue() / bValue);
			}
		}
		
		return ret;
	}
}
