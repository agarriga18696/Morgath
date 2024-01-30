package iu;

import personajes.PNJ;

public class Mensaje {

	public static void Error(String mensaje) {
		System.out.println(Color.ROJO + Texto.NEGRITA + "\n Error: " + Texto.RESET + Color.ROJO + mensaje + "." + Texto.RESET + "\n");
	}

	public static void Advertencia(String mensaje) {
		System.out.println(Color.AMARILLO + Texto.NEGRITA + "\n Advertencia: " + Texto.RESET + Color.AMARILLO + mensaje + "." + Texto.RESET + "\n");
	}

	public static void Exito(String mensaje) {
		System.out.println(Color.VERDE + Texto.NEGRITA + "\n Éxito: " + Texto.RESET + Color.VERDE + mensaje + "." + Texto.RESET + "\n");
	}

	public static void PNJ(String nombre, String oficio, String genero, String mensaje) {

		System.out.println(Color.CIAN + "\n [" + nombre + "][" + oficio + "]: " + Texto.RESET + mensaje + "\n");

	}

}
