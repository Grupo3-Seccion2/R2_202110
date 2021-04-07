package model.data_structures;

public class TablaSimbolos <K extends Comparable<K>, V extends Comparable<V>> implements ITablaSimbolos<K, V>
{
	private ILista<NodoTS<K,V>> listaNodos;
	
	public TablaSimbolos()
	{
		listaNodos = new ArregloDinamico<>(10);
	}

	@Override
	public void put(K key, V value) 
	{
		if(contains(key))
		{
			boolean cambio = false;
			for(int i=1;i<= listaNodos.size() && !cambio;i++)
			{
				if(listaNodos.getElement(i).getKey().compareTo(key)==0)
				{
					listaNodos.getElement(i).setValue(value);
					cambio = true;
				}
			}
		}
		else
		{
			NodoTS<K, V> nuevo = new NodoTS<K,V>(key, value);
			listaNodos.addLast(nuevo);	
		}
		
	}

	@Override
	public V get(K key) 
	{
		NodoTS<K,V> buscado = null;
		for(int i = 1;i<= listaNodos.size();i++)
		{
			if(listaNodos.getElement(i).getKey().compareTo(key)==0)
			{
				buscado = listaNodos.getElement(i);
			}
		}
		return buscado==null?null:buscado.value;
	}

	@Override
	public V remove(K key) 
	{

		return null;
	}

	@Override
	public boolean contains(K key) 
	{
		boolean esta = false;
		for(int i = 1;i<= listaNodos.size()&& !esta;i++)
		{
			if(listaNodos.getElement(i).getKey().compareTo(key)==0)
			{
				esta = true;
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
		return listaNodos.size();
	}

	@Override
	public ILista<K> keySet() 
	{
		ILista<K> llaves = new ArregloDinamico<>(listaNodos.size());
		for(int i = 1; i<= listaNodos.size();i++)
		{
			llaves.addLast(listaNodos.getElement(i).getKey());
		}
		return llaves;
	}

	@Override
	public ILista<V> valueSet()
	{
		ILista<V> valores = new ArregloDinamico<>(listaNodos.size());
		for(int i = 1; i<= listaNodos.size();i++)
		{
			valores.addLast(listaNodos.getElement(i).getValue());
		}
		return valores;
	}

	@Override
	public int hash(K key) {
		// TODO Auto-generated method stub
		return 0;
	}

}
