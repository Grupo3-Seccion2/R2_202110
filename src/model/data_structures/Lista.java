package model.data_structures;

public class Lista <T extends Comparable<T>> implements ILista<T>
{

	private Nodo<T> first;

	private Nodo<T> last;
	private int size;
	
	public Lista()
	{
		first = null;
		last = null;
		size = 0;
	}
	
	public Lista(T elem)
	{
		first = new Nodo<T>(elem);
	}
	@Override

	public void addFirst(T elem) 
	{
		Nodo<T> nuevo = new Nodo<T>(elem);
		if (first == null)
		{
			first = nuevo;
			last = nuevo;
		}
		else
		{
			nuevo.cambiarSiguiente(first);
			first = nuevo;
		}
		size++;

	}

	@Override
	public void addLast(T elem) 
	{
		Nodo<T> nuevo = new Nodo<T>(elem);
		if (first == null)
		{
			first = nuevo;
	
		}
		else
		{
			last.cambiarSiguiente(nuevo);
		}
		last = nuevo;
		size++;
	}

	@Override
	public void insertElement(T elem, int pos) 
	{
		
		if(pos== size)
		{
			addLast(elem);
		}
		else if(pos == 1)
		{
			addFirst(elem);
			
		}
		else
		{
			Nodo<T> nuevo = new Nodo<T>(elem);
			Nodo<T> anterior = first;
			int i = 1;
			
			while(anterior!= null && i!= pos)
			{
				anterior = anterior.darSiguiente();
				i++;
			}
			
			if(anterior!=null)
			{
			nuevo.cambiarSiguiente(anterior.darSiguiente());
			anterior.cambiarSiguiente(nuevo);
			}
			size++;
		}
		
	}

	@Override
	public T removeFirst() 
	{
		Nodo<T> eliminado = first;
		if(first != null)
		{
			Nodo<T> nuevoPrimero = first.darSiguiente();
			first = nuevoPrimero;
		}	
		size--;
		return eliminado.darElemento() ;
	}

	@Override
	public T removeLast() 
	{
		Nodo<T> eliminado = last;
		if(last != null)
		{ 
			last = null;
		}	
		size--;
		return eliminado.darElemento() ;
	}

	@Override
	public T deleteElement(int pos) 
	{
		
		Nodo<T> eliminado = null;
		if(pos== size)
		{
			eliminado = last;
			removeLast();
		}
		else if(pos == 1)
		{
			eliminado = first;
			removeFirst();
			
		}
		else
		{
			Nodo<T> anterior = first;
			int i = 1;
			
			while(anterior!= null && i!= pos)
			{
				anterior = anterior.darSiguiente();
				i++;
			}
			eliminado = anterior.darSiguiente();
			anterior.cambiarSiguiente(anterior.darSiguiente().darSiguiente());
			size--;
		}
		
		return eliminado.darElemento();
	}

	@Override
	public T firstElement() 
	{
		if(size != 0)
		{
			return first.darElemento();
		}
		else
			return null;
	}

	@Override
	public T lastElement() 
	{
		if(size != 0)
		{
			return last.darElemento();
		}
		else
		{
			return null;
		}
	}

	@Override
	public T getElement(int pos) 
	{
		T buscado = null;
		if(pos ==1)
			buscado = first.darElemento();
		else if (pos == size)
			buscado = last.darElemento();
		else
		{
			Nodo<T> anterior = first;
			int i = 1;
		
			while(anterior.darSiguiente()!= null && i!= pos-1)
			{
				anterior = anterior.darSiguiente();
				i++;
			}
			if(anterior.darSiguiente() != null)
				buscado = anterior.darSiguiente().darElemento();
		}
		return buscado;
	}

	@Override
	public int size() 
	{
		
		return size;
	}


	@Override
	public boolean isEmpty() 
	{
		
		return size == 0;
	}

	@Override
	public boolean isPresent(T element) 
	{
		Nodo<T> actual = first;
		boolean present = false;
		int i = 1;
		while(actual!= null && !present)
		{
			if (actual.darElemento().compareTo(element)==0)
				present = true;
			actual = actual.darSiguiente();
			i++;
			
		}
		return present;
	}
	
	@Override
	public ILista<T> sublista(int numElementos) 
	{
		Lista<T> subLista = new Lista<>(); 
		boolean termino = false;
		for (int i = 1; i<= numElementos && !termino; i++)
		{
			
			subLista.addLast(this.getElement(i));
		}
		return subLista;
	}
	
	
	
	
	
	
	
	

	@Override
	public void exchange(int pos1, int pos2) 
	{ 
		T Epos1 = getElement(pos1) ;
		T Epos2 = getElement(pos2) ;
		
		changeElement(pos2, Epos1);
		changeElement(pos1, Epos2);
		
	}

	@Override
	public void changeElement(int pos, T elem) 
	{
		Nodo<T> nuevo = new Nodo<T>(elem);
		Nodo<T> anterior = first;
		if(pos ==1)
		{
			Nodo<T> temporal = first.darSiguiente();
			nuevo.cambiarSiguiente(temporal);
			first = nuevo;
		}
		else
		{
			int i = 1;
			while(anterior.darSiguiente()!= null&& i != pos-1)
			{
				anterior = anterior.darSiguiente();
				i++;
			}
			if(anterior.darSiguiente().darSiguiente()==null)
			{
				anterior.cambiarSiguiente(nuevo);
			}
			else
			{
				Nodo<T> siguiente = anterior.darSiguiente().darSiguiente();
				nuevo.cambiarSiguiente(siguiente);
				anterior.cambiarSiguiente(nuevo);
			}
		}
		
	}

	@Override
	public ILista<T> subListaPos(int pos, int sizeSub) 
	{
		Lista<T> subLista = new Lista<>(); 
		for (int i = 0; i< (sizeSub); i++)
		{
				subLista.addLast(getElement(pos+i));
		}
		return subLista;
	}

	@Override
	public int compareTo(ILista<T> o) {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
