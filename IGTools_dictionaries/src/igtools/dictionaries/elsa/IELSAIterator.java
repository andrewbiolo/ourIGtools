package igtools.dictionaries.elsa;

import igtools.common.nucleotide.B3Nucleotide;

public interface IELSAIterator{
	
    public IELSA elsa();
    /**
     * start SA position of the word
     * @return
     */
	public int istart();
	/**
	 * End SA position +1 ofthe word.
	 * Yes, you can do for(i=start; i<end; i++)
	 * 
	 * @return
	 */
	public int iend();
	
    public int k();
	public B3Nucleotide[] kmer();
    public void kmer(B3Nucleotide[] ns);
    //public String kmerString();
	public int multiplicity();
	public int[] positions();
	public int[] sortedPositions();
	
	public boolean isMinimalHapax();
	public boolean isGlobalMaximalRepeat();
	

	public boolean next();
	public boolean prev();
	
	public boolean good();
        
	public boolean hasNext();
	public boolean hasPrev();
        
        
    public IELSAIterator clone();
    public int compare(IELSAIterator it);
    
}
