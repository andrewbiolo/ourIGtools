package igtools.dictionaries.elsa.test;

import igtools.common.nucleotide.B3Nucleotide;
import igtools.common.sequence.B3LLSequence;
import igtools.common.sequence.B3Sequence;
import igtools.dictionaries.elsa.IELSA;
import igtools.dictionaries.elsa.IELSAIterator;
import igtools.dictionaries.elsa.NELSA;

public class t1 {

	
	
	public static void main(String[] args){
		
		System.out.println(Integer.toBinaryString(Byte.MIN_VALUE));
		System.out.println(Integer.toBinaryString(10));
		System.out.println(Integer.toBinaryString(-10));
		
//		B3LLSequence seq = new B3LLSequence("ACGGTGCANNNGTCATGN");
//		NELSA nelsa = new NELSA(seq);
//		
//		B3Nucleotide[] kmer = new B3Nucleotide[10];
//		IELSAIterator it = nelsa.begin(10);
//		while(it.next()){
//			it.multiplicity();
//			it.positions();
//			it.kmer(kmer);
//			System.out.println(B3Nucleotide.toString(kmer));
//		}
//		
//		int pos = 5;
//		B3Nucleotide.charFor(seq.getB3(pos));
//		
//		
//		
//		//estendere ITELSAIterator
//		
		
	}
	
	private static void fromFile(String seqFile, String nelsaFile){
		try{
			System.out.println("seq");
			B3LLSequence b3seq = B3LLSequence.load(seqFile);
			
			
			System.out.println("nelsa");
			NELSA nelsa = new NELSA();
			nelsa.load(nelsaFile);
			nelsa.setSequence(b3seq);
			
			
			
		}catch(Exception e){
			e.printStackTrace();
			System.err.println(e);
		}
	}
	
	
	
	class Itt implements IELSAIterator{
		
		//private NELSA nelsa;
		private int[] sa;
		B3LLSequence seq;
		
		public Itt(NELSA nelsa, B3LLSequence seq){
			//this.nelsa = nelsa;
			this.sa = nelsa.sa();
			this.seq = seq;
			
		}
		

		@Override
		public IELSA elsa() {
			// TODO Auto-generated method stub
			return null;
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
		public B3Nucleotide[] kmer() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void kmer(B3Nucleotide[] ns) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public int multiplicity() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int[] positions() {
			// TODO Auto-generated method stub
			return null;
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
		public boolean next() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean prev() {
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
		
		@Override
		public Itt clone(){
			return null;
		}
	}
}
