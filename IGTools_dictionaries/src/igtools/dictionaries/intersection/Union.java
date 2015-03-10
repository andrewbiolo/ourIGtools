package igtools.dictionaries.intersection;

import igtools.common.kmer.b3.unit.B3UnitLLKmer;
import igtools.common.nucleotide.B3Nucleotide;
import igtools.common.sequence.B3LLSequence;
import igtools.dictionaries.elsa.IELSAIterator;
import igtools.dictionaries.elsa.OnDiskNELSAIterator;

public class Union {
	public interface UnionListerner{
		public void intersection(B3Nucleotide[] kmer);
	}
	
	B3LLSequence[] seqs;
	IELSAIterator[] its;
	int k;
	
	UnionListerner listener = null;
	
	
	public int intersectionSize = 0;
	public long unionSize = 0;
	
	
	public Union(B3LLSequence[] seqs, IELSAIterator[] its, int k, UnionListerner listener){
		this.seqs = seqs;
		this.its = its;
		this.k = k;
		this.listener = listener;
	}
	
	public void union(){
		
		int minkmer_i = -1;
		//int n_minkmer_i = -1;
//		int o_minkmer_i = -1;
		int compare;
		int ii;
		boolean isint = true;
		
		B3Nucleotide[][] kmers = new B3Nucleotide[its.length][];
		
		for(int i=0; i<its.length; i++){
			if(its[i].next()){
				kmers[i] = new B3Nucleotide[k];
				its[i].kmer(kmers[i]);
				
				if(minkmer_i == -1){
					minkmer_i = i;
				}
				else{
					if(B3Nucleotide.compare(kmers[i],kmers[minkmer_i])<0){
						minkmer_i = i;
					}
				}
			}
			else{
				kmers[i] = null;
			}
		}
		
		System.out.println("first min: "+minkmer_i+" "+B3Nucleotide.toString(kmers[minkmer_i]));
		
		boolean cicle = (minkmer_i != -1);
		
		B3Nucleotide[] stop = B3Nucleotide.toB3("ACGCGCGTTC");
		
		
		///while(cicle && B3Nucleotide.compare(kmers[minkmer_i], stop)<=0){
		while(cicle){
//			System.out.println("=====================================================================================================");
////			System.out.println(minkmer_i+" "+B3Nucleotide.toString(kmers[minkmer_i]));
//			System.out.print("#\t");
//			for(int m=0; m<its.length; m++){
//				if(its[m] != null){
//					System.out.print(m+"\t"+B3Nucleotide.toString(kmers[m]) +"\t");
//				}
//				else{
//					System.out.print("null \t");
//				}
//			}
//			System.out.println();
					
			
			cicle = true;
			//n_minkmer_i = -1;
			
			minkmer_i = -1;
			isint = true;
			for(ii=0; ii<its.length; ii++){
				if(its[ii] != null){
					if(minkmer_i == -1){
						minkmer_i = ii;
					}
					else{
//						System.out.println(ii+" comp("+B3Nucleotide.toString(kmers[minkmer_i])+","+B3Nucleotide.toString(kmers[ii])+") = "+(B3Nucleotide.compare(kmers[minkmer_i], kmers[ii])));
						compare = B3Nucleotide.compare(kmers[minkmer_i], kmers[ii]);
						if(compare >0){
							minkmer_i = ii;
							isint = false;
						}
						else if(compare < 0){
							isint = false;
						}
					}
				}
				else{
					isint = false;
				}
			}
			
//			if(isint)
//				intersectionSize++;
			
			if(minkmer_i == -1){
				cicle = false;
			}
			else{
				if(isint){
					intersectionSize++;
					if(listener != null)
						listener.intersection(kmers[minkmer_i]);
				}
//					System.out.println(B3Nucleotide.toString(kmers[minkmer_i])+" -");
//				else
//					System.out.println(B3Nucleotide.toString(kmers[minkmer_i]));
				
				for(ii=0; ii<its.length; ii++){
					if(ii!=minkmer_i && (its[ii] != null && (B3Nucleotide.compare(kmers[minkmer_i], kmers[ii]) ==0))){
//						System.out.println("next "+ii);
						if(its[ii].next()){
							its[ii].kmer(kmers[ii]);
						}
						else{
							if(its[ii] instanceof OnDiskNELSAIterator)
								try {
									((OnDiskNELSAIterator)its[ii]).close();
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							
							its[ii] = null;
						}
					}
				}
				
				
//				System.out.println("next_m "+minkmer_i);
				if(its[minkmer_i].next()){
					its[minkmer_i].kmer(kmers[minkmer_i]);
				}
				else{
					if(its[minkmer_i] instanceof OnDiskNELSAIterator)
					try {
						((OnDiskNELSAIterator)its[minkmer_i]).close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					its[minkmer_i] = null;
				}
			}
			
			if(cicle)
				unionSize++;
			
//			System.out.println("minkmer_i "+minkmer_i);
//			System.out.print("@\t");
//			for(int m=0; m<its.length; m++){
//				if(its[m] != null){
//					System.out.print(m+"\t"+B3Nucleotide.toString(kmers[m]) +"\t");
//				}
//				else{
//					System.out.print("null \t");
//				}
//			}
//			System.out.println();
			
			
//			System.out.print("#\t");
//			for(int m=0; m<its.length; m++){
//				if(its[m] != null){
//					System.out.print(m+"\t"+B3Nucleotide.toString(kmers[m]) +"\t");
//				}
//				else{
//					System.out.print("null \t");
//				}
//			}
//			System.out.println();
//			
//			isint = true;
//			for(ii=0;ii<its.length; ii++){
//				cicle |= (kmers[ii] != null);
//				if(ii != minkmer_i){
//					if(kmers[ii] != null){
//						System.out.println(ii+" comp("+B3Nucleotide.toString(kmers[minkmer_i])+","+B3Nucleotide.toString(kmers[ii])+") = "+(B3Nucleotide.compare(kmers[minkmer_i], kmers[ii])));
//						compare = B3Nucleotide.compare(kmers[minkmer_i], kmers[ii]);
//						if(compare==0){
//							System.out.print("next "+ii+"\t");
//							if(its[ii].next()){
//								its[ii].kmer(kmers[ii]);
//								if(n_minkmer_i == -1){
//									n_minkmer_i = ii;
//								}
//								else{
//									if(B3Nucleotide.compare(kmers[ii], kmers[n_minkmer_i])<0){
//										n_minkmer_i = ii;
//									}
//								}
//								System.out.println(B3Nucleotide.toString(kmers[ii]));
//							}
//							else{
//								its[ii] = null;
//								System.out.println("null");
//							}
//						}
//						else{
//							isint = false;
//							if(compare > 0){
//								
//							}
//						}
//					}
//					else{
//						isint = false;
//					}
//				}
//			}
//			
//			if(!isint)
//				System.out.println("-----------------------------------------------------------------------------");
//			for(int m=0; m<its.length; m++){
//				if(its[m] != null){
//					System.out.print(B3Nucleotide.toString(kmers[minkmer_i]) +"\t");
//				}
//				else{
//					System.out.print("null \t");
//				}
//			}
//			System.out.println();
//			
//			if(isint){
//				System.out.println(B3Nucleotide.toString(kmers[minkmer_i])+" -");
//			}
//			else{
//				System.out.println(B3Nucleotide.toString(kmers[minkmer_i]));
//			}
//			
//			System.out.println("tmin: "+n_minkmer_i+"; old: "+minkmer_i+"; cicle: "+cicle);
//			
//			
//			if(its[minkmer_i].next()){
//				its[minkmer_i].kmer(kmers[minkmer_i]);
//				if(n_minkmer_i == -1){
//					n_minkmer_i = minkmer_i;
//				}
//				else{
//					if(B3Nucleotide.compare(kmers[minkmer_i], kmers[n_minkmer_i])<0){
//						n_minkmer_i = minkmer_i;
//					}
//				}
//			}
//			else{
//				its[minkmer_i] = null;
//			}
//			
//			
//			
//			
//			if(isint)
//				intersectionSize++;
			
////			System.out.println("min: "+n_minkmer_i+"; old: "+minkmer_i);
//			if(n_minkmer_i == -1){
//				cicle = false;
//			}
//			else{
//				minkmer_i = n_minkmer_i;
////				System.out.println("n min: "+minkmer_i+" "+B3Nucleotide.toString(kmers[minkmer_i]));
//			}
		}
	}
	
	
}
