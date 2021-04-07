package model.data_structures;

public class Nodo <T extends Comparable<T>>
{
	private T element;
	private Nodo<T> next;
	
	public Nodo(T e)
	{

		element = e; 
		next = null;

		this.element = e; 

	}
	
	public void cambiarElemento(T elem)
	{
		this.element = elem;
	}
	
	public Nodo<T> darSiguiente()
	{
		return next;
	}
	
	public T darElemento()
	{
		return element;
	}
	
	public void cambiarSiguiente(Nodo<T> sig)
	{
		next = sig;
	}
}
