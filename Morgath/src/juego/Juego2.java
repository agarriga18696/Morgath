/*package juego;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import comandos.Comandos;
import configuracion.Config;
import misiones.Mision;
import misiones.Misiones;

public class Juego2 extends JFrame {

	private JPanel panelPrincipal, panelCentral, panelSuperior, panelInferior;
	private JLabel labelLocalizacion, labelPuntuacion, labelCursor;
	private JScrollPane barraScrollOutput;
	private JTextArea outputTexto;
	private JTextField inputTexto;

	private Jugador jugador = new Jugador("CAMINO");
	private Misiones misiones = new Misiones();
	private Comandos comandos = new Comandos(jugador, this, misiones);
	public Mision misionActiva = misiones.ejecutarMisiones();

	public Juego2() {

		setSize(Config.anchoVentana, Config.altoVentana);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle(jugador.getLocalizacion().toUpperCase());

		// Paneles.
		panelPrincipal = new JPanel(new BorderLayout());
		panelCentral = new JPanel(new GridLayout(1, 0));
		panelSuperior = new JPanel(new GridLayout(1, 2));
		panelInferior = new JPanel(new BorderLayout());

		// Area Texto.
		outputTexto = new JTextArea();
		inputTexto = new JTextField();

		inputTexto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String comando = inputTexto.getText();
				outputTexto(comando);
				comandos.ejecutarComando(comando);

				inputTexto.setText(""); // Limpiar el campo de texto después de obtener el texto.

				// Notificar al bucle que se ha ingresado un comando
				notificarComandoIngresado();
			}
		});

		// Barra Scroll.
		barraScrollOutput = new JScrollPane(outputTexto);
		barraScrollOutput.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		barraScrollOutput.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

		// Etiquetas.
		labelLocalizacion = new JLabel(jugador.getLocalizacion());
		labelPuntuacion = new JLabel("PUNTOS: " + jugador.getPuntos());
		labelCursor = new JLabel(">");

		configurarVentana();
		setVisible(true);

		SwingUtilities.invokeLater(() -> {
			// Hacer autofocus en la ventana y en el inputTexto.
			requestFocusInWindow();
			inputTexto.requestFocus();
		});

	}

	private void configurarVentana() {

		// Establecer fuente y color.
		labelLocalizacion.setFont(Config.fuente);
		labelLocalizacion.setForeground(new Color(0, 0, 0));
		labelPuntuacion.setFont(Config.fuente);
		labelPuntuacion.setForeground(new Color(0, 0, 0));
		labelCursor.setFont(Config.fuente);
		labelCursor.setForeground(new Color(180, 180, 180));
		outputTexto.setFont(Config.fuente);
		outputTexto.setForeground(new Color(180, 180, 180));
		inputTexto.setFont(Config.fuente);
		inputTexto.setForeground(new Color(180, 180, 180));

		// Panel principal.
		panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
		panelPrincipal.add(panelCentral, BorderLayout.CENTER);
		panelPrincipal.add(panelInferior, BorderLayout.SOUTH);
		
		// Panel superior.
		panelSuperior.add(labelLocalizacion, 1, 0);
		panelSuperior.add(labelPuntuacion, BorderLayout.EAST);
		panelSuperior.setBorder(new EmptyBorder(10, 10, 5, 10));

		// Panel input.
		panelInferior.add(labelCursor, BorderLayout.WEST);
		panelInferior.add(inputTexto, BorderLayout.CENTER);

		// Texto Output.
		outputTexto.setLineWrap(true);
		outputTexto.setWrapStyleWord(true);
		outputTexto.setBorder(new EmptyBorder(10, 10, 10, 10));
		outputTexto.setHighlighter(null);
		outputTexto.setEditable(false);

		// Texto Input.
		inputTexto.setBorder(new EmptyBorder(10, 10, 10, 10));
		inputTexto.setHighlighter(null);

		panelInferior.setBorder(new EmptyBorder(0, 10, 0, 0));

		// Establecer color de fondo.
		panelPrincipal.setBackground(new Color(0, 0, 0));
		panelSuperior.setBackground(new Color(180, 180, 180));
		panelInferior.setBackground(new Color(0, 0, 0));
		outputTexto.setBackground(new Color(0, 0, 0));
		inputTexto.setBackground(new Color(0, 0, 0));

		// Añadir paneles a la juego.
		getContentPane().add(panelPrincipal);

	}

	// Método para mostrar mensajes en el outputTexto.
	public void outputTexto(String texto) {
		outputTexto.append(texto + "\n");
		outputTexto.setCaretPosition(outputTexto.getDocument().getLength());
	}

	// Método para iniciar una partida nueva.
	public void nuevaPartida() {
		while (true) {

			if (misionActiva == null) {
				// Si no hay más misiones activas, terminar el juego.
				outputTexto("¡Has completado todas las misiones! ¡Felicidades!");
				break;
			}

			// Mostrar el mensaje de la misión al jugador (mostrar solo una vez).
			if(!misionActiva.isMensajeMostrado()) {
				String mensajeSistema = misionActiva.getMensaje();
				outputTexto(mensajeSistema);
				misionActiva.setMensajeMostrado(true);
			}

			// Esperar a que el jugador escriba un comando.
			synchronized (this) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			// Obtener el último comando ingresado por el jugador
			String comando = obtenerComandoJugador();
			outputTexto(comando);

			// Verificar que el jugador haya escrito "TERMINAR".
			if (comando.equalsIgnoreCase("TERMINAR")) {
				outputTexto("¡Hasta la próxima, aventurero!");
				break; // Salir del bucle
			} else {
				// Ejecutar el comando.
				comandos.ejecutarComando(comando);
			}
		}
	}

	// Método para notificar al hilo principal cuando se ha ingresado un comando.
	public synchronized void notificarComandoIngresado() {
		notify();
	}

	// Método para obtener el comando del jugador del inputTexto.
	private String obtenerComandoJugador() {

		return inputTexto.getText().trim();
	}

}*/