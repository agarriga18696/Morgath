package configuracion;

import java.awt.Color;

public class Tema {
	
	// Atributos.
	private Color colorPrincipal;
	private Color colorSecundario;
	private Color colorEnfasis;

	public Tema(Color colorPrincipal, Color colorSecundario, Color colorEnfasis) {
		this.colorPrincipal = colorPrincipal;
		this.colorSecundario = colorSecundario;
		this.colorEnfasis = colorEnfasis;
	}

	public Color getColorPrincipal() {
		return colorPrincipal;
	}

	public Color getColorSecundario() {
		return colorSecundario;
	}

	public Color getColorEnfasis() {
		return colorEnfasis;
	}
	
}