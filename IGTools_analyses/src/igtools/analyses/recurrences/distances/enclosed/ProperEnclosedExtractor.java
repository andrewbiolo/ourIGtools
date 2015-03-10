package igtools.analyses.recurrences.distances.enclosed;

import igtools.dictionaries.elsa.IELSAIterator;
import igtools.dictionaries.elsa.INELSA;



/**
 * Extract enclosed regions between proper recurrences.
 * It returns the starting positions of such enclosed regions.
 *   
 * @author vbonnici
 *
 */
public abstract class ProperEnclosedExtractor {

	
	public abstract int[] getEnclosedStartPositions(IELSAIterator it, int dist);
	
	
	public static ProperEnclosedExtractor factory(boolean minimal, boolean overlapping, boolean skipNgaps){
		//TODO
		return null;
	}
	
	
	/**
	 * 
	 * @param minimal = true
	 * @param overlapping = not checked
	 * @param skipNgaps = true
	 * @return
	 */
	public static ProperEnclosedExtractor factory(){
		return new ProperEnclosedExtractor() {
			@Override
			public int[] getEnclosedStartPositions(IELSAIterator it, int dist) {
				System.out.println("dist "+dist);
				
				int[] sa = it.elsa().sa();
				int[] ns = ((INELSA)it.elsa()).ns();
				
				int[] poss = new int[it.multiplicity()];
				int[] nss = new int[it.multiplicity()];
				for(int i = it.istart(), j=0; i<it.iend(); i++, j++){
					poss[j] = sa[i];
					nss[j] = ns[i];
				}
				igtools.common.util.Arrays.keyedQsort(poss, nss);
				
				int count = 0;
				for(int i=0; i<poss.length-1; i++){
					if( (poss[i+1] - poss[i] == dist)    &&     (nss[i]>dist)){
						count++;
					}
				}
				
				System.out.println("count "+count);
				
				int[] spos = new int[count];
				
				int j=0;
				for(int i=0; i<poss.length-1; i++){
					if( (poss[i+1] - poss[i] == dist)    &&     (nss[i]>dist)){
						spos[j] = poss[i];
						j++;
					}
				}
				
				return spos;
			}
		};
	}
}
