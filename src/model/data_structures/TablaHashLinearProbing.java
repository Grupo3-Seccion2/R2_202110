package model.data_structures;

public class TablaHashLinearProbing<K extends Comparable<K>, V extends Comparable<V>> implements ITablaSimbolos <K, V>
{
	private ILista<NodoTS<K,V>> listaNodos;
	private int tamanoTabla;
	private int tamanoActual;
	
	public TablaHashLinearProbing(int tamanoInicial) 
	{
		tamanoTabla =  tamanoInicial;
		listaNodos = new ArregloDinamico<>(nextPrime(tamanoTabla));
		tamanoActual = 0;
		
		for(int i = 1; i<= tamanoTabla;i++)
		{
			listaNodos.addLast(null);
		}
	}
	
	@Override
	public void put(K key, V value) 
	{
		int posicion = hash(key);
		NodoTS<K,V> nodo = listaNodos.getElement(posicion);
		if(nodo != null && !nodo.isEmpty())
		{
			posicion = getNextEmpty(posicion);
		}
		listaNodos.changeElement(posicion, new NodoTS<>(key, value));
		tamanoActual ++;
	}

	@Override
	public V get(K key)
	{
		int posicion = hash(key);
		V retornar = null;
		boolean encontroNull = false;
		while(retornar == null && !encontroNull)
		{
			NodoTS<K, V> actual = listaNodos.getElement(posicion);
			if(actual== null)
			{
				encontroNull = true;
			}
			else if(actual.getKey().compareTo(key)==0 && !actual.isEmpty())
			{
				retornar = actual.getValue();
			}
			else
			{
				posicion ++;
				if(posicion>tamanoTabla)
				{
					posicion = 1;
				}
			}
		}
		return retornar;
	}

	@Override
	public V remove(K key) 
	{
		int posicion = hash(key);
		V retornar = null;
		boolean encontroNull = false;
		while(retornar == null && !encontroNull)
		{
			NodoTS<K, V> actual = listaNodos.getElement(posicion);
			if(actual== null)
			{
				encontroNull = true;
			}
			else if(actual.getKey().compareTo(key)==0 && !actual.isEmpty())
			{
				retornar = actual.getValue();
			}
			else
			{
				posicion ++;
				if(posicion>tamanoTabla)
				{
					posicion = 1;
				}
			}
		}
		if(retornar != null)
		{
			listaNodos.getElement(posicion).setEmpty();
		}
		return retornar;
	}

	@Override
	public boolean contains(K key) 
	{
		int posicion = hash(key);
		boolean esta = false;
		boolean encontroNull = false;
		while(!esta && !encontroNull)
		{
			NodoTS<K, V> actual = listaNodos.getElement(posicion);
			if(actual== null)
			{
				encontroNull = true;
			}
			else if(actual.getKey().compareTo(key)==0 && !actual.isEmpty())
			{
				esta = true;
			}
			else
			{
				posicion ++;
				if(posicion>tamanoTabla)
				{
					posicion = 1;
				}
			}
		}
		return esta;
	}

	@Override
	public boolean isEmpty() 
	{
		
		return listaNodos.isEmpty();
	}

	@Override
	public int size() 
	{
		return tamanoActual;
	}

	@Override
	public ILista<K> keySet() 
	{
		ILista<K> llaves = new ArregloDinamico<>(tamanoActual);
		for(int i = 1; i<= tamanoTabla;i++)
		{
			if(listaNodos.getElement(i)!=null)
			{
				llaves.addLast(listaNodos.getElement(i).getKey());
			}
		}
		return llaves;
	}

	@Override
	public ILista<V> valueSet() 
	{
		ILista<V> valores = new ArregloDinamico<>(tamanoActual);
		for(int i = 1; i<= tamanoTabla;i++)
		{
			if(listaNodos.getElement(i)!=null)
			{
				valores.addLast(listaNodos.getElement(i).getValue());
			}
				
		}
		return valores;
	}
	
	public void changeValue(K key, V value)
	{
		int posicion = hash(key);
		boolean esta = false;
		boolean encontroNull = false;
		while(!esta && !encontroNull)
		{
			NodoTS<K, V> actual = listaNodos.getElement(posicion);
			if(actual== null)
			{
				encontroNull = true;
			}
			else if(actual.getKey().compareTo(key)==0 && !actual.isEmpty())
			{
				actual.setValue(value);
				esta = true;
			}
			else
			{
				posicion ++;
				if(posicion>tamanoTabla)
				{
					posicion = 1;
				}
			}
		}
	}

	@Override
	public int hash(K key)
	{
		return Math.abs(key.hashCode()%tamanoTabla)+1;
	}
	

	private int getNextEmpty(int posicion)
	{
		int pos = posicion+1;
		while(listaNodos.getElement(pos)!= null && !listaNodos.getElement(pos).isEmpty())
		{
			pos++;
			if(pos >tamanoTabla)
			{
				pos = 1;
			}
		}
		return pos;
	}
	
	private void rehash()
	{
		ILista<NodoTS<K,V>> nodos = darNodos();
		tamanoActual = 0;
		tamanoTabla = nextPrime(tamanoTabla);
		listaNodos = new ArregloDinamico<>(tamanoTabla);
		
		for(int i = 1; i<= tamanoTabla;i++)
		{
			listaNodos.addLast(null);
		}
		
		NodoTS<K, V> actual;
		while((actual = nodos.removeFirst())!= null)
		{
			put(actual.getKey(),actual.getValue());
		}
	}
	
	private ILista<NodoTS<K, V>> darNodos()
	{
		ILista<NodoTS<K,V>> nodos = new Lista<>();
		for(int i = 1; i<= listaNodos.size();i++)
		{
			NodoTS<K,V> actual =listaNodos.getElement(i);
			if(actual !=null)
			{
				nodos.addLast(actual);
			}
		}
		return nodos;
	}

   //Funciones para calcular el siguiente primo

    static boolean isPrime(int n)

    {

        // Corner cases

        if (n <= 1)
        	return false;

        if (n <= 3) 
        	return true;

         

        // This is checked so that we can skip

        // middle five numbers in below loop

        if (n % 2 == 0 || n % 3 == 0) 
        	return false;

         

        for (int i = 5; i * i <= n; i = i + 6)
        {

            if (n % i == 0 || n % (i + 2) == 0)

            	return false;

        }

        return true;

    }
    
    // Function to return the smallest

    // prime number greater than N

    static int nextPrime(int N)

    {

        // Base case

        if (N <= 1)

            return 2;

     

        int prime = N;

        boolean found = false;

     

        // Loop continuously until isPrime returns

        // true for a number greater than n

        while (!found)

        {

            prime++;

            if (isPrime(prime))

                found = true;

        }

     

        return prime;

    }

}
