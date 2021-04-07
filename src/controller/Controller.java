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
		modelo = new Modelo(200,5);
		view = new View();
		
	}

	public void run() 
	{
		Scanner lector = new Scanner(System.in);
		boolean fin = false;
		ILista<Categoria> categorias = modelo.darCategorias();
		view.printMenu();
		while( !fin ){
			
			int option = lector.nextInt();
			switch(option){
				case 1:
					view.printMessage("Si desea cargar los datos en las dos tablas de Hash ingrese 1: ");
					int carga = lector.nextInt();
					if(carga ==1)
						try
						{
							modelo.cargarDatosTablaSC();
							modelo.cargarDatosTablaLP();
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
					break;
				case 2:
					view.printMessage("Para poder realizar el requerimiento es necesario que ingrese la siguiente informacion: \n  - Nombre del pais:");
					String pais = lector.next();
					view.printMessage("-Nombre de la categoria:\n Recuerde que las categorias son:");
					
					for(int i = 1; i<=categorias.size();i++)
					{
						System.out.println(categorias.getElement(i).toString());
					}
						
					String categoria = lector.next();
					ILista<VideoYoutube> buscados =modelo.requerimientoLP(pais, categoria);
					view.printReq(buscados);
					break;

				case 3:
					view.printMessage("Para poder realizar el requerimiento es necesario que ingrese la siguiente informacion: \n  - Nombre del pais:");
					pais = lector.next();
					view.printMessage("-Nombre de la categoria:\n Recuerde que las categorias son:");
					
					for(int i = 1; i<=categorias.size();i++)
					{
						System.out.println(categorias.getElement(i).toString());
					}
					categoria = lector.next();
					buscados =modelo.requerimientoSC(pais, categoria);
					view.printReq(buscados);
					break;
				case 4:
					long promedioSC = modelo.pruebaMetodoGetSC();
					view.printMessage("El tiempo promedio del metodo get(K) en la tabla con manejo de colisiones con SC en milisegundos fue de:  "+promedioSC);
					long promedioLP = modelo.pruebaMetodoGetLP();
					view.printMessage("El tiempo promedio del metodo get(K) en la tabla con manejo de colisiones con LP en milisegundos fue de:  "+promedioLP);
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
