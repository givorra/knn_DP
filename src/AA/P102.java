package AA;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.Arrays;


/*
 * 10 cross validation / training / test ~> tasa de aciertos
 * condensing
 */
public class P102 
{
	private ArrayList<Nota> notas;
	private ArrayList<ArrayList<Nota>> conjuntos; // 0-8 trainning, 9 test
	
	static FileWriter fichero;
    static PrintWriter pw;
    static FileReader fr;
    static BufferedReader br;
    public String costes;
    public StringTokenizer stCostes;
    public int cuentatokens;
	
	public class Vecino implements Comparable<Vecino>
	{
		private Nota nota;
		private float cost;
		
		public Vecino()
		{
			nota = new Nota();
		}
		
		public void setCost(float cost){this.cost = cost;}
		public void setNota(Nota nota)
		{
			this.nota.setPuntos(nota.getPuntos());
			this.nota.setNombre(nota.getNombre());
		}

		public float getCost(){return cost;}
		public Nota getNota(){return nota;}
		public int compareTo(Vecino v)
		{

			int estado=-1;
			if(this.cost == v.getCost())
				estado=0;
			else if(this.cost < v.getCost())
				estado=1;
			else if(this.cost > v.getCost())
				estado=-1;
			return estado;

		}
	}
	
	public P102()
	{
		notas = new ArrayList<Nota>();
		conjuntos = new ArrayList<ArrayList<Nota>>(10);
		for(int i = 0; i < 10; i++)
			conjuntos.add(new ArrayList<Nota>());
		
		
	}
	
	public static void main(String args[]) throws IOException
	{
		long aciertos = 0, fallos = 0;
		Nota nAux = null;
		P102 p102 = new P102();
		p102.leerFichero();
		p102.divideFichero();
			
		for(int i=0; i<10; i++)
		{
			ArrayList<Nota> trainning = p102.unificaConjuntos(i);	// Obtiene conjunto de entrenamiento 
			
			//fichero = new FileWriter("costes"+i+".txt");        
			//pw = new PrintWriter(fichero);
			//leeCostes();
			// Para leer los costes de la cache
			fr = new FileReader("costes"+i+".txt");
			br = new BufferedReader(fr);
			p102.costes = br.readLine();
			fr.close();
			//Para añadir los costes que faltan;
			//fichero = new FileWriter("costes"+i+".txt", true);
			//pw = new PrintWriter(fichero);
			
			p102.stCostes = new StringTokenizer(p102.costes, ";");
			//System.out.println("Tokens: "+p102.stCostes.countTokens());
			
			aciertos = fallos = 0;
			
			// Para cada nota del test, obtener su resultado
			for(Nota nota: p102.getConjunto(i))
			{
				nAux = p102.kNN(trainning, nota.getPuntos(), 13);	
				//System.out.println("Nota leida: " + nota.getNombre());
				//System.out.println("Nota obtenida: "+nAux.getNombre());		
				if(nota.getNombre().equals(nAux.getNombre()))
				{
					++aciertos;
				}
				else
				{
					++fallos;
				}
			}
			System.out.print(aciertos+";"+fallos+";\n");
			//System.out.println("<<<<<<<< Test "+i+" >>>>>>>>");
			//System.out.println("Aciertos: "+ Long.toString(aciertos));
			//System.out.println("Fallos: "+Long.toString(fallos));
			//System.out.println("Tasa de aciertos: "+Double.toString((aciertos*100)/trainning.size()) + "%");
			//pw.close();
			//fichero.close();
		}
	}
	
