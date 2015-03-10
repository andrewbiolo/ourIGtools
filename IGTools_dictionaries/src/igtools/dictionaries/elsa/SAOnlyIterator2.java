package igtools.dictionaries.elsa;

import java.io.IOException;

import igtools.common.nucleotide.B3Nucleotide;
import igtools.common.sequence.B3LLSequence;
import igtools.dictionaries.elsa.IELSA;
import igtools.dictionaries.elsa.IELSAIterator;

public class SAOnlyIterator2 implements IELSAIterator {

	private int[] sa;
	private B3LLSequence seq;
	private int k;
	/*
	 * This 2 x (k+1) matrix contains the suffix array's intervals of the current k-mer, for each deep.
	 * The first row contains the lower limits and the second row the upper limits.
	 * So limits[][0] contains the suffix array bounds of 0-mer (e.g. the interval [0, sequence's length minus one]),
	 * limits[][1] contains the suffix array bounds of 1-mer that the iterator is considering at certain moment 
	 * (e.g. the interval [first occurrence of 'X', last occurrence of 'X' minus one] if the iterator is 
	 * analyzing the character 'X') and so on. With this matrix the iterator can obtain the next k-mer without
	 * restarting the analyze from the first character.
	 */
	private int limits[][];
	private int deep;
	
	// It doesen't need NELSA object, it only uses the suffix array.
	public SAOnlyIterator2 (int[] sa, B3LLSequence seq, int k){
		this.sa = sa;
		this.seq = seq;
		this.k = k;
		this.limits = new int[2][k+1];
		this.limits[0][0] = 0;
		this.limits[1][0] = this.seq.length - 1;
		this.deep = 0;
	}

	// Get the number of times the current k-mer is present in the sequence.
	@Override
	public int multiplicity() {
		if (this.deep > 0) return this.limits[1][this.deep] - this.limits[0][this.deep] + 1;
		else return 0;
	}

	/*
	 *  Get the list of the sequence's positions in which the current k-mer occurs, in
	 *  suffix array order (so they aren't lexicographically sorted). 
	 */
	@Override
	public int[] positions() {
		int size;
		if ((size = this.multiplicity()) > 0) {
			int[] positions = new int[size];
			for (int i = 0; i < size ; i ++) positions[i] = this.sa[this.limits[0][this.deep]+i];
			return positions;
		} else return null;
	}

	/*
	 * Jumps to the next k-mer, if any.
	 * At initialization the iterator doesn't point to any k-mer, so it is necessary to call
	 * this method for getting also the first k-mer. If the method return 'false' there is no
	 * more k-mer and the iterator doesen't point to any k-mer ('deep' is set to -1).
	 */
	@Override
	public boolean next() {
		
		int i;
		boolean found = false, ctrl;
		
		if (this.limits[1][this.deep] >= this.seq.length - 1 && this.limits[0][this.deep] == this.seq.length - this.multiplicity()) this.deep = -1; // Reached the end of the suffix array
		ctrl = false;
		while (this.deep > 0 && ctrl == false) { // Finds previous not yet analyzed interval
			if (this.limits[1][this.deep] < this.limits[1][this.deep-1]) {
				this.limits[0][this.deep-1] = this.limits[1][this.deep] + 1;
				ctrl = true;
			}
			this.deep --;
		}
		while (found == false && this.deep >= 0) { 
			for (i = this.deep; i < this.k; i ++) { // Searching for a k-mer
				int index = this.limits[0][i];
				if (index >= this.seq.length) { // Reached the end of the suffix array
					i = this.k; // Break for condition, exit
					this.deep = -1;
				} else {
					if (this.limits[0][i+1] == this.limits[0][i] && this.limits[0][i+1] != 0) { // Interval already analyzed
						this.limits[1][i] = this.limits[0][i];
						i = this.k + 1; // Break for condition, skip suffix
						ctrl = false;
						while (this.deep > 0 && ctrl == false) { // Finds previous not yet analyzed interval
							if (this.limits[1][this.deep] < this.limits[1][this.deep-1]) {
								this.limits[0][this.deep-1] = this.limits[1][this.deep] + 1;
								ctrl = true;
							}
							this.deep --;
						}
					} else { // Get current interval
						byte current = this.getB3Safe(this.sa[this.limits[0][i]]+i);
						if (current != B3Nucleotide.NULL_CODE) { // Refine interval
							int l = this.limits[0][i] + 1;
							while (l < this.seq.length && current == this.getB3Safe(this.sa[l]+i) && l <= this.limits[1][i]) l ++;
							this.limits[0][i+1] = this.limits[0][i];
							this.limits[1][i+1] = l - 1;
							this.deep ++;
							i ++;
							if (this.limits[0][i] == this.limits[1][i] && this.seq.length - this.sa[this.limits[0][i]] >= this.k) { // Only one suffix in the interval, stop refining
								while (i < this.k && this.limits[0][i+1] != this.limits[0][i]) { // Check if the k-mer is really new and set the limits
									this.limits[0][i+1] = this.limits[0][i];
									this.limits[1][i+1] = this.limits[1][i];
									i ++;
								}
								if (i == this.k) { // It's not a k-mer already found
									found = true;
									this.deep = i;
								} else {
									i = this.k + 1;
								}
							} else i --;
						} else { // Suffix shorter than k characters
							this.limits[0][i] = this.limits[0][i] + 1;
							if (this.limits[0][i] > this.limits[1][i]) this.limits[1][i] = this.limits[1][i-1];
							i = this.k; // Break for condition, skip suffix
						}
					}
				}
			}
			if (i == this.k) found = true; // k-mer found
		}
		
		return found;
	}
	
