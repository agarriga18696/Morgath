package objetos;

public class Objeto_Arma extends Objeto {
	
	private int ataque;

	public Objeto_Arma(String nombre, String descripcion, int valorVenta, boolean objetoDeMision, int ataque) {
		super(nombre, descripcion);
		
		setValorVenta(valorVenta);
		setObjetoDeMision(objetoDeMision);
		this.ataque = ataque;
	}

	public int getAtaque() {
		return ataque;
	}

	public void setAtaque(int ataque) {
		this.ataque = ataque;
	}
	

}
