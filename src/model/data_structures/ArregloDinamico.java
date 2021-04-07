package model.data_structures;

/**
 * 2019-01-23
 * Estructura de Datos Arreglo Dinamico de Strings.
 * El arreglo al llenarse (llegar a su maxima capacidad) debe aumentar su capacidad.
 * @author Fernando De la Rosa
 *
 */
public class ArregloDinamico <T extends Comparable<T>> implements ILista<T> 
{
		/**
		 * Capacidad maxima del arreglo
		 */
        private int tamanoMax;
		/**
		 * Numero de elementos presentes en el arreglo (de forma compacta desde la posicion 0)
		 */
        private int tamanoAct;
        /**
         * Arreglo de elementos de tamaNo maximo
         */
        private T elementos[ ];

        /**
         * Construir un arreglo con la capacidad maxima inicial.
         * @param max Capacidad maxima inicial
         */
		public ArregloDinamico( int max )
        {
               elementos = (T[]) new Comparable[max];
               tamanoMax = max;
               tamanoAct = 0;
        }

		public T buscar(T dato) 
		{
			T respuesta = null;
			for(int i = 1; i <= tamanoAct; i ++ ) 
			{
				if (elementos[i].compareTo(dato)==0)
					respuesta = elementos[i];
			}
			return respuesta;
		}


		@Override
		public void addFirst(T elem)
		{
			T[] copia = elementos;
			
			if(isEmpty())
				elementos[1] = elem;
			else
			{
				elementos[1] = elem;
				for(int i = 1; i <= tamanoAct; i++)
				{
					elementos[i+1] = copia[i];
				}
			}
			tamanoAct++;
			
		}


		@Override
		public void addLast(T elem) 
		{
			if ( (tamanoAct+1) == tamanoMax )
            {  
                 tamanoMax = 2 * tamanoMax;
                 T [ ] copia = elementos;
                 elementos = (T[])new Comparable[tamanoMax];
                 for ( int i = 1; i <= tamanoAct; i++)
                 {
                  	 elementos[i] = copia[i];
                 } 
            }
			if(tamanoAct==0)
				elementos[1]=elem;
			else
				elementos[tamanoAct+1] = elem;
            tamanoAct++;
		}


		@Override
		public void insertElement(T elem, int pos) 
		{
			if (pos ==1)
				addFirst(elem);
			else if(pos==tamanoAct)
				addLast(elem);
			else
			{
				T[] copia = elementos;
				elementos[pos] = elem;
				for(int i = pos; i <= tamanoAct; i++)
				{
					elementos[i+1] = copia[i];
				}
				tamanoAct++;
			}
		
		}


		@Override
		public T removeFirst() 
		{
			T[] copia = elementos;
			if(!isEmpty())
			{
				for(int i = 1; i <= tamanoAct; i++ )
				{
					elementos[i+1] = copia[i];
				}
			}
			return copia[1];
		}


		@Override
		public T removeLast() 
		{
			T[] copia = elementos;
			T elem = elementos[tamanoAct];
			return elem;
		}


		@Override
		public T deleteElement(int pos) 
		{
			T[] copia = elementos;
			T elem = null;
			for ( int i = 1; i <=tamanoAct; i++)
			{
				if(i == pos)
				{
					elem = copia[i];
					elementos[i] = null;
				}
			}
			return elem;
		}


		@Override
		public T firstElement() 
		{
			return elementos[1];
		}


		@Override
		public T lastElement() 
		{
			return  elementos[tamanoAct];
		}


		@Override
		public T getElement(int pos) 
		{
			return elementos[pos];
		}


		@Override
		public int size() 
		{
			return tamanoAct;
		}



		@Override
		public boolean isPresent(T element) 
		{
			T actual;
			boolean presente = false;
			for(int i = 1; i <=tamanoAct &&!presente; i ++ )
			{
				actual = elementos[i];
				if(actual.compareTo(element) == 0)
				{
					presente = true;
				}
			}
			return presente;
		}


		@Override
		public void exchange(int pos1, int pos2) 
		{
			T elem1 = null;
			T elem2 = null;
			
			if(tamanoAct != 0)
			{
				elem1 = getElement(pos1);
				elem2 = getElement(pos2);
				elementos[pos1]= elem2;
				elementos[pos2]=elem1;
			}
			
		}


		@Override
		public void changeElement(int pos, T elem)
		{
			if(!isEmpty())
			{
				elementos[pos]= elem;
			}
				
			
		}

		

		@Override
		public boolean isEmpty() 
		{
			return tamanoAct == 0;
		}

		@Override
		public ILista<T> sublista(int numElementos) 
		{
			ArregloDinamico<T> subLista = new ArregloDinamico<T>(numElementos+1);
			for(int i = 1; i <=numElementos; i++)
			{	
				subLista.insertElement(elementos[i], i);
			}
			return subLista;
		}

		@Override
		public ILista<T> subListaPos(int pos, int size) 
		{
			ArregloDinamico<T> subLista = new ArregloDinamico<T>(size+1);
			int j = 1;
			for(int i =pos; i<=size;i++)
			{
				subLista.insertElement(elementos[i], j);
				j++;
			}
			return subLista;
		}

		@Override
		public int compareTo(ILista<T> o) {
			// TODO Auto-generated method stub
			return 0;
		}



}