	/*
	 * Method for getting the 'pos' element of the sequence.
	 * It checks if the position of the requested element is not out of bound.
	 */
	private byte getB3Safe (int pos) {
		if (pos >= this.seq.length || pos < 0) return B3Nucleotide.NULL_CODE;
		else return (byte) this.seq.getB3(pos);
	}
		
	/*
	 * Return a copy of the current k-mer (or null if the iterator doesn't point to any)
	 * It istantiates a new memory area for the k-mer.
	 */
	@Override
	public B3Nucleotide[] kmer() {
		if (this.multiplicity() > 0) {
			B3Nucleotide[] mer = new B3Nucleotide[this.k];
			this.seq.getB3(this.sa[this.limits[0][this.deep]], mer);
			return mer;
		} else return null;
	}
	
	/*
	 * Return a copy of the current k-mer (or null if the iterator doesn't point to any)
	 * It reuses the memory area pointed by the parameter.
	 */
	@Override
	public void kmer(B3Nucleotide[] mer) {
		if (this.multiplicity() > 0) this.seq.getB3(this.sa[this.limits[0][this.deep]], mer);
	}
	
	// Returns a copy of this iterator
	@Override
	public IELSAIterator clone() {
		SAOnlyIterator2 tmp = new SAOnlyIterator2 (this.sa, this.seq, this.k);
		for (int i = 0; i <= k; i ++) {
			tmp.limits[0][i] = this.limits[0][i];
			tmp.limits[1][i] = this.limits[1][i];
		}
		tmp.deep = this.deep;

		return tmp;
	}

	// Get method for saving 'limits' private field
	public int[][] getLimits() {
		return this.limits;
	}
	
	// Set method for loading 'limits' private field
	public void setLimits(int[][] limits) {
		for (int i = 0; i <= k; i ++) {
			this.limits[0][i] = limits[0][i];
			this.limits[1][i] = limits[1][i];
		}
	}
	
	// Get method for saving 'deep' private field
	public int getDeep() {
		return this.deep;
	}
	
	// Set method for loading 'deep' private field
	public void setDeep(int deep) {
		this.deep = deep;
	}

// -----------------------------------------------------------------------------
	
	@Override
	public IELSA elsa() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean prev() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int istart() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int iend() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int k() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int[] sortedPositions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isMinimalHapax() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isGlobalMaximalRepeat() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean good() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasPrev() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int compare(IELSAIterator it) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}