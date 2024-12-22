package objetos;

public class Objeto_Arma extends Objeto {
	
	private int valorVenta;
	private int ataque;
	private boolean objetoDeMision;

	public Objeto_Arma(String icono, String nombre, String descripcion, boolean objetoDeMision, int valorVenta, int ataque) {
		super(icono, nombre, descripcion);
		
		this.objetoDeMision = objetoDeMision;
		this.valorVenta = valorVenta;
		this.ataque = ataque;
	}

	public int getAtaque() {
		return ataque;
	}
	
	public int getValorVenta() {
		return valorVenta;
	}

	public boolean isObjetoDeMision() {
		return objetoDeMision;
	}

	public void setObjetoDeMision(boolean objetoDeMision) {
		this.objetoDeMision = objetoDeMision;
	}
	

}
