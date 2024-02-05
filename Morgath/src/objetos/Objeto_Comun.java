package objetos;

public class Objeto_Comun extends Objeto {
	
	public Objeto_Comun(String nombre, String descripcion, int valorVenta, boolean objetoDeMision) {
		super(nombre, descripcion);
		
		setValorVenta(valorVenta);
		setObjetoDeMision(objetoDeMision);
	}
	

}
