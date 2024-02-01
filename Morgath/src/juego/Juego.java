package juego;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import comandos.Comandos;
import configuracion.Config;

public class Juego extends JFrame {

	private JPanel panelPrincipal, panelSuperior, panelInput;
	private JLabel labelLocalizacion, labelPuntuacion, labelCursor;
	private JScrollPane barraScrollOutput, barraScrollInput;
	private JTextArea textoOutput;
	private JTextField textoInput;

	private Jugador jugador = new Jugador("CAMINO");
	private Comandos comandos = new Comandos(jugador, this);

	public Juego() {

		setSize(Config.anchoVentana, Config.altoVentana);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle(jugador.getLocalizacion().toUpperCase());

		// Paneles.
		panelPrincipal = new JPanel(new BorderLayout());
		panelSuperior = new JPanel(new BorderLayout());
		panelInput = new JPanel(new BorderLayout()); // Nuevo panel para textoInput

		// Area Texto.
		textoOutput = new JTextArea();
		textoInput = new JTextField();

		textoInput.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        String comando = textoInput.getText();
		        outputTexto(comando);
		        comandos.ejecutarComando(comando);

		        textoInput.setText(""); // Limpiar el campo de texto después de obtener el texto.

		        // Notificar al bucle que se ha ingresado un comando
		        notificarComandoIngresado();
		    }
		});

		// Barra Scroll.
		barraScrollOutput = new JScrollPane(textoOutput);
		barraScrollOutput.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		barraScrollOutput.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

		barraScrollInput = new JScrollPane(textoInput);
		barraScrollInput.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		barraScrollInput.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

		// Etiquetas.
		labelLocalizacion = new JLabel(jugador.getLocalizacion());
		labelPuntuacion = new JLabel("PUNTOS: " + jugador.getPuntos());
		labelCursor = new JLabel(">");

		configurarVentana();
		setVisible(true);

	}

	private void configurarVentana() {

		// Establecer fuente y color.
		labelLocalizacion.setFont(Config.fuente);
		labelLocalizacion.setForeground(Color.BLACK);
		labelPuntuacion.setFont(Config.fuente);
		labelPuntuacion.setForeground(Color.BLACK);
		labelCursor.setFont(Config.fuente);
		labelCursor.setForeground(new Color(0, 0, 0));
		textoOutput.setFont(Config.fuente);
		textoOutput.setForeground(new Color(0, 0, 0));
		textoInput.setFont(Config.fuente);
		textoInput.setForeground(new Color(0, 0, 0));

		// Panel superior.
		panelSuperior.add(labelLocalizacion, BorderLayout.WEST);
		panelSuperior.add(labelPuntuacion, BorderLayout.EAST);
		panelSuperior.setBorder(new EmptyBorder(10, 10, 5, 10));

		// Panel input.
		panelInput.add(labelCursor, BorderLayout.WEST); // Agregar el labelCursor a la izquierda.
		panelInput.add(barraScrollInput, BorderLayout.CENTER);

		// Panel principal.
		panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
		panelPrincipal.add(barraScrollOutput, BorderLayout.CENTER);
		panelPrincipal.add(panelInput, BorderLayout.SOUTH); // Agregar el panelInput a la parte inferior.

		// Area texto.
		textoOutput.setLineWrap(true);
		textoOutput.setWrapStyleWord(true);
		textoOutput.setBorder(new EmptyBorder(10, 10, 10, 10));

		// Texto input.
		textoInput.setBorder(new EmptyBorder(10, 10, 10, 10));
		//textoInput.setHighlighter(null);

		panelInput.setBorder(new EmptyBorder(0, 10, 0, 0));

		// Establecer color de fondo.
		panelPrincipal.setBackground(Color.BLACK);
		panelSuperior.setBackground(Color.LIGHT_GRAY);
		panelInput.setBackground(new Color(255, 255, 255));
		textoOutput.setBackground(new Color(255, 255, 255));
		textoInput.setBackground(new Color(255, 255, 255));

		// Añadir paneles a la juego.
		getContentPane().add(panelPrincipal);

	}

	// Método para mostrar mensajes en el textoOutput.
	public void outputTexto(String texto) {
	    textoOutput.append(texto + "\n");
	    textoOutput.setCaretPosition(textoOutput.getDocument().getLength());
	}

	// Método para iniciar una partida nueva.
	public void nuevaPartida() {
		String mensajeSistema = "¡Saludos, aventurero! Te doy la bienvenida.";
		outputTexto(mensajeSistema);

		while (true) {
	        // Esperar a que el usuario ingrese un comando
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

	        // Verificar si el jugador escribió "TERMINAR"
	        if (comando.equalsIgnoreCase("TERMINAR")) {
	            outputTexto("¡Hasta la próxima, aventurero!");
	            break;  // Salir del bucle
	        } else {
	            // Llamar al método ejecutarComando de la clase Comandos
	            comandos.ejecutarComando(comando);
	        }
	    }
	}
	
	// Método para notificar al hilo principal cuando se ha ingresado un comando.
	public synchronized void notificarComandoIngresado() {
	    notify();
	}

	// Método para obtener el comando del jugador del textoInput.
	private String obtenerComandoJugador() {

		return textoInput.getText().trim();
	}

}