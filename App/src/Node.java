/**
 * Clase Node, se encarga de crear un nodo
 * 
 * @author Fabian Alejandro Cristancho Rincon
 *
 * @param <T> Objeto generico de tipo T
 */
public class Node<T> {
	protected T infoData;
	protected Node<T> next;

	/**
	 * Constructor que recibe un objeto de tipo T y un objeto de tipo Node<T> el
	 * cual va a representar a un siguiente
	 * 
	 * @param infoData Objeto de tipo T
	 * @param next     Nodo siguiente
	 */
	public Node(T infoData, Node<T> next) {
		this.infoData = infoData;
		this.next = next;
	}

	/**
	 * Constructor que recibe un objeto de tipo T y lo iguala al atributo de la
	 * clase, iguala a next como null
	 * 
	 * @param infoData
	 */
	public Node(T infoData) {
		this.infoData = infoData;
		this.next = null;
	}

	/**
	 * Obtiene un objeto de tipo T
	 * 
	 * @return objeto tipo T
	 */
	public T getInfoData() {
		return infoData;
	}

	/**
	 * Obtiene el nodo siguiente
	 * 
	 * @return nodo siguiente
	 */
	public Node<T> getNext() {
		return next;
	}

	/**
	 * Asigna un objeto de tipo T al atributo de la clase
	 * 
	 * @param infoData
	 */
	public void setInfoData(T infoData) {
		this.infoData = infoData;
	}

	/**
	 * Asigna un nodo siguiente al atributo de la clase
	 * 
	 * @param next
	 */
	public void setNext(Node<T> next) {
		this.next = next;
	}
}
