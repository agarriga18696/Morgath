package utilidades;

public class ConvertirTexto {
	
	public static String CamelCase(String texto) {
		// Convertir el texto a minúscula y eliminar espacios innecesarios.
		String[] palabras = texto.trim().toLowerCase().split("\\s+");
		
		StringBuilder textoConvertido = new StringBuilder();
		
		// Capitalizar la primera letra de cada palabra.
		for(String palabra : palabras) {
			
			if(!palabra.isEmpty()) {
				
				// Añadir espacio en blanco antes de cada palabra, menos en la primera.
				if(textoConvertido.length() > 0) {
					textoConvertido.append(' ');
				}
				
				// Convertir a mayúscula el primer caracter de cada palabra.
				textoConvertido.append(Character.toUpperCase(palabra.charAt(0)));
				// Añadir el resto de la palabra (minúscula).
				if(palabra.length() > 1) {
					textoConvertido.append(palabra.substring(1));
				}
			}
			
		}
		
		return textoConvertido.toString();
		
	}

}
