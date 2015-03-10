package igtools.common.positionList;

public class MultiPositionsList implements IPositionsList{
	
	private static class PLNode{
		public IPositionsList plist;
		public PLNode next;
		public PLNode(IPositionsList p, PLNode n){
			plist = p;
			next = n;
		}
	}
	private static class PLINode{
		public IPositionsListIterator iti;
		public PLINode next;
		public PLINode(IPositionsListIterator i, PLINode n){
			iti = i;
			next = n;
		}
	}
	
	private static class fIterator implements IPositionsListIterator{
		@Override
		public int next() {	return -1;}
		@Override
		public int position() {	return -1;}	
	}
	
	private static class oIterator implements IPositionsListIterator{
		private PLINode fIterator;
		
		private PLINode c,p,m, pm;
		private int value = -1;
		
		public oIterator(PLNode n){
			PLNode plc = n;
			while(plc!=null){
				fIterator = new PLINode(plc.plist.iterator(), fIterator);
				plc = plc.next;
			}
			
			next();
		}
		
		@Override
		public int next() {
			if(fIterator == null){
				value = -1;
				return -1;
			}
			
			c = fIterator; 
			m = c;
			p = pm = null;
			
			while(c != null){
				if(c.iti.position() > m.iti.position()){
					m = c;
					pm = p;
				}
				p = c;
				c = c.next;
			}
			
			if(m != null){
				value = m.iti.position();
				
				if(m.iti.next() == -1){
					if(pm == null)
						fIterator = m.next;
					else
						pm.next = m.next;
				}
				
				return value;
			}
			
			value = -1;
			return -1;
		}
		@Override
		public int position() {
			return value;
		}	
	}
	
	
	
	
	private PLNode firstList = null;
	private int size;
	
	public MultiPositionsList(){
		size = 0;
	}
	
	public MultiPositionsList(IPositionsList...plists){
		for(IPositionsList p : plists){
			addPlist(p);
		}
	}
	
	public MultiPositionsList(IPositionsList[] plists, int l, int r){
		for(int i=l; i<r+1; i++){
			addPlist(plists[i]);
		}
	}
	
	
	
	public void addPlist(IPositionsList p){
		if(p!=null && p.size()!=0){
			firstList = new PLNode(p, firstList);
			size += p.size();
		}
	}
	
	

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size==0;
	}

	
	public IPositionsListIterator orderedIterator() {
		if(firstList == null) return new fIterator();
		return new oIterator(firstList);
		
	}
	
	@Override
	public IPositionsListIterator iterator() {
		if(firstList == null) return new fIterator();
		return new IPositionsListIterator() {
			private PLNode cPLNode = firstList;
			private IPositionsListIterator cPLIterator = firstList.plist.iterator();
			@Override
			public int position() {
				if(cPLNode == null) return -1;
				return cPLIterator.position();
			}
			
			@Override
			public int next() {
				if(cPLNode != null){
					if(cPLIterator.next() == -1){
						cPLNode = cPLNode.next;
						if(cPLNode != null){
							cPLIterator = cPLNode.plist.iterator();
						}
						else return -1;
					}
					return cPLIterator.position();
				}
				return -1;
			}
		};
	}
	
	
	
	@Override
	public String toString(){
		String s = "[";
		s += this.size +"|";
		IPositionsListIterator IT = this.iterator();
		int p;
		if((p = IT.position()) != -1){
			s += p+" ";
			while((p = IT.next()) != -1){
				s += p+" ";
			}
		}
		s += "]";
		return s;
	}

}
