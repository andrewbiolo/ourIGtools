package igtools.common.kmer;

import igtools.common.nucleotide.B2Nucleotide;
import igtools.common.nucleotide.INucleotide;

public interface IArrayKmer {

	public int[] code();
	public INucleotide[] nucleotides();
	
}
