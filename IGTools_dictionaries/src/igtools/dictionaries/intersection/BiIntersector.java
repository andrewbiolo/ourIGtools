package igtools.dictionaries.intersection;

import igtools.common.nucleotide.B3Nucleotide;
import igtools.common.sequence.B3LLSequence;
import igtools.dictionaries.elsa.IELSAIterator;
import igtools.dictionaries.elsa.OnDiskNELSAIterator;

public class BiIntersector {

		
	B3LLSequence[] seqs;
	IELSAIterator[] its;
	int k;
	
	
	public int intersectionSize = 0;
	public int unionSize = 0;
	
	
	public BiIntersector(B3LLSequence[] seqs, IELSAIterator[] its, int k){
		this.seqs = seqs;
		this.its = its;
		this.k = k;
	}
	
	
	public void intersect(){
		
		//if(its[0].next() && its[1].next()){
			
			B3Nucleotide[][] kmers = new B3Nucleotide[2][];
			kmers[0] = new B3Nucleotide[k];
			kmers[1] = new B3Nucleotide[k];
			
			
			int compare = -1;
			while(true){
				if(its[0].next())	its[0].kmer(kmers[0]);
				else				{compare = -2; break;}
				if(its[1].next())	its[1].kmer(kmers[1]);
				else				{compare = -3; break;}
				
				compare = B3Nucleotide.compare(kmers[0], kmers[1]);
				
				//System.out.println(B3Nucleotide.toString(kmers[0]) +"\t"+B3Nucleotide.toString(kmers[1])+"\t"+unionSize);
				
				if(compare == 0){
					intersectionSize++;
					unionSize++;
				}
				else if(compare < 0){
					unionSize++;
					//System.out.println(B3Nucleotide.toString(kmers[0]) +"\t"+B3Nucleotide.toString(kmers[1])+"--------");
					while(compare < 0){
						unionSize++;
						if(its[0].next()){
							its[0].kmer(kmers[0]);
							compare = B3Nucleotide.compare(kmers[0], kmers[1]);
							
//							System.out.println(B3Nucleotide.toString(kmers[0]));
						}
					}
				}
				else{
					unionSize++;
					//System.out.println(B3Nucleotide.toString(kmers[0]) +"\t"+B3Nucleotide.toString(kmers[1])+"++++++++");
					while(compare > 0){
						unionSize++;
						if(its[1].next()){
							its[1].kmer(kmers[1]);
							compare = B3Nucleotide.compare(kmers[0], kmers[1]);
							//System.out.println("\t\t"+B3Nucleotide.toString(kmers[1]));
						}
					}
				}
			}
			
			System.out.println(B3Nucleotide.toString(kmers[0]) +"\t"+B3Nucleotide.toString(kmers[1]));
			
			if(compare <0){
				System.out.println("fintersection: "+intersectionSize);
				System.out.println("funion: "+unionSize);
				
				if(its[0].hasNext()){
					System.out.println("its[0] can go");
					unionSize++;
					while(its[0].next())
						unionSize++;
				}
				if(its[1].hasNext()){
					System.out.println("its[1] can go");
					unionSize++;
					while(its[1].next())
						unionSize++;
				}
			}
			
		//}
	}
		
}
