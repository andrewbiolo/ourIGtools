package igtools.common.positionList;

/**
 * Insertion on head => decreasing order.
 * 
 * 
 * Attention all methods work supposing a decreasing order with no duplicates!
 * So, use insert() following this purpose.
 * 
 * @author bovi
 *
 */
public class LinkedPositionsList implements IPositionsList{

	public static class PosListNode{		
		public Integer pos = -1;
		public PosListNode next = null;
		public PosListNode(Integer p){ pos = p;}
		public PosListNode(Integer p, PosListNode n){ pos = p; next = n;}
	}
	
	
	private PosListNode head = null;
	private int size = 0;
	
	
	public LinkedPositionsList(){
	}
	
	public int size(){
		return size;
	}
	
	public void insertOnHead(Integer p){
		if(head == null){
			head = new PosListNode(p);
		}
		else{
			head = new PosListNode(p, head);
		}
		size++;
	}
	
	public PosListNode insertAfter(PosListNode n, Integer p){
		if(n == null){
			insertOnHead(p);
			return head;
		}
		else{
			n.next = new PosListNode(p, n.next);
			return n.next;
		}
	}
	
	public void orderedInsert(int npos){
		PosListNode c = head;
		PosListNode p = null;
		while(c!=null && c.pos>npos){
			p = c;
			c=c.next;
		}
		
		if(c==null){
			if(p==null){
				head = new PosListNode(npos);
			}
			else{
				p.next = new PosListNode(npos);
			}
		}
		else{
			if(p==null){
				head = new PosListNode(npos, head);
			}
			else{
				p.next = new PosListNode(npos, c);
			}
		}
		size++;
	}
	
	public boolean isEmpty(){
		return head == null;
	}
	
	
	/**
	 * maintains descreasing order
	 *  
	 * @param pl
	 */
	public void union(LinkedPositionsList pl){
		if(pl!=null){
			
			PosListNode pl_p;
			
			if(head == null && pl.head!=null){
				head = new PosListNode(pl.head.pos);	size++;
				pl_p = pl.head.next;
				PosListNode tail_p = head;
				while(pl_p != null){
					tail_p.next = new PosListNode(pl_p.pos);	size++;
					tail_p = tail_p.next;
					pl_p = pl_p.next;
				}
			}else{//head == null && pl.head == null (not runned)    ||    head!=null
				PosListNode c = head;
				PosListNode p = null;
				pl_p = pl.head;
				while(pl_p != null){

					while(c!=null && c.pos > pl_p.pos){
						p = c;
						c = c.next;
					}
					if(p == null){
						head = new PosListNode(pl_p.pos, c);	size++;
					}
					else{
						p.next = new PosListNode(pl_p.pos, c);	size++;
						c = p.next;
					}
					
					pl_p = pl_p.next;
				}
			}
		}
	}	
	
	@Override
	public String toString(){
		String s  = "["+this.size+"|";
		PosListNode n = head;
		while(n!=null){
			s += n.pos + " ";
			n = n.next;
		}
		s += "]";
		return s;
	}
	
	
	public static LinkedPositionsList follow(LinkedPositionsList before, LinkedPositionsList after){
		if(before == null || after == null) return new LinkedPositionsList();
		LinkedPositionsList plist = new LinkedPositionsList();
		
		PosListNode b_node = before.head;
		PosListNode a_node = after.head;
		
		while(b_node !=null){
			while(a_node!=null && (a_node.pos)>(b_node.pos+1)){
				a_node = a_node.next;
			}
			if(a_node == null) break;
			if(a_node.pos == b_node.pos +1) plist.insertOnHead(a_node.pos);
			
			b_node = b_node.next;
		}
		return plist;
	}
	
	public static LinkedPositionsList follow(LinkedPositionsList before, LinkedPositionsList after, int inc){
		if(before == null || after == null) return new LinkedPositionsList();
		
		LinkedPositionsList plist = new LinkedPositionsList();
		LinkedPositionsList.PosListNode phead = null;
		LinkedPositionsList.PosListNode ptail = null;
		
		PosListNode b_node = before.head;
		PosListNode a_node = after.head;
		
		int size = 0;
		
		while(b_node !=null){
			while(a_node!=null && (a_node.pos)>(b_node.pos +inc)){
				a_node = a_node.next;
			}
			if(a_node == null) break;
			if(a_node.pos == b_node.pos +inc){
				size++;
				if(ptail == null){
					ptail = phead = new LinkedPositionsList.PosListNode(b_node.pos);
				}
				else{
					ptail.next = new LinkedPositionsList.PosListNode(b_node.pos);
					ptail = ptail.next;
				}
			}
			
			b_node = b_node.next;
		}
		
		plist.head = phead;
		plist.size = size;
		
		return plist;
	}

	@Override
	public IPositionsListIterator iterator() {
		return new IPositionsListIterator(){
			private PosListNode n = head;

			@Override
			public int next() {
				if(n == null) return -1;
				n = n.next;
				if(n == null) return -1;
				return n.pos;
			}

			@Override
			public int position() {
				if(n == null) return -1;
				return n.pos;
			}
			
		};
	}

}
