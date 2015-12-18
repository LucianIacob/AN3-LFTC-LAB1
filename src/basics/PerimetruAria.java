package basics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class PerimetruAria {

	public static void main(String[] args) throws IOException {
		PerAria();
	}

	public static void PerAria() throws NumberFormatException, IOException{
		System.out.print("Dati raza: ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int raza = Integer.parseInt(br.readLine());
		
		if (raza < 1)
			return;
		
		System.out.println("Perimetrul este: " + 2 * Math.PI * raza);
		System.out.println("Aria este: " + Math.PI * raza * raza);
	}
}
