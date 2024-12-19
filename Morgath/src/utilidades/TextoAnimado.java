package utilidades;

import javax.swing.SwingUtilities;

public class TextoAnimado {

	private static long tiempo = 20; // milisegundos

	// Mostrar el texto caracter a caracter como una maquina de escribir.
	public static void maquinaEscribir(String texto, Runnable onCharacterPrinted) {
		new Thread(() ->{
			try {
				for(int i = 0; i < texto.trim().length(); i++) {
					Thread.sleep(tiempo);
					SwingUtilities.invokeLater(onCharacterPrinted);
				}

			} catch(Exception e) {
				e.printStackTrace();
			}

		}).start();
	}
}
