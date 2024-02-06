package objetos;

public class Objeto_Comun extends Objeto {
	
	private int valorVenta;
	
	public Objeto_Comun(String nombre, String descripcion, boolean objetoDeMision, int valorVenta) {
		super(nombre, descripcion);
		
		setObjetoDeMision(objetoDeMision);
		this.valorVenta = valorVenta;
	}

	public int getValorVenta() {
		return valorVenta;
	}
	
/*
 * 
 * 
 * 
 * 
 * 
 * 
 */
	
	
}

