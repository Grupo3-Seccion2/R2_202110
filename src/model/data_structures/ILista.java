package model.data_structures;

public interface ILista <T extends Comparable<T>> extends Comparable<ILista<T>>
{
	public void addFirst(T elem);
	
	public void addLast(T elem);
	
	public void insertElement(T elem, int pos);
	
	public T removeFirst();
	
	public T removeLast();
	
	public T deleteElement(int pos);
	
	public T firstElement();
	
	public T lastElement();
	
	public T getElement(int pos);
	
	public int size();
	
	public boolean isEmpty();
	
	public boolean isPresent(T element);
	
	public void exchange(int pos1, int pos2);
	
	public void changeElement(int pos, T elem);
	
	/**
	* Crear una sublista de la lista original (this).
	* Los elementos se toman en el mismo orden como aparecen en la lista original (this).
	* @param número de elementos que contendrá la sublista. Si el número es superior al tamaño
	 
	* original de la lista, se obtiene una copia de la lista original.
	* @return sublista creada con la misma representación de la lista original (this).
	*/
	public ILista<T> sublista(int numElementos);
	
	/**
	* Retorna una sublista con los elementos desde pos hasta la cantidad solicitada.
	* @param pos Posición desde la que se quiere obtener la sublista.
	* @param size Tamaño de la sublista.
	* @return Una nueva lista con los elementos solicitados.
	*/
	public ILista<T> subListaPos(int pos, int size);
}
