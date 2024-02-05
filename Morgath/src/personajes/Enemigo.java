package personajes;


public class Enemigo extends Personaje {

	private boolean esJefe;
	private int recompensaPorMatar;
	
	public Enemigo(String nombre, boolean esJefe, int recompensaPorMatar, int vidas) {
		super(nombre, vidas);
		
		this.esJefe = esJefe;
		this.recompensaPorMatar = recompensaPorMatar;
	}
	
}
