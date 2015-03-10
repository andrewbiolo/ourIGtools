package igtools.dictionaries.elsa;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.util.Arrays;

import igtools.common.exceptions.UnimplementedException;
import igtools.common.nucleotide.B3Nucleotide;
import igtools.common.sequence.B3LLSequence;
import igtools.common.sequence.B3Sequence;
import igtools.common.util.Timer;

public class OnDiskNELSAIterator implements IELSAIterator {

	public int k;
	
//	private boolean onDiskSeq = true;
	private B3LLSequence b3seq = null;
//	private DataInputStream seqStream = null;
	
	private DataInputStream nelsaStream = null;
	private RandomAccessFile nelsaRAF = null;
	
	private int seqLength = 0;
	private int nelsaLength = 0;
	
	public B3Nucleotide[] kmer;
	
	protected int sa_istart;
	protected int istart;//inclusive
	protected int iend;//exclusive
	protected int limit;
	protected int r_limit;
	
	private long seek_pos = 0;
	
	private int c_sa=-1, c_lcp=-1, c_ns=-1;
	//private int c_pos=-1;
	
//	public OnDiskNELSAIterator(String seqFile, String nelsaFile, int k) throws Exception{
//		this.k = k;
//		onDiskSeq = true;
//		initB3Seq(seqFile);
//		initNELSA(nelsaFile);
//	}
	public OnDiskNELSAIterator(B3LLSequence seq, String nelsaFile, int k) throws Exception{
		this.k = k;
//		onDiskSeq = false;
		b3seq = seq;
		seqLength = b3seq.length();
		initNELSA(nelsaFile);
		
		if(seqLength != nelsaLength)
			throw new Exception("Discarding seq and nelsa length: "+seqLength+" "+nelsaLength);
		
		r_limit = seqLength - 1;
		limit = seqLength - k;
		istart = -1;
		sa_istart = -1;
		iend = 0;
	}
	
	public void close() throws Exception{
//		if(seqStream != null)
//			seqStream.close();
		if(nelsaStream != null)
			nelsaStream.close();
		if(nelsaRAF != null)
			nelsaRAF.close();
	}
	
	public B3LLSequence b3seq(){
		return b3seq;
	}
	
	
//	private int getB3(int pos,B3Nucleotide[] ns){
//		if(onDiskSeq)
//			return b3seq.getB3(pos, ns);
//		
//		return 0;
//	}
	
//	private void initB3Seq(String seqFile) throws Exception{
//		seqStream = new DataInputStream(new BufferedInputStream(new FileInputStream(seqFile)));
//		long version = seqStream.readLong();
//		if(version !=  B3LLSequence.serialVersionUID){
//			seqStream.close();
//			throw new Exception("Wrong LMLR3BitSet file version. \nExpected: "+ Long.toBinaryString(B3LLSequence.serialVersionUID) +"\nfound: \n"+Long.toBinaryString(version));
//		}
//		seqLength = seqStream.readInt();
//	}
	private void initNELSA(String nelsaFile) throws Exception{
		nelsaRAF = new RandomAccessFile(nelsaFile, "r");
//		System.out.println("nof bytes: "+nelsaRAF.length());
		long version = nelsaRAF.readLong();
		if(version !=  NELSA.serialVersionUID){
			nelsaRAF.close();
			throw new Exception("Wrong NELSA file version. \nExpected: "+ Long.toBinaryString(NELSA.serialVersionUID) +"\nfound: \n"+Long.toBinaryString(version));
		}
		nelsaLength = nelsaRAF.readInt();
		seek_pos = nelsaRAF.getFilePointer();
//		System.out.println("fseek: "+seek_pos);
		nelsaStream = new DataInputStream(new BufferedInputStream(new FileInputStream(nelsaRAF.getFD())));
		if(nelsaLength>0)
			nextRow();
			//nextRow(-1);
	}

	@Override
	public IELSA elsa() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public int istart() {
		return istart;
	}

	@Override
	public int iend() {
		return iend;
	}

	@Override
	public int k() {
		return k;
	}

	@Override
	public B3Nucleotide[] kmer() {
		B3Nucleotide[] seq = new B3Nucleotide[k];
		b3seq.getB3(sa_istart, seq);
		return seq;
	}

	@Override
	public void kmer(B3Nucleotide[] ns) {
		b3seq.getB3(sa_istart, ns);
	}

	@Override
	public int multiplicity() {
		return iend - istart;
	}

