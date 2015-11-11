package AA;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class RellenaCostes {
	
	static FileWriter fichero;
    static PrintWriter pw;
    static FileReader fr;
    static BufferedReader br;
    static String costes;
    static StringTokenizer stCostes;
    
	public static void main(String[] args) throws IOException
	{
		for(int i = 0; i < 10; i++)
		{
			fr = new FileReader("costes"+i+".txt");
			br = new BufferedReader(fr);
			costes = br.readLine();
			fr.close();
			stCostes = new StringTokenizer(costes, ";");
			
			//Para añadir los costes que faltan;
			fichero = new FileWriter("ncostes"+i+".txt");
			pw = new PrintWriter(fichero);
			
			for(int j = 0; j < 9000000; j++)
				pw.print(stCostes.nextToken()+";");
			pw.close();
			fichero.close();
		}
	}
}
