package objetos;

public class Objeto_Comun extends Objeto {
	
	private int valorVenta;
	private boolean objetoDeMision;
	
	public Objeto_Comun(String icono, String nombre, String descripcion, boolean objetoDeMision, int valorVenta) {
		super(icono, nombre, descripcion);
		
		this.objetoDeMision = objetoDeMision;
		this.valorVenta = valorVenta;
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

