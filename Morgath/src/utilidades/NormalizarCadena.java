package utilidades;

import java.text.Normalizer;

public class NormalizarCadena {

	// Método para normalizar una cadena y quitar acentos.
	public static String quitarAcentos(String cadena) {
	    if(cadena == null) {
	        return null;
	    }
	    
	    // Reemplazar los caracteres acentuados con sus equivalentes sin acento.
	    return Normalizer.normalize(cadena, Normalizer.Form.NFD)
	            .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
	}

}
