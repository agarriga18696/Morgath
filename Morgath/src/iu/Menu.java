package iu;

import java.util.Scanner;

import principal.Aventura;
import utilidades.ValidarEntrada;

public class Menu {

	private static Scanner cin = new Scanner(System.in);

	// Menú principal.
	public static void MenuPrincipal(String msgOpcion) {

		Titulo.titulo_1("Morgath");

		System.out.println(Texto.NEGRITA + " 1." + Texto.RESET + " Comenzar aventura");
		System.out.println(Texto.NEGRITA + " 2." + Texto.RESET + " Ayuda");
		System.out.println(Texto.NEGRITA + " 3." + Texto.RESET + " Salir");

		boolean opcionValida = false;

		while(!opcionValida) {

			int opcion = ValidarEntrada.Int(cin, msgOpcion);

			switch(opcion) {
			case 1:
				opcionValida = true;
				System.out.println("\n Comenzar nueva aventura");
				Aventura.NuevaAventura();
				break;
			case 2:
				opcionValida = true;
				System.out.println("\n Ayuda");
				break;
			case 3:
				opcionValida = true;
				System.out.println("\n Salir del juego");
				System.exit(0);
				break;
			default:
				Mensaje.Error("Opción no válida");
				break;
			}

		}

	}

	// Mostrar menú con opciones.
	public static void MenuOpciones(String... opciones) {

		for(int i = 0; i < opciones.length; i++) {

			System.out.println(" " + (i+1) + ". " + opciones[i]);

		}

	}

}
