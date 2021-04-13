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
		
		categoriaArreglo= new ArregloDinamico<>(50);
		try 
		{
			cargarDatosArregloCat();
			cargarDatosTablaSC();
			
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
		try
		{
			Reader in = new FileReader("./data/videos-all.csv");
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
				String llave = (categoria).toLowerCase();
				
			
				
				ILista<VideoYoutube> valoresActuales = videosTablaSC.get(llave);
				if(valoresActuales != null)
				{
					//boolean encontroPrimero = false;
					//for(int j = 1;j<= valoresActuales.size() && !encontroPrimero;j++)
					//{
					//	VideoYoutube actual = valoresActuales.getElement(j);
					//	if(actual.compareTo(video)==0)
					//	{
					//		encontroPrimero = true;
					//		actual.setTrendingDays(actual.getTrendingDays()+1);
					//	}
					//}
					valoresActuales.addLast(video);
					videosTablaSC.changeValue(llave, valoresActuales);
					i++;
					
				}
				else
				{
					valoresActuales = new ArregloDinamico<>(10);
					valoresActuales.addLast(video);
					videosTablaSC.put(llave, valoresActuales);
					i++;
				}
			
			}
			
			System.out.println("Datos cargados \n "+ "Totalidad de videos: "+i);
			System.out.println("Totalidad de duplas en la tabla de hash con separate Chaining: "+ videosTablaSC.size());
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
	
	/**
	 *  Metodo que busca los n videos con mas views de una categoria especifica con un pais dado
	 * @pre la lista de videos no debe estar vacia
	 * @param String country. nombre del pais que se quiere los videos con mas likes
	 * @param String categoryName. nombre de la categoria especifica
	 * @param int n. numero de videos que se quieren listar
	 * @return Lista de videos que cumplen con las condiciones del requerimiento
	 */
	public ILista<VideoYoutube> requerimiento1(String country, String categoryName, int n )
	{
		ILista<VideoYoutube> videosPorcategoria = videosTablaSC.get(categoryName);
		ILista<VideoYoutube> subLista = new ArregloDinamico<VideoYoutube>(n);
		Ordenamiento<VideoYoutube> algsOrdenamientoVideos = new Ordenamiento<VideoYoutube>(); 
		
		Comparator<VideoYoutube> comparador = new VideoYoutube.ComparadorXViews(); 
	
		algsOrdenamientoVideos.ordenarShell(videosPorcategoria, comparador,false);
		boolean termino = false;
		int c = 0;
		for(int i = 1; i <= videosPorcategoria.size()&& !termino;i++)
		{
			if (c >=n)
			{
				termino = true;	
			}
			else
			{
				if(videosPorcategoria.getElement(i).getCountry().compareToIgnoreCase(country)==0)
				{
						c++;
						subLista.addLast(videosPorcategoria.getElement(i));
					
				}
			}
		}
		
		return subLista;
		
		
	}
	
	/**
	 * Busca el video con mas dias como tendencia dado un pais que entra como parametro
	 * @pre la lista de videos no debe estar vacia
	 * @param String country
	 * @return video con mas dias como tendencia
	 */
	public VideoYoutube requerimiento2(String country)
	{
		VideoYoutube tend = null;
		ILista<VideoYoutube> lista = new ArregloDinamico<>(10);
		for(int i = 1; i < lista.size(); i++)
		{
			ILista<VideoYoutube> videosCategoria = videosTablaSC.get(categoriaArreglo.getElement(i).darNombre().toLowerCase());
			for(int j = 1; j <= videosCategoria.size();j++)
			{
				VideoYoutube actual = videosCategoria.getElement(j);
				if(actual.getCountry().equalsIgnoreCase(country))
				{
					lista.addLast(actual);
				}

			}
		}
		if(!lista.isEmpty())
		{
			int maxNumDias = 0;
			for(int i = 1; i <=lista.size();i++)
			{
				if(lista.getElement(i)!= null)
				{
					int actNumDias = lista.size();
					if(actNumDias > maxNumDias)
					{
						maxNumDias = actNumDias;
						tend = lista.firstElement();
					}
				}
				tend.setTrendingDays(maxNumDias);
			}
		}
		return tend;
	}

	/**
	 * Busca el video con mas dias como tendencia dado una categoria que entra como parametro
	 * @pre la lista de videos no debe estar vacia
	 * @param String categoryName. nombre de la categoria de la cual se quiere buscar
	 * @return video con mas dias como tendencia
	 */
	public VideoYoutube requerimiento3(String categoryName)
	{
		VideoYoutube tendencia = null;
		ILista<VideoYoutube> videosPorCategoria = videosTablaSC.get(categoryName.toLowerCase());
		if(videosPorCategoria!=null)
		{
			TablaHashLinearProbing<String, ILista<VideoYoutube>> videos = new TablaHashLinearProbing<String,ILista<VideoYoutube>>(videosPorCategoria.size()/2,0.5);
			for(int i= 1; i<= videosPorCategoria.size();i++)
			{
				VideoYoutube actual = videosPorCategoria.getElement(i);
				String llave = actual.getTitle();
				ILista<VideoYoutube> valoresActuales = videos.get(llave);
				if(valoresActuales != null)
				{
					valoresActuales.addLast(actual);
					videos.changeValue(llave, valoresActuales);
	
				}
				else
				{
					valoresActuales = new ArregloDinamico<>(10);
					valoresActuales.addLast(actual);
					videos.put(llave, valoresActuales);
				}
			}
			int maxNumDias = 0;
			for(int i = 1; i <=videos.darListaNodos().size();i++)
			{
				if(videos.darListaNodos().getElement(i)!= null)
				{
					ILista<VideoYoutube> valoresActuales = videos.darListaNodos().getElement(i).getValue();
					int actNumDias = valoresActuales.size();
					if(actNumDias>maxNumDias)
					{
						maxNumDias = actNumDias;
						tendencia = valoresActuales.firstElement();
					}
				}
				
			}
			tendencia.setTrendingDays(maxNumDias);
		}
		return tendencia;

	}
	
	/**
	 *  Metodo que busca los n videos con mas likes de un pais especifico con un tag dado
	 * @pre la lista de videos no debe estar vacia
	 * @param String country. nombre del pais que se quiere los videos con mas likes
	 * @param String tag. tag especifico que se quiere. tag debe ser una subcadena del atributo tags de un video
	 * @param int n. numero de videos que se quieren listar
	 * @return Lista de videos que cumplen con las condiciones del requerimiento
	 */
	public ILista<VideoYoutube> requerimiento4(String tag, int n )
	{
		ILista<VideoYoutube> videosPorTag = new ArregloDinamico<>(10);
		for(int i = 1; i <= categoriaArreglo.size();i++)
		{
			ILista<VideoYoutube> videosPorCategoria = videosTablaSC.get(categoriaArreglo.getElement(i).darNombre().toLowerCase());
			if(videosPorCategoria != null)
			{
				for(int j = 1; j <= videosPorCategoria.size();j++)
				{
					VideoYoutube actual = videosPorCategoria.getElement(j);
					if(actual.getTags().contains(tag))
					{
						String[] tags = actual.getTags().split("\\|");
						for(int k = 0; k <tags.length-1;k++)
						{
							if(tags[k].compareToIgnoreCase(tag)==0)
							{
								videosPorTag.addLast(actual);
							}
						}
					}
				}
			}
		}
		
		if(!videosPorTag.isEmpty())
		{
			Ordenamiento<VideoYoutube> algsOrdenamientoVideos = new Ordenamiento<VideoYoutube>(); 
			Comparator<VideoYoutube> comparador = new VideoYoutube.ComparadorXViews();
			algsOrdenamientoVideos.ordenarShell(videosPorTag, comparador, false);
			
		}
		return videosPorTag.sublista(n);
	}

}
