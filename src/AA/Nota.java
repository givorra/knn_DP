package AA;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Nota 
{
	private String nombre;
	private ArrayList<Coordenada> puntos;
	
	
	Nota()
	{
		puntos = new ArrayList<Coordenada>();
		nombre = new String();
	}
	
	Nota(ArrayList<Coordenada> puntos)
	{
		this.puntos = puntos;
	}
	
	public ArrayList<Coordenada> getPuntos()
	{
		return puntos;
	}
	
	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}
	
	public void setPuntos(ArrayList<Coordenada> puntos)
	{
		this.puntos = puntos;
	}
	
	public String getNombre()
	{
		return nombre;
	}
	
	public Coordenada getPunto(int i)
	{
		return puntos.get(i);
	}
	
	// Guarda el nombre de la nota y manda a procesar los puntos
	public void leerLinea(String linea)
	{
		StringTokenizer st = new StringTokenizer(linea, " ");
		nombre = st.nextToken();
		leerPuntos(st.nextToken());
		
	}
	
	// Procesa las coordenadas y las almacena en el array
	private void leerPuntos(String linea)
	{
		StringTokenizer st = new StringTokenizer(linea, ";");
		StringTokenizer stCoords;
		while (st.hasMoreTokens())
		{
			String coord = st.nextToken();
			Coordenada punto = new Coordenada();
			stCoords = new StringTokenizer(coord, ",");
			punto.setX(Integer.parseInt(stCoords.nextToken()));
			punto.setY(Integer.parseInt(stCoords.nextToken()));
			puntos.add(punto);
		}
	}
	
	public String toString()
	{
		String s = new String();
		s = nombre + "\n";
		for(int i = 0; i < puntos.size(); i++)
			s += puntos.get(i).toString() + ";";
		return s;
	}
}
