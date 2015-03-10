package igtools.dictionaries.elsa;

import igtools.common.nucleotide.B3Nucleotide;

public class ByFactorsNELSAIterator implements IELSAIterator{
	
	
	private int k;
	private int l;
	private NELSA nelsa;
	
	private int nof_its;
	private IELSAIterator[] its;
	private int p;
	
	public ByFactorsNELSAIterator(int k, int l, NELSA nelsa){
		this.k = k;
		this.l = l;
		this.nelsa = nelsa;
		
		nof_its = k-l+1;
		its = new IELSAIterator[nof_its];
		for(int i=0; i<nof_its; i++)
			its[i] = null;
		p = -1;
	}
	

	@Override
	public IELSA elsa() {
		return nelsa;
	}

	@Override
	public int istart() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public int iend() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public int k() {
		return k;
	}
	
	public int l(){
		return l;
	}

	@Override
	public B3Nucleotide[] kmer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void kmer(B3Nucleotide[] ns) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public int multiplicity() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public int[] positions() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public int[] sortedPositions() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public boolean isMinimalHapax() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public boolean isGlobalMaximalRepeat() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public boolean next() {
		if(nof_its == k){
			
		}
		else{
			if(p == -1){
				its[0] = nelsa.begin(k);
			}
			else{
				
			}
		}
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean prev() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public boolean good() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public boolean hasNext() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public boolean hasPrev() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public int compare(IELSAIterator it) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
	@Override
	public IELSAIterator clone(){
		return new ByFactorsNELSAIterator(k, l, nelsa);
	}

}
