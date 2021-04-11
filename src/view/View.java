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
			
			System.out.println("\n1. Requerimiento 1");
			System.out.println("2. Requerimiento 2");
			System.out.println("3. Requerimiento 3");
			System.out.println("4. Requerimiento 4");
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
		public void printReq1(ILista<VideoYoutube> lista)
		{
			System.out.println("Los resultados de la busqueda son:");
			for(int i = 1;i<= lista.size();i++)
			{
				System.out.println(lista.getElement(i).requerimiento1());
			}
				
		}
		 /**
	     * Metodo que imprime en la consola los resultados en el formato deseado para el requerimiento 4
	     * @param lista. Lista con los videos resultantes de la busqueda 
	     */
		public void printReq4(ILista<VideoYoutube> lista)
		{
			System.out.println("Los resultados de la busqueda son:");
			for(int i = 1;i<= lista.size();i++)
			{
				System.out.println(lista.getElement(i).requerimiento4());
			}
				
		}
		
		 /**
	     * Metodo que imprime en la consola el resultado en el formato deseado para el requerimiento 2
	     * @param video. Video resultante de la busqueda 
	     */
		public void printReq2(VideoYoutube video)
		{
			if(video == null)
			{
				System.out.println("No se encuentran videos por el pais ingresado");
			}
			else
			{
				System.out.println("Los resultados de la busqueda son:");
				System.out.println(video.requerimiento2());
			}
		}
		
		/**
	     * Metodo que imprime en la consola el resultado en el formato deseado para el requerimiento 3
	     * @param video. Video resultante de la busqueda 
	     */
		public void printReq3(VideoYoutube video)
		{
			if(video == null)
			{
				System.out.println("No se encuentran videos por la categoria ingresada");
			}
			else
			{
				System.out.println("Los resultados de la busqueda son:");
				System.out.println(video.requerimiento3());
			}
		}

		
}
