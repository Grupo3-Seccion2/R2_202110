package model.data_structures;

public class NodoTS<K extends Comparable<K>, V> implements Comparable<NodoTS<K,V>>
{

	NodoTS<K,V> next;
    K key;
    V value;

    public NodoTS(K key, V value) 
    {
        this.key = key;
        this.value = value;
    }

    public K getKey() 
    {
        return key;
    }

    public void setKey(K key) 
    {
        this.key = key;
    }

    public void setValue(V value) 
    {
        this.value = value;
    }

    public V getValue() 
    {
        return value;
    }

    public NodoTS<K, V> getNext() 
    {
        return next;
    }

    public void setNext(NodoTS<K, V> next) 
    {
        this.next = next;
    }

    public boolean hasNext()
    {
        return next != null;
    }

    public void cleanNext()
    {
        this.next = null;
    }

    public void disconectNext()
    {
        if(next != null){
            this.next = (this.next.hasNext()) ? this.next.next : null;
        }
    }

    public void setEmpty()
    {
    	this.key = null;
    }
    public boolean isEmpty()
    {
    	return this.key == null;
    }
    @Override
    public int compareTo(NodoTS<K, V> o) 
    {
        return key.compareTo(o.key);
    }

}
