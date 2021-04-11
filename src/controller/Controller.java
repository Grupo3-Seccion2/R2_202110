package controller;

import java.text.ParseException;
import java.util.Comparator;
import java.util.Scanner;

import model.data_structures.ILista;
import model.logic.Categoria;
import model.logic.Modelo;
import model.logic.VideoYoutube;
import model.utils.Ordenamiento;
import view.View;

public class Controller {

	/**
	 *  Instancia del Modelo
	 */
	private Modelo modelo;
	
	/**
	 *  Instancia de la Vista
	 */
	private View view;
	
	/**
	 * Crear la vista y el modelo del proyecto
	 */
	public Controller ()
	{
		modelo = new Modelo(100,5);
		view = new View();
		
	}

	public void run() 
	{
		Scanner lector = new Scanner(System.in);
		boolean fin = false;
		ILista<Categoria> categorias = modelo.darCategorias();
		
		while( !fin ){
			view.printMenu();
			int option = lector.nextInt();
			switch(option){
				case 1:
					view.printMessage("Para poder realizar el requerimiento es necesario que ingrese la siguiente informacion: \n  - Nombre del pais:");
					String pais = lector.next();
					view.printMessage("-Nombre de la categoria:\n Recuerde que las categorias son:");
					
					for(int i = 1; i<=categorias.size();i++)
					{
						System.out.println(categorias.getElement(i).toString());
					}
						
					String categoria = lector.next();
					view.printMessage("-Cantidad de elementos a listar:");
					int n = lector.nextInt();
					ILista<VideoYoutube> buscados = modelo.requerimiento1(pais, categoria, n);
					view.printReq1(buscados);
					break;

				case 2:
					view.printMessage("Para poder realizar el requerimiento es necesario que ingrese la siguiente informacion: \n  - Nombre del pais:");

				
					String pais1 = lector.next();
					VideoYoutube buscado = modelo.requerimiento2(pais1);
					
					String pais2 = lector.next();
					VideoYoutube buscadoP = modelo.requerimiento2(pais2);
					view.printReq2(buscadoP);

					break;

				case 3:

					view.printMessage("Para poder realizar el requerimiento es necesario que ingrese la siguiente informacion: \n  - Nombre de la categoria:");
				
					for(int i = 1; i<=categorias.size();i++)
					{
						System.out.println(categorias.getElement(i).toString());
					}
					String cat = lector.next();
					VideoYoutube buscadoC = modelo.requerimiento3(cat);
					view.printReq3(buscadoC);
					break;
				
				case 4:
					view.printMessage("-Cantidad de elementos a listar:");
					int n4 = lector.nextInt();
					view.printMessage("-Tag de interes:");
					String tag= lector.next();
					ILista<VideoYoutube> buscadosT = modelo.requerimiento4(tag, n4);
					System.out.println("Acabo");
					view.printReq4(buscadosT);
					break;
	
				
				case 5: 
					view.printMessage("--------- \n Hasta pronto !! \n---------"); 
					lector.close();
					fin = true;
					break;
					
					
				default: 
					view.printMessage("--------- \n Opcion Invalida !! \n---------");
					break;
			}
		}

		
	}
}
