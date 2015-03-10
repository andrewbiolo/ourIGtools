package igtools.common.positionList;

/**
 * 
 * Ex:
 * 
 * while(it.position() != -1){
 *  do domething
 *  it.next();
 * }
 * 
 * @author bovi
 *
 */
public interface IPositionsListIterator {

	public int next();
	public int position();
}