	public Nota kNN(ArrayList<Nota> entrenamiento, ArrayList<Coordenada> test, int k) throws NumberFormatException, IOException
	{
		//float W = 0;
		float costAux;
		Vecino[] kVecinos = new Vecino[k+1];
		Vecino auxVecino = new Vecino();
		
		// Inicializa array de vecinos
		for(int i = 0; i <= k; i++)
		{
			auxVecino = new Vecino();
			auxVecino.setCost(Float.NEGATIVE_INFINITY);
			kVecinos[i] = auxVecino;
		}
		// Obtiene los K vecinos mas cercanos
		for(Nota nota: entrenamiento)
		{			
			//costAux = (float) (1/(this.DTWDistance(nota.getPuntos(), test)+0.0000001));
			costAux = 0;
			cuentatokens++;
			if(stCostes.hasMoreTokens())
				costAux = Float.parseFloat(stCostes.nextToken());
			else
			{
				System.out.println("Error, no hay mas tokens");
				//costAux = (float) (1/(this.DTWDistance(nota.getPuntos(), test)+0.0000001));
				//pw.print(costAux+";");
			}
			
			//pw.print(costAux+";");
			
			// Miramos el array de vecinos a ver si mejora el coste
			for(int i = k-1; i >= 0; i--)
			{
				if(costAux > kVecinos[i].getCost())
				{
					kVecinos[k].setNota(nota);
					kVecinos[k].setCost(costAux);
					Arrays.sort(kVecinos);
					break;
				}
			}
		}
		
		//for(int i = 0; i<k; i++)
			//kVecinos[i].setCost(1);
		
		// Ponderar y dar resultado
		for(int i = 0; i < k; i++)
		{
			//W += kVecinos[i].getCost();
			for(int j= i+1; j < k; j++)
			{
				if(kVecinos[j].getNota().getNombre().equals(kVecinos[i].getNota().getNombre()))
				{
					kVecinos[i].setCost((kVecinos[i].getCost() + kVecinos[j].getCost()));
					kVecinos[j].getNota().setNombre(Integer.toString(j));
				}
			}
		}
		Arrays.sort(kVecinos);
		
		return kVecinos[0].getNota();
	}
	
	private void leerFichero() throws IOException
	{
		String linea;
		FileReader file = new FileReader("prueba.pts");
		BufferedReader bf = new BufferedReader(file);
		while((linea = bf.readLine()) != null)
		{
			// Estoy leyendo una linea para nombre y otra para puntos, es una sola linea con nombre y puntos.
			Nota nota = new Nota();
			nota.leerLinea(linea);
			notas.add(nota);
		}
		bf.close();
	}
	
	public ArrayList<Nota> getNotas()
	{
		return notas;
	}
	
	public ArrayList<Nota> getConjunto(int i)
	{
		return conjuntos.get(i);
	}
	
	float DTWDistance(ArrayList<Coordenada> s, ArrayList<Coordenada> t)
	{
		float DTW[][] = new float[s.size()+1][t.size()+1]; 
		
		for(int i = 0; i <= s.size(); i++)
			DTW[i][0] = Float.POSITIVE_INFINITY;
		for(int i = 0; i <= t.size(); i++)
			DTW[0][i] = Float.POSITIVE_INFINITY;
		DTW[0][0] = 0;
		
		for(int i = 1; i <= s.size(); i++)
		{
			for(int j = 1; j <= t.size(); j++)
			{
				float cost = s.get(i-1).distanciaEuclidea(t.get(j-1));
				DTW[i][j] = cost + Math.min(DTW[i-1][j], Math.min(DTW[i][j-1], DTW[i-1][j-1]));
			}
		}
		//pw.println(DTW[s.size()][t.size()]);
		return DTW[s.size()][t.size()];
	}

	public class CustomComparator implements Comparator<Nota>
	{
		@Override
		public int compare(Nota n1, Nota n2)
		{
			return n1.getNombre().compareTo(n2.getNombre());
		}
	}
	
	
	public void divideFichero()
	{		
		Collections.sort(notas, new CustomComparator());
		
		for(int i = 0; i < notas.size()-9; i += 10)
		{
			conjuntos.get(0).add(notas.get(i));
			conjuntos.get(1).add(notas.get(i+1));
			conjuntos.get(2).add(notas.get(i+2));
			conjuntos.get(3).add(notas.get(i+3));
			conjuntos.get(4).add(notas.get(i+4));
			conjuntos.get(5).add(notas.get(i+5));
			conjuntos.get(6).add(notas.get(i+6));
			conjuntos.get(7).add(notas.get(i+7));
			conjuntos.get(8).add(notas.get(i+8));
			conjuntos.get(9).add(notas.get(i+9));
		}
	}
	
	public ArrayList<Nota> unificaConjuntos(int excluido)
	{
		ArrayList<Nota> trainning = new ArrayList<Nota>();
		for(int i = 0; i < 10; i++)
		{
			if(i != excluido)
				for(int j =0; j< conjuntos.get(i).size(); j++)
				{
					trainning.add(conjuntos.get(i).get(j));
				}
		}
		return trainning;
	}
}
