import java.util.Comparator;

/**
 * Clase MyQueue, crea una lista de tipo cola en la que el primer elemento que
 * entra es el primero que sale
 * 
 * @version 1.0 - 05/09/2018
 * @author Fabian Alejandro Cristancho Rincon
 *
 * @param <T> objeto de tipo generico
 */

public class MyQueue<T> {

	private Node<T> first;
	private Node<T> last;
	private Comparator<T> comparator;

	/**
	 * Constructor que inicializa el primer elemento y ultimo en nulo
	 */
	public MyQueue() {
		this.first = null;
		this.last = null;
	}

	/**
	 * Constructor que recibe un comparador y lo iguala al de la clase
	 */
	public MyQueue(Comparator<T> comparator) {
		this.comparator = comparator;
		this.first = null;
		this.last = null;
	}

	/**
	 * Constructor al que se le pasa por parametro una cola e inicializa sus
	 * elementos con los de la clase
	 * 
	 * @param myQueue
	 */
	public MyQueue(MyQueue<T> myQueue) {
		this.first = myQueue.first;
		this.last = myQueue.last;
	}

	/**
	 * Determina si la cola esta vacia
	 * 
	 * @return true si la cola esta vacia
	 */
	public boolean isEmpty() {
		return this.first == null;
	}

	/**
	 * Agrega un nuevo elemento a la cola
	 * 
	 * @param info Nuevo elemento tipo T
	 */
	public void addElement(T info) {
		if (!this.isEmpty()) {
			this.last.next = new Node<>(info);
			this.last = this.last.next;

		} else {
			this.first = new Node<>(info);
			this.last = this.first;
		}
	}

	/**
	 * Obtiene el primer elemento de la cola
	 * 
	 * @return primer elemento de la cola, en caso de que no este vacia
	 */
	public T getElement() {
		if (!this.isEmpty()) {
			T aux = this.first.infoData;
			this.first = this.first.next;
			return aux;
		}
		return null;
	}

	/**
	 * Se encarga de realizar la particion de la cola principal en dos colas
	 * auxiliares
	 * 
	 * @return Cola original fusionada a partir de las dos colas auxiliares
	 */
	private MyQueue<T> partition() {
		MyQueue<T> aux1 = new MyQueue<>();
		MyQueue<T> aux2 = new MyQueue<>();
		Node<T> nodeAux;
		aux1.addElement(this.getElement());
		nodeAux = aux1.first;
		while (!this.isEmpty()) {
			if (nodeAux == aux1.first) {
				if (comparator.compare(this.first.infoData, aux1.last.infoData) >= 0) {
					aux1.addElement(this.getElement());
					nodeAux = aux1.first;
				} else {
					aux2.addElement(this.getElement());
					nodeAux = aux2.first;
				}
			} else {
				if (comparator.compare(this.first.infoData, aux2.last.infoData) > 0) {
					aux2.addElement(this.getElement());
					nodeAux = aux2.first;
				} else {
					aux1.addElement(this.getElement());
					nodeAux = aux1.first;
				}
			}
		}
		return fusion(aux1, aux2);
	}

	/**
	 * Se encarga de fusionar dos colas auxiliares en la cola principal, siguiendo
	 * un orden determinado
	 * 
	 * @param aux1 Primera cola auxiliar
	 * @param aux2 Segunda cola auxiliar
	 * @return Cola principal fusionada
	 */
	private MyQueue<T> fusion(MyQueue<T> aux1, MyQueue<T> aux2) {
		while (!aux1.isEmpty() && !aux2.isEmpty()) {
			if (comparator.compare(aux1.first.infoData, aux2.first.infoData) <= 0) {
				this.addElement(aux1.getElement());
			} else {
				this.addElement(aux2.getElement());
			}
		}
		if (!aux1.isEmpty()) {
			while (!aux1.isEmpty()) {
				this.addElement(aux1.getElement());
			}
		} else {
			while (!aux2.isEmpty()) {
				this.addElement(aux2.getElement());
			}
		}
		return this;
	}

	/**
	 * Determina si los elementos de la cola principal ya se encuentran en orden,
	 * para el caso de menor a mayor
	 * 
	 * @return true si todos los elementos de la cola estan ordenados
	 */
	private boolean isQueueInOrder() {
		Node<T> aux = this.first;
		while (aux != last) {
			if (comparator.compare(aux.infoData, aux.next.infoData) <= 0) {
				aux = aux.next;
			} else {
				return false;
			}
		}
		return true;
	}

	/**
	 * Metodo sortMerge, que reune una serie de metodos con el fin de implementar el
	 * metodo de ordenamiento sortMerge (particion/fusion)
	 * 
	 * @return
	 */
	public MyQueue<T> sortMerge() {
		if (comparator != null) {
			while (!isQueueInOrder()) {
				partition();
			}
		}
		return this;
	}
}
