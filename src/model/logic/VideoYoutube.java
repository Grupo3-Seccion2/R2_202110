package model.logic;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class VideoYoutube implements Comparable<VideoYoutube> 
{
	/**
	 * id alfanumerico del video. Unico para cada video
	 */
	private String id;
	
	/**
	 * Fecha en la que el video fue tendencia
	 */
	private Date trendingDate;
	
	/**
	 * Titulo del video
	 */
	private String title;
	
	/**
	 * Titulo del canal donde el video fue publicado 
	 */
	private String channel;
	
	/**
	 * id de la categoria del video
	 */
	private int categoryId;
	
	/**
	 * Fecha en la que el video fue publicado
	 */
	private Date publishDate;
	
	/**
	 * Tags del video
	 */
	private String tags;
	
	/**
	 * Numero de vistas
	 */
	private int views;
	
	/**
	 * Numero de likes
	 */
	private int likes;
	
	/**
	 * Numero de dislikes
	 */
	private int dislikes;
	
	/**
	 * Pais correpsondiente a la publicacion del video
	 */
	private String country;
	
	/**
	 * Numero de dias que el video fue tendencia
	 */
	private int trendingDays;
	
	/**
	 * Metodo constructor
	 */
	public VideoYoutube(String pId, Date pTrending, String pTitle, String pChannel, int pCategoryId, Date pPublish, String ptags, int pViews, int pLikes, int pDislikes, String pCountry)
	{
		id = pId;
		trendingDate = pTrending;
		title = pTitle;
		channel = pChannel;
		categoryId = pCategoryId;
		publishDate = pPublish;
		tags = ptags;
		views = pViews;
		likes = pLikes;
		dislikes = pDislikes;
		country = pCountry;
		trendingDays=0;
	}
	
	/**
	 * Metodo que retorna el id del video
	 * @return id del video
	 */
	public String getId() 
	{
		return id;
	}
	
	/**
	 * Metodo que retorna la fecha de tendencia
	 * @return fecha del dia de tendencia
	 */
	public Date getTrendingDate()
	{
		return trendingDate;
	}
	
	/**
	 * Metodo que retorna el titulo del video
	 * @return titulo del video
	 */
	public String getTitle()
	{
		return title;
	}
	
	/**
	 * Metodo que retorna el canal correspondiente del video
	 * @return canal del video
	 */
	public String getChannel()
	{
		return channel;
	}
	
	/**
	 * Metodo que retorna el id de la categoria del video
	 * @return id de la categoria del video
	 */
	public int getCategoryID()
	{
		return categoryId;
	}
	
	/**
	 * Metodo que retorna la fecha de publicacion
	 * @return fecha del dia de publicacion
	 */
	public Date getPublishDate()
	{
		return publishDate;
	}
	
	/**
	 * Metodo que retorna los tgas correspondientes del video
	 * @return tags del video
	 */
	public String getTags()
	{
		return tags;
	}
	
	/**
	 * Metodo que retorna el numero de vistas
	 * @return vistas del video
	 */
	public int getViews()
	{
		return views;
	}
	
	/**
	 * Metodo que retorna el numero de likes
	 * @return likes del video
	 */
	public int getLikes()
	{
		return likes;
	}
	
	/**
	 * Metodo que retorna el numero de dislikes
	 * @return dislikes del video
	 */
	public int getDislikes()
	{
		return dislikes;
	}
	
	/**
	 * Metodo que retorna el pais asociado al video
	 * @return pias del video
	 */
	public String getCountry()
	{
		return country;
	}

	/**
	 * Metodo que retorna el numero de dias de tendencias 
	 * @return dias de tendencia del video
	 */
	public int getTrendingDays()
	{
		return trendingDays;
	}
	/**
	 * Metodo que actualiza el numero de dias de tendencia del video 
	 * @param int trendingD
	 */
	public void setTrendingDays(int trendingD)
	{
		trendingDays =trendingD;
	}
	public String requerimiento1()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yy.dd.MM");
		String  trendingdate= sdf.format(trendingDate);
		String mensaje = trendingdate+"    "+title+"    "+channel+"    "+publishDate+"    "+views+"    "+likes+"    "+dislikes;
		return mensaje;
	}
	public String requerimiento4()
	{
		String mensaje = title+"    "+channel+"    "+publishDate+"    "+views+"    "+likes+"    "+dislikes+"    "+tags;
		return mensaje;
	}
	
	public String requerimiento2()
	{
		String mensaje = title+"    "+channel+"    "+country+"    "+trendingDays;
		return mensaje;
	}
	public String requerimiento3()
	{
		String mensaje = title+"    "+channel+"    "+categoryId+"    "+trendingDays;
		return mensaje;
	}

	@Override
	public int compareTo(VideoYoutube otro) 
	{
		int dif = this.id.compareToIgnoreCase(otro.id);
		return dif;
	}
	
	public static class ComparadorXLikes implements Comparator<VideoYoutube>
	
	{
		/** Comparador alterno de acuerdo al n�mero de likes
		* @return valor 0 si video1 y video2 tiene los mismos likes.
		 valor negativo si video1 tiene menos likes que video2.
		 valor positivo si video1 tiene m�s likes que video2. */
		@Override
		public int compare(VideoYoutube video1, VideoYoutube video2) 
		{
			if(Integer.compare(video1.getLikes(), video2.getLikes()) == 0)
				return 0;
			else if(Integer.compare(video1.getLikes(), video2.getLikes()) < 0)
				return -1;
			else
				return 1;
			
		}
		
	}

	public static class ComparadorXPais implements Comparator<VideoYoutube>
	{
		@Override
		public int compare(VideoYoutube v1, VideoYoutube v2) {
		    if (v1.getCountry() == v2.getCountry()) 
		    {
		        return 0;
		    }
		    if (v1.getCountry() == null) {
		        return -1;
		    }
		    if (v2.getCountry() == null) {
		        return 1;
		    }
		    return v1.getCountry().compareTo(v2.getCountry());
		  }

		
	}


	
	

	public static class ComparadorXCategoriaId implements Comparator<VideoYoutube>
	{

		@Override
		public int compare(VideoYoutube o1, VideoYoutube o2) 
		{
			int dif = o1.categoryId-o2.categoryId;
			if (dif == 0)
				return 0;
			else if(dif <0)
				return -1;
			else
				return 1;
		}
		
	}
	
	
	public static class ComparadorXViews implements Comparator<VideoYoutube>
	{


		@Override
		public int compare(VideoYoutube o1, VideoYoutube o2) 
		{
			int dif = o1.views-o2.views;
			if (dif == 0)
				return 0;
			else if(dif <0)
				return -1;
			else
				return 1;
		}
		
	}
}
