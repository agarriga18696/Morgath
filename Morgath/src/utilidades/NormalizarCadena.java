package utilidades;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class NormalizarCadena {

	// Método para normalizar una cadena y omitir acentos.
	public static String quitarAcentos(String cadena) {
		String comandoNormalizado = Normalizer.normalize(cadena, Normalizer.Form.NFD);
		Pattern patron = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return patron.matcher(comandoNormalizado).replaceAll("");
	}

}
