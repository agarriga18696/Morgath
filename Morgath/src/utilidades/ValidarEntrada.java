package utilidades;

import java.util.Scanner;

import iu.Mensaje;
import iu.Texto;

public class ValidarEntrada {

	// Validar Int.
	public static int Int(Scanner cin, String mensaje) {
		int numero = 0;

		while(true) {
			System.out.print("\n " + mensaje + ": ");

			if(cin.hasNextInt()) {
				numero = cin.nextInt();
				cin.nextLine();
				return numero;
			} else {
				Mensaje.Error("El valor introducido no es válido");
				cin.nextLine();
			}
		}
	}

	// Validar Double.
	public static double Double(Scanner cin, String mensaje) {
		double numero = 0;

		while(true) {
			System.out.print("\n " + mensaje + ": ");

			if(cin.hasNextDouble()) {
				numero = cin.nextDouble();
				cin.nextLine();
				return numero;
			} else {
				Mensaje.Error("El valor introducido no es válido");
				cin.nextLine();
			}
		}
	}

	// Validar String.
	public static String String(Scanner cin, String mensaje) {
		String cadena = null;

		while(true) {
			System.out.print("\n " + mensaje + ": ");

			if(cin.hasNextLine()) {
				cadena = cin.nextLine();
				return cadena;
			} else {
				Mensaje.Error("El caracter introducido no es válido");
				cin.nextLine();
			}
		}
	}

}
