package basics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Cmmdc {

	public static void main(String[] args) throws NumberFormatException, IOException {
		cmmdc();
	}

	public static void cmmdc() throws NumberFormatException, IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.print("Dati primul numar: ");
		int nr1 = Integer.parseInt(br.readLine());
		
		System.out.print("Dati al doilea numar: ");
		int nr2 = Integer.parseInt(br.readLine());
		
		int rest;
		
		do 
		{
			rest = Math.abs(nr1) % Math.abs(nr2);
			nr1 = Math.abs(nr2);
			nr2 = rest;	
		}
		while (rest > 0);
	    
		System.out.println("cmmdc = " + nr1);
	}
}
