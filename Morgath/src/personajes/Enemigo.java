package personajes;

public class Enemigo extends PersonajeBase {

	private String conversacion;
	private Tipo tipo;
	private boolean esJefe;
	private int recompensaPorMatar;

	public Enemigo(String nombre, String conversacion, Tipo tipo, int vidas, boolean esJefe, int recompensaPorMatar) {
		super(nombre, vidas);

		this.conversacion = conversacion;
		this.tipo = tipo == null ? Tipo.DESCONOCIDO : tipo;
		this.esJefe = esJefe;
		this.recompensaPorMatar = recompensaPorMatar;
	}

	// Tipos de enemigo.
	public enum Tipo {
		MALEANTE,
		MERCENARIO,
		BANDIDO,
		CRIATURA,
		BESTIA,
		DEMONIO,
		NO_MUERTO,
		DESCONOCIDO;

		public String getTipoLegible() {
			switch(this) {
			case NO_MUERTO:
				return "NO MUERTO";
			default:
				return this.toString();
			}
		}
	}


	public Tipo getTipo() {
		return tipo;
	}

	public String getConversacion() {
		return conversacion;
	}

	public boolean isEsJefe() {
		return esJefe;
	}

	public int getRecompensaPorMatar() {
		return recompensaPorMatar;
	}


}
