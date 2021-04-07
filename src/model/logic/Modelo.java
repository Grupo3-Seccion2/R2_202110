package model.logic;

import java.io.FileReader;
import java.io.Reader;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import model.data_structures.ArregloDinamico;
import model.data_structures.ILista;
import model.data_structures.TablaHashLinearProbing;
import model.data_structures.TablaHashSeparateChaining;
import model.data_structures.TablaSimbolos;
import model.utils.Ordenamiento;

 
/**
 * Definicion del modelo del mundo
 *
 */
public class Modelo <T extends Comparable<T>>
{
	/**
	 * Atributos del modelo del mundo
	 */
	private TablaHashSeparateChaining<String,ILista <VideoYoutube>> videosTablaSC;
	
	private TablaHashLinearProbing<String, ILista<VideoYoutube>> videosTablaLP;
	

	private ArregloDinamico<Categoria> categoriaArreglo;
	
	/**
	 * Constructor del modelo del mundo con capacidad dada
	 * @param tamano
	 */
	public Modelo(int tamanoInicial, int factorCarga)
	{
		videosTablaSC = new TablaHashSeparateChaining<String, ILista<VideoYoutube>>(tamanoInicial, factorCarga);
		videosTablaLP = new TablaHashLinearProbing<String,ILista<VideoYoutube>>(tamanoInicial);
		categoriaArreglo= new ArregloDinamico<>(50);
		try 
		{
			cargarDatosArregloCat();
			
		}
		catch (Exception e)
		{	
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Servicio de consulta de numero de elementos presentes en el modelo 
	 * @return numero de elementos presentes en el modelo
	 */
	public int darTamanoSC()
	{
		return videosTablaSC.size();
	}

	public int darTamanoLP()
	{
		return videosTablaLP.size();
	}
	
	/**
	 * Metodo que retorna el arreglo con las categorias 
	 * @return arreglo de categorias
	 */
	public ILista<Categoria> darCategorias()
	{
		return categoriaArreglo;
	}
	
	
	
	/**
	 * Metodo que retorna id, unico, de la categoria cuyo nombre entra como parametro
	 * @param nombre. Cadena de caracteres que representa el nombre de la categoria de la cual se quiere el id
	 * @return id correspondiente a la catergoria
	 */
	public int darIdSegunNombreCategoria(String nombre)
	{
		int id = 0;
		boolean encontro = false;
		for(int i = 1; i <= categoriaArreglo.size() && !encontro;i++)
		{
			if(categoriaArreglo.getElement(i).darNombre().compareToIgnoreCase(nombre)==0)
			{
				encontro = true;
				id = categoriaArreglo.getElement(i).darId();
			}
		}
		return id;
	}
	public String darNombreSegunIdCategoria(int id)
	{
		String nombre = null;
		boolean encontro = false;
		for(int i = 1; i <= categoriaArreglo.size() && !encontro;i++)
		{
			if(categoriaArreglo.getElement(i).darId() == id)
			{
				encontro = true;
				nombre = categoriaArreglo.getElement(i).darNombre();
			}
		}
		return nombre;
	}
	//------------------
	//Carga de  Datos
	//------------------
	
	/**
	 * Metodo que carga los datos de los videos como una Tabla de hash con manejo de colisiones con Separate chaining que  desde un archivo csv 
	 */
	public void cargarDatosTablaSC() throws NumberFormatException, ParseException
	{
		VideoYoutube video = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy.dd.MM");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		long tiempototal= 0;
		try
		{
			Reader in = new FileReader("./data/videos-small.csv");
			Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
			int i = 0;
			for(CSVRecord record : records)
			{
				String id = record.get(0);
				Date trending = format.parse(record.get(1));
				String title = record.get(2);
				String channel = record.get(3);
				int categoryId = Integer.parseInt(record.get(4));
				Date publishDate = format2.parse(record.get(5));
				String tags = record.get(6);
				int views = Integer.parseInt(record.get(7));
				int likes = Integer.parseInt(record.get(8));
				int dislikes = Integer.parseInt(record.get(9));
				String country = record.get(16);
						
				video = new VideoYoutube(id, trending, title, channel, categoryId, publishDate, tags, views, likes, dislikes, country);
				String categoria = darNombreSegunIdCategoria(categoryId);
				String llave = (country+categoria).toLowerCase();
				
				
				long start = 0;
				long stop = 0;
				
				ILista<VideoYoutube> valoresActuales = videosTablaSC.get(llave);
				if(valoresActuales != null)
				{
					 boolean esta = false;
					 for(int j= 1; j<= valoresActuales.size()&& !esta;j++)
					 {
						 if(valoresActuales.getElement(j).getTitle().compareToIgnoreCase(title) == 0)
						 {
							 esta = true;
						 }
					 }
					 if(!esta)
					 {
						 valoresActuales.addLast(video);
						 videosTablaSC.changeValue(llave, valoresActuales); 
						 i++;
					 }
					
				}
				else
				{
					valoresActuales = new ArregloDinamico<>(10);
					valoresActuales.addLast(video);
					start = System.currentTimeMillis();
					videosTablaSC.put(llave, valoresActuales);
					stop = System.currentTimeMillis();
					i++;
				}
				tiempototal += (stop-start);
				
			}
			
			System.out.println("Datos cargados \n "+ "Totalidad de videos: "+i);
			System.out.println("Totalidad de duplas en la tabla de hash con separate Chaining: "+ videosTablaSC.size());
			System.out.println("Promedio del metodo put en milisegundos en la tabla de hash con separate Chaining: "+(tiempototal/(i-1)));
		}
		catch (IOException e) 
		{
			e.printStackTrace();
			System.out.println("No se encontro el archivo para cargar los datos");
		}
	}
	
	/**
	 * Metodo que carga los datos de los videos como una Tabla de hash con manejo de colisiones con Linear Probing que  desde un archivo csv 
	 */
	public void cargarDatosTablaLP() throws NumberFormatException, ParseException
	{
		VideoYoutube video = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy.dd.MM");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		long tiempototal= 0;
		try
		{
			Reader in = new FileReader("./data/videos-small.csv");
			Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
			int i = 0;
			for(CSVRecord record : records)
			{
				String id = record.get(0);
				Date trending = format.parse(record.get(1));
				String title = record.get(2);
				String channel = record.get(3);
				int categoryId = Integer.parseInt(record.get(4));
				Date publishDate = format2.parse(record.get(5));
				String tags = record.get(6);
				int views = Integer.parseInt(record.get(7));
				int likes = Integer.parseInt(record.get(8));
				int dislikes = Integer.parseInt(record.get(9));
				String country = record.get(16);
						
				video = new VideoYoutube(id, trending, title, channel, categoryId, publishDate, tags, views, likes, dislikes, country);
				String categoria = darNombreSegunIdCategoria(categoryId);
				String llave = (country+categoria).toLowerCase();
				
				
				long start = 0;
				long stop = 0;
				
				ILista<VideoYoutube> valoresActuales = videosTablaLP.get(llave);
				if(valoresActuales != null)
				{
					 boolean esta = false;
					 for(int j= 1; j<= valoresActuales.size()&& !esta;j++)
					 {
						 if(valoresActuales.getElement(j).getTitle().compareToIgnoreCase(title) == 0)
						 {
							 esta = true;
						 }
					 }
					 if(!esta)
					 {
						 valoresActuales.addLast(video);
						 videosTablaLP.changeValue(llave, valoresActuales); 
						 i++;
					 }
					
				}
				else
				{
					valoresActuales = new ArregloDinamico<>(10);
					valoresActuales.addLast(video);
					start = System.currentTimeMillis();
					videosTablaLP.put(llave, valoresActuales);
					stop = System.currentTimeMillis();
					i++;
				}
				tiempototal += (stop-start);
				
			}
			
			System.out.println("Datos cargados \n "+ "Totalidad de videos: "+i);
			System.out.println("Totalidad de duplas en la tabla de hash con linear Probing: "+ videosTablaLP.size());
			System.out.println("Promedio del metodo put en milisegundos en la tabla de hash con linear Probing: "+(tiempototal/(i-1)));
		}
		catch (IOException e) 
		{
			e.printStackTrace();
			System.out.println("No se encontro el archivo para cargar los datos");
		}
	}
	
	
	/**
	 * Metodo que carga los datos de las categorias en una arreglo desde un archivo csv 
	 */
	public void cargarDatosArregloCat() throws NumberFormatException, ParseException
	{
	
		try
		{
			Reader in = new FileReader("./data/category-id.csv");
			Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
			for(CSVRecord record : records)
			{
				int Id = Integer.parseInt(record.get(0));
				String nombre = record.get(1);
				Categoria aAgregar = new Categoria(nombre,Id);
				categoriaArreglo.addLast(aAgregar);
			}
		}
		catch (IOException e) 
		{
			e.printStackTrace();
			System.out.println("No se encontro el archivo para cargar los datos");
		}
	}

	//-----------------------------------------------------
	//Requerimientos 
	//-----------------------------------------------------
	
	public ILista<VideoYoutube> requerimientoLP(String country, String category)
	{
		return videosTablaLP.get(country+category);
	}
	
	public ILista<VideoYoutube> requerimientoSC(String country, String category)
	{
		return videosTablaSC.get(country+category);
	}
	
	public long pruebaMetodoGetSC()
	{
		long total = 0;
		String[] paises =new String[5];
		paises[0] ="canada";
		paises[1]="france";
		paises[2]="india";
		paises[3]="germany";
		paises[4]="japan";
		for(int i=0;i<1000;i++)
		{
			long start = 0;
			long stop = 0;
			if (i <700)
			{
				int n1 = (int) (Math.random()*5);
				int n2 = (int)(Math.random()*(31)+1);
				String llave = (paises[n1] + categoriaArreglo.getElement(n2).darNombre()).toLowerCase();
				start = System.currentTimeMillis();
				videosTablaSC.get(llave);
				stop = System.currentTimeMillis();
				
			}
			else
			{
				int n2 = (int)(Math.random()*(31)+1);
				String llave = ("lakaaiaana" + categoriaArreglo.getElement(n2).darNombre()).toLowerCase();
				
				start = System.currentTimeMillis();
				videosTablaSC.get(llave);
				stop = System.currentTimeMillis();
			}
			total += (stop-start);
		}
		return(total);
	}
	
	public long pruebaMetodoGetLP()
	{
		long total = 0;
		String[] paises =new String[5];
		paises[0] ="canada";
		paises[1]="france";
		paises[2]="india";
		paises[3]="germany";
		paises[4]="japan";
		for(int i=0;i<1000;i++)
		{
			long start = 0;
			long stop = 0;
			if (i <700)
			{
				int n1 = (int) (Math.random()*5);
				int n2 = (int)(Math.random()*(31)+1);
				String llave = (paises[n1]+categoriaArreglo.getElement(n2).darNombre()).toLowerCase();
				start = System.currentTimeMillis();
				videosTablaLP.get(llave);
				stop = System.currentTimeMillis();
				
			}
			else
			{
				int n2 = (int)(Math.random()*(31)+1);
				String llave = ("lakaaiaana"+categoriaArreglo.getElement(n2).darNombre()).toLowerCase();
				
				start = System.currentTimeMillis();
				videosTablaLP.get(llave);
				stop = System.currentTimeMillis();
			}
			total += (stop-start);
		}
		return(total);
	}
}
