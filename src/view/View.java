package view;

import model.data_structures.ILista;
import model.logic.Modelo;
import model.logic.VideoYoutube;

public class View 
{
	    /**
	     * Metodo constructor
	     */
	    public View()
	    {
	    	
	    }
	    
		public void printMenu()
		{
			System.out.println("1. Cargar datos de videos");
			System.out.println("2. Realizar Requerimiento 1 con tablas de simbolos por LinearProbing");
			System.out.println("3. Realizar Requerimiento 1 con tablas de simbolos por SeparateChaining");
			System.out.println("4. Buscar y analizar el metodo get");
			System.out.println("5. Exit");
			System.out.println("Dar el numero de opcion a resolver, luego oprimir tecla Return: (e.g., 1):");
		}
		
		 /**
	     * Metodo que imprime en la consola un mensaje deseado ingresado por el ususario
	     * @param mensaje. Cadena de caracteres que se quiere imprimir 
	     */
		public void printMessage(String mensaje) 
		{

			System.out.println(mensaje);
		}		
		
		 /**
	     * Metodo que imprime en la consola los resultados en el formato deseado para el requerimiento 1
	     * @param lista. Lista con los videos resultantes de la busqueda 
	     */
		public void printReq(ILista<VideoYoutube> lista)
		{
			if(lista.isEmpty())
			{
				System.out.println("No hay videos asociados a la combinacion de pais y categoria ingresados!! ");
			}
			else
			{
				System.out.println("Los resultados de la busqueda son:");
				for(int i = 1;i<= lista.size();i++)
				{
					System.out.println(lista.getElement(i).requerimiento1());
				}
			}
				
		}
		
}
