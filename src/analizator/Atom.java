package analizator;

public class Atom {
	
	private String numeAtom;
	private int codAtom;
	
	public Atom(String numeAtom, int codAtom) {
		this.setNumeAtom(numeAtom);
		this.setCodAtom(codAtom);
	}

	public void setCodAtom(int codAtom2) {
		this.codAtom = codAtom2;
	}

	public void setNumeAtom(String numeAtom2) {
		this.numeAtom = numeAtom2;
	}

	public String getNumeAtom() {
		return numeAtom;
	}

	public int getCodAtom() {
		return codAtom;
	}
	
	public String toString() {
		return "Atom: nume=" + this.getNumeAtom() + " cod = " + this.getCodAtom();
	}
}