	@Override
	public int[] positions() {
		if(multiplicity() > 1){
//			int[] pos =  Arrays.copyOfRange(sa, istart, iend);
//			Arrays.sort(pos);
//			return pos;
			try{
				//System.out.println("seek_pos_to: "+seek_pos);
				
				//nelsaRAF.seek(seek_pos);
				nelsaRAF.seek(seek_pos + (3*4*istart));
//				System.out.println("seek_pos_to: "+(seek_pos + (3*4*istart)));
//				if(nelsaStream != null)
//					nelsaStream.close();
				nelsaStream = new DataInputStream(new BufferedInputStream(new FileInputStream(nelsaRAF.getFD())));
				
				int[] poss = new int[multiplicity()];
				
				for(int i=0; i<poss.length; i++){
					//nextRow(-1);
					nextRow();
					poss[i] = c_sa;
				}
				
				if(iend < r_limit)
					//nextRow(-1);
					nextRow();
				
				return poss;
			}catch(Exception e){
				e.printStackTrace();
				System.out.println(e);
				return null;
			}
		}
		else{
			int[] poss = new int[1];
			poss[0] = sa_istart;
			return poss;
		}
	}

	@Override
	public int[] sortedPositions() {
		int[] poss = positions();
		Arrays.sort(poss);
		return poss;
	}

	@Override
	public boolean isMinimalHapax() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public boolean isGlobalMaximalRepeat() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
	
	//private void nextRow(int pos) throws Exception{
	//	if(c_pos != pos){
	public void nextRow() throws Exception{
//			if(nelsaStream == null)
//				nelsaStream = new DataInputStream(new BufferedInputStream(new FileInputStream(nelsaRAF.getFD())));			
			c_sa = nelsaStream.readInt();
			c_lcp = nelsaStream.readInt();
			c_ns = nelsaStream.readInt();
			//c_pos++;
	//	}
	}
	public void prevRow() throws Exception{
		if(istart > 0){
			nelsaRAF.seek(seek_pos + (3*4*(istart-1)));
			nelsaStream = new DataInputStream(new BufferedInputStream(new FileInputStream(nelsaRAF.getFD())));
			c_sa = nelsaStream.readInt();
			c_lcp = nelsaStream.readInt();
			c_ns = nelsaStream.readInt();
		}
	}
	
	@Override
	public boolean next(){
//		System.out.println("("+iend+","+nelsaLength+")");
		if(iend < nelsaLength){
		//System.out.println(">next");
		try{
			//System.out.println("l "+iend);
			int l = iend;
			//while(l<sa.length && (ns[l]<=k || sa[l]>limit)){
			if(l<nelsaLength){
				//nextRow(l);
				//nextRow();
				//TODO
				//while(l<nelsaLength && (c_ns<=k || c_sa>limit || c_lcp<k)){
				while(l<nelsaLength && (c_ns<=k || c_sa>limit)){
					//if(c_ns<=k)System.out.println("c_ns<=k : "+c_ns+"<="+k);
					//if(c_sa>limit) System.out.println("c_sa>limit : "+c_sa+">"+limit);
					l++;
					//nextRow(l);
					if(l<nelsaLength)
					nextRow();
				}
			}
			
			//System.out.println("l "+l);
			
			//if(l >= sa.length){
			if(l >= nelsaLength){
				istart = -1;
				iend = 0;
				//System.out.println("<next");
				return false;
			}
			
			//seek_pos = nelsaRAF.getFilePointer();
			//System.out.println("seek pos fd: "+seek_pos);
			//seek_pos = nelsaRAF.getChannel().position();
			//System.out.println("seel pos chl: "+seek_pos);
			istart = l;
			sa_istart = c_sa;
			//System.out.println("istart "+istart);
			l++;
			//while(l<sa.length && lcp[l]>=k && sa[l]<limit && ns[l]>k){
			if(l<nelsaLength){
				//nextRow(l);
				nextRow();
				//TODO
				while(l<nelsaLength && (c_lcp>=k && c_sa<limit && c_ns>k)){
					l++;
					//nextRow(l);
					if(l<nelsaLength)
					nextRow();
				}
			}
			iend = l;
			//System.out.println("iend "+iend);
			//System.out.println("<next");
			return true;
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e);
//			System.out.println("<next");
			return false;
		}
		}
		return false;
	}

	@Override
	public boolean prev() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not supported yet.");
