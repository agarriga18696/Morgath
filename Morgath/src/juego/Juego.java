package juego;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import comandos.Comandos;
import configuracion.Config;
import misiones.Mision;
import misiones.Misiones;

public class Juego extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel panelPrincipal, panelCentral, panelSuperior, panelInferior;
	private JLabel labelUbicacion, labelPuntuacion, labelCursor;
	private JScrollPane barraScrollOutput;
	private JTextArea outputTexto;
	private JTextField inputTexto;

	private Jugador jugador = new Jugador("CAMINO");
	private Misiones misiones = new Misiones();
	private Comandos comandos = new Comandos(jugador, this, misiones);
	public Mision misionActiva;

	public Juego() {

		setSize(Config.anchoVentana, Config.altoVentana);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("MORGATH I");

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
				outputTexto(Config.CURSOR + " " + comando);
				comandos.ejecutarComando(comando);

				// Limpiar el campo de texto después de obtener el texto.
				inputTexto.setText("");

				// Notificar al bucle que se ha ingresado un comando.
				notificarComandoIngresado();
			}
		});

		// Barra Scroll.
		barraScrollOutput = new JScrollPane(outputTexto);
		barraScrollOutput.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		barraScrollOutput.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

		// Etiquetas.
		labelUbicacion = new JLabel("UBICACIÓN: " + jugador.getLocalizacion());
		labelPuntuacion = new JLabel("PUNTOS: " + jugador.getPuntos());
		labelCursor = new JLabel(Config.CURSOR);

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
		labelUbicacion.setFont(Config.fuente);
		labelUbicacion.setForeground(Config.colorSecundario);
		labelPuntuacion.setFont(Config.fuente);
		labelPuntuacion.setForeground(Config.colorSecundario);
		labelCursor.setFont(Config.fuente);
		labelCursor.setForeground(Config.colorSecundario);
		outputTexto.setFont(Config.fuente);
		outputTexto.setForeground(Config.colorSecundario);
		inputTexto.setFont(Config.fuente);
		inputTexto.setForeground(Config.colorSecundario);

		// Panel principal.
		panelPrincipal.setLayout(new BorderLayout());
		panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
		panelPrincipal.add(panelCentral, BorderLayout.CENTER);
		panelPrincipal.add(panelInferior, BorderLayout.SOUTH);
		panelPrincipal.setBackground(Config.colorPrincipal);

		// Panel superior.
		panelSuperior.setLayout(new GridLayout(1, 2));
		panelSuperior.add(labelUbicacion);
		panelSuperior.add(labelPuntuacion);
		labelPuntuacion.setHorizontalAlignment(SwingConstants.RIGHT);
		panelSuperior.setBorder(Config.borde);
		panelSuperior.setBackground(Config.colorPrincipal);

		// Panel central.
		panelCentral.setLayout(new BorderLayout());
		panelCentral.add(barraScrollOutput, BorderLayout.CENTER);
		panelCentral.setBackground(Config.colorPrincipal);
		barraScrollOutput.setBorder(null);
		outputTexto.setLineWrap(true);
		outputTexto.setWrapStyleWord(true);
		outputTexto.setBorder(Config.borde);
		outputTexto.setHighlighter(null);
		outputTexto.setEditable(false);
		outputTexto.setBackground(Config.colorPrincipal);

		// Panel inferior.
		panelInferior.setLayout(new BorderLayout());
		panelInferior.add(labelCursor, BorderLayout.WEST);
		panelInferior.add(inputTexto, BorderLayout.CENTER);
		panelInferior.setBackground(Config.colorPrincipal);
		labelCursor.setBorder(new EmptyBorder(0, 20, 0, 0));
		inputTexto.setBorder(new EmptyBorder(20, 10, 20, 20));
		inputTexto.setHighlighter(null);
		inputTexto.setBackground(Config.colorPrincipal);

		// Añadir paneles a la juego.
		getContentPane().add(panelPrincipal);

	}

	// Método para mostrar mensajes en el outputTexto.
	public void outputTexto(String texto) {
		outputTexto.append(texto + "\n");
		outputTexto.setCaretPosition(outputTexto.getDocument().getLength());
	}

	// Método para iniciar la partida.
	public void nuevaPartida() {
		misionActiva = misiones.ejecutarMisiones();
		
		while (true) {
			
			ejecutarMision();
			
			// Verificar si has terminado todas las misiones.
			if (misionActiva == null) {
				outputTexto("¡Has completado todas las misiones! ¡Felicidades!");
				break;
			}

			mostrarMensajeDeMision();

			// Finalizar la misión inicial (introducción).
			if (misionActiva.getNombre().equalsIgnoreCase("Iniciación")) {
				misionActiva.finalizarMision(misionActiva);
			}

			if (misionActiva.isCompletada()) {
				procesarMisionCompletada();
			}
			
			actualizarLabelPuntos();

			esperarComando();
		}
	}

	// Método para ejecutar la misión.
	private void ejecutarMision() {
		misionActiva = misiones.ejecutarMisiones();
	}

	// Mostrar mensaje de la misión.
	private void mostrarMensajeDeMision() {
		if (!misionActiva.isMensajeMostrado()) {
			String mensajeSistema = "- Nueva Misión: " + misionActiva.getNombre() + "\n\n" + misionActiva.getMensaje();
			outputTexto(mensajeSistema);
			
			misionActiva.setMensajeMostrado(true);
		}
	}

	// Completar una misión.
	private void procesarMisionCompletada() {
		int recompensa = misionActiva.getRecompensa();
		
		String mensaje = String.format("- ¡Misión \"%s\" completada!\n- Recompensa: %d puntos.", misionActiva.getNombre(), recompensa);
		outputTexto(mensaje);
		
		jugador.setPuntos(jugador.getPuntos() + recompensa);
		actualizarLabelPuntos();
		
		// Avanzar a la siguiente misión.
		avanzarASiguienteMision();
	}
	
	// Actualizar label de puntos.
	private void actualizarLabelPuntos() {
		labelPuntuacion.setText("PUNTOS: " + jugador.getPuntos());
	}

	// Método para esperar a que el jugador introduzca un comando.
	private void esperarComando() {
		synchronized (this) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		outputTexto(obtenerComandoJugador());
	}

	// Método para avanzar a la siguiente misión al completar una.
	private void avanzarASiguienteMision() {
		misionActiva = misiones.ejecutarMisiones();

		if (misionActiva != null && !misionActiva.isMensajeMostrado()) {
			String mensajeSistema = "\nNueva Misión: " + misionActiva.getNombre() + "\n\n" + misionActiva.getMensaje();
			outputTexto(mensajeSistema);
			misionActiva.setMensajeMostrado(true);
		}

		notificarComandoIngresado();
	}

	// Método para notificar al hilo principal cuando se ha ingresado un comando.
	public synchronized void notificarComandoIngresado() {
		notify();
	}

	// Método para obtener el comando del jugador del inputTexto.
	private String obtenerComandoJugador() {

		return inputTexto.getText().trim();
	}

}