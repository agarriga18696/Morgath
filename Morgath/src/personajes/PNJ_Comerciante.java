package personajes;

import objetos.Objeto;


public class PNJ_Comerciante extends PNJ {

	public PNJ_Comerciante(String nombre, String conversacion, int vidas, Objeto... objeto) {
		super(nombre, conversacion, vidas, objeto);

	}

	@Override
	public String obtenerConversacion() {
		return this.conversacion;
	}

}
