package objetos;

public class Objeto_Arma extends Objeto {
	
	private int valorVenta;
	private int ataque;

	public Objeto_Arma(String nombre, String descripcion, boolean objetoDeMision, int valorVenta, int ataque) {
		super(nombre, descripcion);
		
		setObjetoDeMision(objetoDeMision);
		this.valorVenta = valorVenta;
		this.ataque = ataque;
	}

	public int getAtaque() {
		return ataque;
	}
	
	public int getValorVenta() {
		return valorVenta;
	}
	

}
