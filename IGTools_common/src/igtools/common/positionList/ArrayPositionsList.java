package igtools.common.positionList;


public class ArrayPositionsList implements IPositionsList{
	
	private int[] elements;
	
	public ArrayPositionsList(int size){
		this.elements = new int[size];
	}
	
	public ArrayPositionsList(int[] data, boolean copy){
		if(copy){
			this.elements = new int[data.length];
			for(int i=0; i<data.length; i++)
				this.elements[i] = data[i];
		}
		else{
			this.elements = data;
		}
	}
	public ArrayPositionsList(LinkedPositionsList plist){
		if(plist.size() > 0){
			this.elements = new int[plist.size()];
			IPositionsListIterator it = plist.iterator();
			this.elements[0] = it.position();
			int i = 1, e;
			while((e = it.next()) != -1){
				elements[i] = e;
				i++;
			}
		}
	}	

	@Override
	public int size() {
		return this.elements.length;
	}

	@Override
	public boolean isEmpty() {
		return this.elements.length == 0; 
	}

	@Override
	public IPositionsListIterator iterator() {
		return new IPositionsListIterator(){
			private int i = 0;

			@Override
			public int next() {
				i++;
				if(i >= elements.length)
					return -1;
				return elements[i];
			}

			@Override
			public int position() {
				if(i > elements.length)
					return -1;
				return elements[i];
			}
			
		};
	}

}
