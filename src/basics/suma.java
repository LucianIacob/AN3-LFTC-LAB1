package basics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class suma {

	public static void main(String[] args) throws NumberFormatException, IOException {
		Suma();
	}
	
	public static void Suma() throws NumberFormatException, IOException{
		System.out.print("Dati n: ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine()), i;
		i = n;
		
		ArrayList<Double> myList = new ArrayList<Double>();
		
		double suma = 0;
		while (n>0)
		{
			System.out.print("Dati nr real: ");
			double nr = Double.parseDouble(br.readLine());
			myList.add(nr);
			n = n-1;
		}
		
		while (i>0)
		{
			suma = suma + myList.get(i-1);
			i=i-1;
		}
		
		System.out.println("Suma este: " + suma);
	}
}