//		int l = istart;
//        l--;
//if(l == sa.length)
//l = sa.length-1;
//while(l>=0 && (ns[l]<=k || sa[l]>limit))
////while(l>=0 && (ns[l]<=k || sa[l]>limit || lcp[l]<k))
//l--;
//
//if(l<0){
//istart = -1;
//iend = 0;
//return false;
//}
////
////iend = l+1;
//while(l>=0 && lcp[l]>=k && sa[l]<limit && ns[l]>k){
//l--;
//}
////istart = l;
//        iend = istart;
//        istart = l;
//return true;
	}

	@Override
	public boolean good() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public boolean hasNext() {
		 return (istart+1 < r_limit && iend < r_limit);
		// TODO Auto-generated method stub
		//throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public boolean hasPrev() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public int compare(IELSAIterator it) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public OnDiskNELSAIterator clone(){
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
	
	
	public static boolean equals(B3Nucleotide[] ns1, B3Nucleotide[] ns2){
		for(int i=0; i<ns1.length; i++){
			if(ns1[i].code() != ns2[i].code())
				return false;
		}
		return true;
	}
	public static boolean equals(int[] pos1, int[] pos2){
		for(int i=0; i<pos1.length; i++){
			if(pos1[i]!= pos2[i])
				return false;
		}
		return true;
	}
	public static void println(int[] pos){
		for(int i=0; i<pos.length; i++)
			System.out.print(pos[i]+" ");
		System.out.println();
	}
	
	
	
	public static void main(String[] args){
		String iseq = null;
		String inelsa = null;
		int k = 0;
		try{
			iseq =args[0];
			inelsa = args[1];
			k = Integer.parseInt(args[2]);
		}catch(Exception e){
		}
		
		try{
			Timer timer = new Timer();
			
			System.out.println(iseq);
			System.out.println("Loading sequence...");
			B3LLSequence b3seq = B3LLSequence.load(iseq);
			System.out.println("done "+timer.getElapsedSecs() +"sec.\n");
			
			timer.reset();
			System.out.println("Loading NELSA...");
			NELSA nelsa = new NELSA();
			nelsa.load(inelsa);
			System.out.println("done "+timer.getElapsedSecs()+" sec.");
			nelsa.setSequence(b3seq);
			nelsa.print(k);
			
			IELSAIterator nit = nelsa.begin(k);
			OnDiskNELSAIterator dit = new OnDiskNELSAIterator(b3seq, inelsa, k);
			
			int count = 0;
			
			B3Nucleotide[] nns = new B3Nucleotide[k];
			B3Nucleotide[] dns = new B3Nucleotide[k];
			int[] npos;
			int[] dpos;
			
			while(nit.next()){
				nit.kmer(nns);
				npos=nit.positions();
				
				System.out.println("nit ["+nit.istart()+","+nit.iend()+"] "+ B3Nucleotide.toString(nit.kmer()));
				println(npos);
				
				if(!dit.next()){
					System.out.println("RAM NELSA is longer than Disk NELSA");
					System.out.println("last kmer: "+ B3Nucleotide.toString(nit.kmer()));
					System.out.println("RAM istart: "+nit.istart());
					System.out.println("RAM iend: "+nit.iend());
					System.out.println("Disk istart: "+dit.istart());
					System.out.println("Disk iend: "+dit.iend());
					break;
				}
				dit.kmer(dns);
				dpos=dit.positions();
				if(!equals(nns, dns)  || (nit.istart()!=dit.istart()) || (nit.iend()!=dit.iend())){
					System.out.println("RAM NELSA is different from Disk NELSA");
					System.out.println("last kmer: "+ B3Nucleotide.toString(nit.kmer()));
					System.out.println("RAM istart: "+nit.istart());
					System.out.println("RAM iend: "+nit.iend());
					System.out.println("last kmer: "+ B3Nucleotide.toString(dit.kmer()));
					System.out.println("Disk istart: "+dit.istart());
					System.out.println("Disk iend: "+dit.iend());
					break;
				}
				else if(!equals(npos, dpos)){
					System.out.println("RAM NELSA pos are different from Disk NELSA");
					System.out.println("last kmer: "+ B3Nucleotide.toString(nit.kmer()));
					System.out.println("RAM istart: "+nit.istart());
					System.out.println("RAM iend: "+nit.iend());
					println(npos);
					System.out.println("last kmer: "+ B3Nucleotide.toString(dit.kmer()));
					System.out.println("Disk istart: "+dit.istart());
					System.out.println("Disk iend: "+dit.iend());
					println(dpos);
					break;
				}
				else{
					System.out.println("ok ["+nit.istart()+","+nit.iend()+"] ["+dit.istart()+","+dit.iend()+"]");
				}
				System.out.println("-");
			}
			
			if(dit.next()){
				System.out.println("RAM NELSA is shorter than Disk NELSA");
				System.out.println("last kmer: "+ B3Nucleotide.toString(dit.kmer()));
				System.out.println("RAM istart: "+nit.istart());
				System.out.println("RAM iend: "+nit.iend());
				System.out.println("Disk istart: "+dit.istart());
				System.out.println("Disk iend: "+dit.iend());
			}
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e);
		}
	}
}
