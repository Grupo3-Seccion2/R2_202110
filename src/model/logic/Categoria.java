package model.logic;

public class Categoria implements Comparable<Categoria>
{
	private String nombre;
	 
	private int id;
	
	public Categoria(String Nombre, int Id)
	{
		nombre = Nombre;
		id = Id;
	}

	public String darNombre()
	{
		return nombre;
	}
	
	public int darId()
	{
		return id;
	}
	
	@Override
	public String toString()
	{
		return "- "+ nombre;
	}
	@Override
	public int compareTo(Categoria o) 
	{
		int comp;
		int compar= this.nombre.compareToIgnoreCase(o.nombre);
		if(compar == 0)
			comp = 0;
		else if (compar<0)
			comp = -1;
		else 
			comp = 1;
		return comp;
	}
	

}
