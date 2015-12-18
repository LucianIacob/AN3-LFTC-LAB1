package analizator;

public class TS {
	private int pozitieTS;
	private String simbol;
	
	public TS(int pozitieTS, String simbol) {
		this.setPozitieTS(pozitieTS);
		this.setSimbol(simbol);
	}

	public String getSimbol() {
		return simbol;
	}

	public void setSimbol(String simbol) {
		this.simbol = simbol;
	}

	public int getPozitieTS() {
		return pozitieTS;
	}

	public void setPozitieTS(int pozitieTS) {
		this.pozitieTS = pozitieTS;
	}
	
	
}
