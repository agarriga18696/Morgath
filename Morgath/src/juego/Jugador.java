package juego;

public class Jugador {

	private int puntos;
	private String localizacion;
	
	public Jugador(String localizacion) {
		
		this.puntos = 0;
		this.localizacion = localizacion;	
		
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public String getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
	}
	
}
