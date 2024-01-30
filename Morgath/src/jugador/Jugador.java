package jugador;

public class Jugador {

	private String nombre;
	private String clase;
	private String raza;

	public Jugador(String nombre, String clase, String raza) {
		this.nombre = nombre;
		this.clase = clase;
		this.raza = raza;
	}

	public String getNombre() {
		return nombre;
	}

	public String getClase() {
		return clase;
	}

	public String getRaza() {
		return raza;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

	public void setRaza(String raza) {
		this.raza = raza;
	}

}
