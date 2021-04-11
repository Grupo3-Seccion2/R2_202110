package model.data_structures;

public class TablaHashSeparateChaining<K extends Comparable<K>, V  extends Comparable<V>> implements ITablaSimbolos <K, V>
{
	private ILista<ILista<NodoTS<K,V>>> listaNodos;
	private int tamanoTabla;
	private int tamanoActual;
	
	private int factorCarga;
	
	public TablaHashSeparateChaining(int tamanoInicial, int factorCarga) 
	{
		listaNodos = new ArregloDinamico<>(nextPrime(tamanoInicial));
		tamanoTabla = tamanoInicial;
		tamanoActual= 0;
		this.factorCarga = factorCarga;
		
		for(int i = 1; i<= tamanoTabla;i++)
		{
			listaNodos.addLast(null);
		}
	}

	@Override
	public void put(K key, V value) 
	{
		int posicion = hash(key);
		ILista<NodoTS<K,V>> listaSC = listaNodos.getElement(posicion);
		if(listaSC != null && !contains(key))
		{
			if(listaSC.size()== factorCarga)
			{
				rehash();
				put(key,value);
			}
			else
			{
				listaSC.addLast(new NodoTS<K, V>(key, value));
				tamanoActual++;
			}
			
		}
		else
		{
			listaNodos.changeElement(posicion, new ArregloDinamico<NodoTS<K,V>>(factorCarga));
			listaNodos.getElement(posicion).addLast(new NodoTS<K,V>(key,value));
			tamanoActual++;
			
		}
		
		
		
	}

	@Override
	public V get(K key) 
	{
		V buscado = null;
		int posicion = hash(key);
		ILista<NodoTS<K,V>> listaSC = listaNodos.getElement(posicion);
		if(listaSC != null)
		{
			for(int i = 1; i <= listaSC.size() && buscado == null; i++)
			{
				if(listaSC.getElement(i).getKey().compareTo(key)==0)
				{
					buscado = listaSC.getElement(i).getValue();
				}
			}
		}
		return buscado;
	}

	@Override
	public V remove(K key) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean contains(K key) 
	{
		boolean esta = false;
		int posicion = hash(key);
		ILista<NodoTS<K,V>> listaSC = listaNodos.getElement(posicion);
		if(listaSC != null)
		{
			for(int i = 1; i <= listaSC.size() && !esta; i++)
			{
				if(listaSC.getElement(i).getKey().compareTo(key)==0)
				{
					esta = true;
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
	public int size() {
		return tamanoActual;
	}

	@Override
	public ILista<K> keySet() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ILista<V> valueSet() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void changeValue(K key, V value)
	{
		boolean cambio = false;
		int posicion = hash(key);
		ILista<NodoTS<K,V>> listaSC = listaNodos.getElement(posicion);
		if(listaSC != null)
		{
			for(int i = 1; i <= listaSC.size() && !cambio; i++)
			{
				if(listaSC.getElement(i).getKey().compareTo(key)==0)
				{
					listaSC.getElement(i).setValue(value);
				}
			}
		}

	}
	
	public int hash(K key)
	{
		return Math.abs(key.hashCode()%tamanoTabla)+1;
	}
	
	 //Rehash
	private void rehash()
	{
		System.out.println("Rehash");
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
			ILista<NodoTS<K,V>> actual = listaNodos.getElement(i);
			if(actual !=null)
			{
				for(int j = 1; j <= actual.size();j++)
				{
					nodos.addLast(actual.getElement(j));
				}
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
