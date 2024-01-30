package objetos;

public class Pocion extends Objeto {
	
	private String tipo;
	private int cantidadEfecto;
	
	public Pocion(String nombre, String rareza, String descripcion, int valor, double peso, boolean consumible, boolean objetoDeMision, String tipo, int cantidadEfecto) {
		super(nombre, rareza, descripcion, valor, peso, consumible, objetoDeMision);
		
		this.tipo = tipo;
		this.cantidadEfecto = cantidadEfecto;
		
	}
	
	public enum Tipo {
		
		SALUD, MAGIA, CURAR_ENFERMEDAD, VENENO, UTILIDAD
		
	}

}
