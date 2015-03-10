package igtools.dictionaries.intersection;

import igtools.common.nucleotide.B3Nucleotide;
import igtools.common.sequence.B3LLSequence;
import igtools.dictionaries.elsa.IELSAIterator;

public class Intersector {
	public interface IntersectorListerner{
		public void intersection(B3Nucleotide[] kmer);
	}
	
	
	B3LLSequence[] seqs;
	IELSAIterator[] its;
	int k;
	IntersectorListerner listener;
	
	
	public int intersectionSize = 0;
	//public int unionSize = 0;
	
	
	public Intersector(B3LLSequence[] seqs, IELSAIterator[] its, int k, IntersectorListerner listener){
		this.seqs = seqs;
		this.its = its;
		this.k = k;
		this.listener = listener;
	}
	
	public void intersect(){
		boolean cicle = true;
		for(int i=0; i<its.length; i++){
			if(!its[i].next()){
				cicle = false;
				break;
			}
		}
		
		if(cicle){
			B3Nucleotide[][] kmers = new B3Nucleotide[its.length][];
			for(int i=0; i<its.length; i++){
				kmers[i] = new B3Nucleotide[k];
				its[i].kmer(kmers[i]);
			}
			
//			B3Nucleotide[] kmer = kmers[0];
			int kmer_i = 0;
			
			int compare;
			
			//B3Nucleotide[] stop = B3Nucleotide.toB3("ATACCCTCGT");
			B3Nucleotide[] stop = B3Nucleotide.toB3("ATACCCTCTA");
			
//			while(cicle && B3Nucleotide.compare(kmers[kmer_i], stop)<=0){
			while(cicle){
//				System.out.println("====================================================================================================");
//				System.out.print("#\t");
//				for(int m=0; m<its.length; m++){
//					if(its[m] != null){
//						System.out.print(m+"\t"+B3Nucleotide.toString(kmers[m]) +"\t");
//					}
//					else{
//						System.out.print("null \t");
//					}
//				}
//				System.out.println();
				
				for(int i=0; i<its.length; i++){
					//if(kmer_i != i){
//					System.out.println("comp("+kmer_i+","+i+")("+B3Nucleotide.toString(kmers[kmer_i])+","+B3Nucleotide.toString(kmers[i])+") = "+B3Nucleotide.compare(kmers[kmer_i], kmers[i]));
						compare = B3Nucleotide.compare(kmers[kmer_i], kmers[i]);
						
						if(compare == 0){
						}
						else{
							if(compare > 0){
								while(compare >0){
									if(!its[i].next()){
										cicle = false;
										break;
									}
									else{
										its[i].kmer(kmers[i]);
										compare = B3Nucleotide.compare(kmers[kmer_i], kmers[i]);
//										System.out.println("comp_("+kmer_i+","+i+")("+B3Nucleotide.toString(kmers[kmer_i])+","+B3Nucleotide.toString(kmers[i])+") = "+B3Nucleotide.compare(kmers[kmer_i], kmers[i]));
									}
								}
								
								if(cicle){
									if(compare == 0){
									}
									else if(compare<0){
										kmer_i = i;
										i = -1;
									}
									else{
										System.out.println("OPS");
									}
								}
							}
							else{
								kmer_i = i;
								i = -1;
							}
						}
					//}
				}	
				if(cicle){
//					System.out.print("@\t");
//					for(int m=0; m<its.length; m++){
//						if(its[m] != null){
//							System.out.print(m+"\t"+B3Nucleotide.toString(kmers[m]) +"\t");
//						}
//						else{
//							System.out.print("null \t");
//						}
//					}
//					System.out.println();
//					System.out.println(kmer_i +"\t"+ B3Nucleotide.toString(kmers[kmer_i]));
					
					intersectionSize++;
					if(listener != null)
						listener.intersection(kmers[kmer_i]);
					
					if(!its[kmer_i].next()){
						cicle = false;
						break;
					}
					else{
						its[kmer_i].kmer(kmers[kmer_i]);
					}
				}
			}
		}
	}
}
