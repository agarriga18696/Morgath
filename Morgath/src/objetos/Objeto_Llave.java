package objetos;

public class Objeto_Llave extends Objeto {
	
	private boolean maestra;

	public Objeto_Llave(String icono, String nombre, String descripcion, Rareza rareza, boolean deMision, Integer valorVenta, boolean maestra) {
		super(icono, nombre, descripcion, rareza, deMision, valorVenta);
		
		this.maestra = maestra;
	}

	public boolean isMaestra() {
		return maestra;
	}

	public void setMaestra(boolean maestra) {
		this.maestra = maestra;
	}
	
}
