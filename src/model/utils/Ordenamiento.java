package model.utils;

import java.util.Comparator;

import model.data_structures.ILista;

public final class Ordenamiento <T extends Comparable<T>>
{
	// Ordenamientos 
	public void ordenarInsercion(ILista<T> lista, Comparator<T> criterio, boolean ascendente)
	{
		for (int i = 2;i<=lista.size();i++)
		{
			boolean enPosicion =false;
			for (int j = i;j>1&& !enPosicion;j--)
			{
				int factorComparacion = (ascendente?1:-1)*criterio.compare(lista.getElement(j), lista.getElement(j-1));
				if(factorComparacion <0)
				{
					lista.exchange(j, j-1);
				}
				else
					enPosicion = true;
			}
		}
	}
	
	public void ordenarShell(ILista<T> lista, Comparator<T> criterio, boolean ascendente)
	{
		int N = lista.size();
		int h = 1;
		
		while(h < N/3)
		{
			h = 3*h + 1;
		}
		while(h >= 1)
		{
			for(int i = h+1; i<=N ; i++)
			{
				boolean enPosicion = false;
				for(int j = i; j>h && !enPosicion;j-=h)
				{
					int factorComparacion = (ascendente?1:-1)*criterio.compare(lista.getElement(j), lista.getElement(j-h));
					if(factorComparacion <0)
					{
						lista.exchange(j, j-h);
					}
					else
						enPosicion = true;
				}
			}
			h /= 3;
		}
		
	}
	

	
	// Ordenamientos Recursivos
	
	public final void ordenarQuickSort(ILista<T>lista, Comparator<T> criterio,boolean ascendente)
	{
		sort(lista,criterio,ascendente,1,lista.size());
	}
	
	public final void ordenarMergeSort(ILista<T> lista, Comparator<T> criterio, boolean ascendente)
	{
		int size = lista.size();
		if(size >1)
		{
			int mid = (int) (size/2);
			
			ILista<T> leftList = lista.subListaPos(1,mid);
			ILista<T> rightList = lista.subListaPos(mid+1, size-mid);
			
			ordenarMergeSort(leftList,criterio,ascendente);
			ordenarMergeSort(rightList, criterio,ascendente);
			
			int i,j,k;
			i =j=k= 1;
			
			int leftelements = leftList.size();
			int rightelements = rightList.size();
			
			while(i<= leftelements && j <= rightelements)
			{
				T elemi = leftList.getElement(i);
				T elemj = rightList.getElement(j);
				
				int factorComparacion = (ascendente?1:-1)*criterio.compare(elemi,elemj);
				
				if(factorComparacion <=0)
				{
					lista.changeElement(k, elemi);
					i++;
				}
				else
				{
					lista.changeElement(k, elemj);
					j++;
				}
				k++;
				
			}
			
			while(i<= leftelements)
			{
				lista.changeElement(k, leftList.getElement(i));
				i++;
				k++;
			}
			while(j<= rightelements)
			{
				lista.changeElement(k, rightList.getElement(j));
				j++;
				k++; 
			}
			
		}
	}
	
	private final int partition(ILista<T> lista, Comparator<T> criterio ,boolean ascendente, int lo, int hi )
	{
		int follower,leader;
		follower = leader = lo;
		while(leader<hi)
		{
			int factorComparacion = (ascendente?1:-1)*criterio.compare(lista.getElement(leader), lista.getElement(hi));
			if(factorComparacion <0)
			{
				lista.exchange(follower, leader);
				follower++;
			}
			leader++;
		}
		lista.exchange(follower, hi);
		return follower;
	}
	
	private final void sort(ILista<T> lista, Comparator<T> criterio ,boolean ascendente,int lo,int hi)
	{
		if(lo>= hi)
			return;
		int pivot = partition(lista, criterio, ascendente, lo, hi);
		sort(lista,criterio,ascendente,lo,pivot-1);
		sort(lista,criterio,ascendente,pivot+1,hi);
	}
}
