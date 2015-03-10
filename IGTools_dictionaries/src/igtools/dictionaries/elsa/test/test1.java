package igtools.dictionaries.elsa.test;

import java.io.IOException;

import igtools.common.nucleotide.B3Nucleotide;
import igtools.common.sequence.B3LLSequence;
import igtools.dictionaries.elsa.IELSAIterator;
import igtools.dictionaries.elsa.NELSA;
import igtools.dictionaries.elsa.SAOnlyIterator;
import igtools.dictionaries.elsa.SAOnlyIterator2;

public class test1 {

	public static void main(String[] args){
		
		try{
			
			String a_seq ="/media/data/Genome/EscherichiaColi/536/3bit/ecoli_536.3bit";
			String a_nelsa = "/media/data/Genome/EscherichiaColi/536/nelsa/ecoli_536.nelsa";
			
			System.out.println("Loading sequence...");
			B3LLSequence b3seq = B3LLSequence.load(a_seq);
			System.out.println("done");
			System.out.println("Loading NELSA...");
			NELSA nelsa = new NELSA();
			nelsa.load(a_nelsa);
			System.out.println("done");
			nelsa.setSequence(b3seq);
			int[] sa = nelsa.sa();
			
			// Prints sequence's suffix array
			
			int k_s = 1;
			int k_e = 100;
			
			long time_s, time_e1, time_e2, time_e3;
			
			for(int k=k_s; k<k_e; k++){
				B3Nucleotide[] kmer = new B3Nucleotide[k];
				
				time_s = System.nanoTime();
				SAOnlyIterator it1 = new SAOnlyIterator(sa,b3seq,k);
				while(it1.next()){
				}
				time_e1 = System.nanoTime() - time_s;
				
				
				time_s = System.nanoTime();
				SAOnlyIterator2 it2 = new SAOnlyIterator2(sa,b3seq,k);
				while(it2.next()){
				}
				time_e2 = System.nanoTime() - time_s;
				
				time_s = System.nanoTime();
				IELSAIterator it3 = nelsa.begin(k);
				while(it3.next()){
					it3.kmer(kmer);
				}
				time_e3 = System.nanoTime() - time_s;
				System.out.println(k+"\t"+(time_e1/1e6)+"\t"+(time_e2/1e6)+"\t"+(time_e3/1e6));
			}
			
			
//			for(int k=k_s; k<k_e; k++){
//				System.out.println(k);
//				B3Nucleotide[] kmer1 = new B3Nucleotide[k];
//				B3Nucleotide[] kmer2 = new B3Nucleotide[k];
//				
//				SAOnlyIterator it1 = new SAOnlyIterator(sa,b3seq,k);
//				IELSAIterator it2 = nelsa.begin(k);
//				
//				while(it1.next()){
//					it1.kmer(kmer1);
//					if(!it2.next()){
//						System.out.println("not it2.next : "+B3Nucleotide.toString(kmer1));
//					}
//					else{
//						it2.kmer(kmer2);
//						if(B3Nucleotide.compare(kmer1, kmer2) != 0){
//							System.out.println(k + " : kmer1 != kmer2 : "+B3Nucleotide.toString(kmer1)+" "+B3Nucleotide.toString(kmer2));
//							//System.exit(0);
//						}
//					}
//				}
//				if(it2.next()){
//					it2.kmer(kmer2);
//					System.out.println("its2 has more kmers : " + B3Nucleotide.toString(kmer2));
//				}
//			}
			
			
		
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e);
		}
		/*
		 * For freezing the iterator's state: 
		 * get 'SAOnlyIterator.limits' and 'SAOnlyIterator.deep' private fields by mean of respective
		 * getter methods ('SAOnlyIterator.getLimits()', 'SAOnlyIterator.getDeep()') and save them
		 */
		// limits = it.getLimits();
		// deep = it.getDeep();
		// save 'limits' and 'deep' values
		/*
		 * For restoring the iterator's state:
		 * load previously saved 'limits' and 'deep' values and set them by mean of respective
		 * setter methods ('SAOnlyIterator.setLimits(int[][])', 'SAOnlyIterator.setDeep(int)')
		 */
		// load 'limits' and 'deep' values
		// it.setLimits(limits);
		// it.setDeep(deep);
	}
}
