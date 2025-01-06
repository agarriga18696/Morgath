package objetos;

public class Objeto_Arma extends Objeto {
	
	private int ataque;

	public Objeto_Arma(String icono, String nombre, String descripcion, Rareza rareza, boolean deMision, Integer valorVenta, int ataque) {
		super(icono, nombre, descripcion, rareza, deMision, valorVenta);

		this.ataque = ataque;
	}

	public int getAtaque() {
		return ataque;
	}

}
