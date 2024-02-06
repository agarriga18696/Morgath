package personajes;


public class Enemigo extends Personaje implements Conversar {

	private String conversacion;
	private boolean esJefe;
	private int recompensaPorMatar;
	
	public Enemigo(String nombre, int vidas, String conversacion, boolean esJefe, int recompensaPorMatar) {
		super(nombre, vidas);
		
		this.conversacion = conversacion;;
		this.esJefe = esJefe;
		this.recompensaPorMatar = recompensaPorMatar;
	}

	
	@Override
	public String obtenerConversacion() {
		return this.conversacion;
	}

	@Override
	public void establecerConversacion(String nuevaConversacion) {
		this.conversacion = nuevaConversacion;
	}

	public boolean isEsJefe() {
		return esJefe;
	}


	public int getRecompensaPorMatar() {
		return recompensaPorMatar;
	}
	
	
}
