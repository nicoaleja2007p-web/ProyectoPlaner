package interfaz;

import modelo.Actividad;
import modelo.DiaDeViaje;
import modelo.Viaje;
import negocio.GestorReporte;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class VistaItinerario extends JPanel {

    private VentanaPrincipal ventana;
    private Viaje viajeActual;

    private JLabel labelTitulo;
    private JTextArea areaItinerario;
    private JScrollPane scroll;
    private JButton botonVerReporte;
    private JButton botonNuevoViaje;

    public VistaItinerario(VentanaPrincipal ventana) {
        this.ventana = ventana;
        inicializarComponentes();
        configurarLayout();
        configurarEventos();
    }

    private void inicializarComponentes() {
        setBackground(new Color(245, 245, 250));

        labelTitulo = new JLabel("Itinerario de tu viaje");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        labelTitulo.setForeground(new Color(41, 98, 160));

        areaItinerario = new JTextArea();
        areaItinerario.setFont(new Font("Courier New", Font.PLAIN, 13));
        areaItinerario.setEditable(false);
        areaItinerario.setBackground(new Color(255, 255, 255));
        areaItinerario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        scroll = new JScrollPane(areaItinerario);
        scroll.setPreferredSize(new java.awt.Dimension(600, 350));

        botonVerReporte = new JButton("Ver Reporte de Costos");
        botonVerReporte.setFont(new Font("Arial", Font.BOLD, 13));
        botonVerReporte.setBackground(new Color(41, 98, 160));
        botonVerReporte.setForeground(Color.WHITE);
        botonVerReporte.setFocusPainted(false);

        botonNuevoViaje = new JButton("Planificar otro viaje (cambiar preferencias)");
        botonNuevoViaje.setFont(new Font("Arial", Font.PLAIN, 13));
        botonNuevoViaje.setBackground(new Color(200, 200, 200));
        botonNuevoViaje.setFocusPainted(false);
    }

    private void configurarLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(labelTitulo, gbc);

        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(scroll, gbc);

        gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(botonVerReporte, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(botonNuevoViaje, gbc);
    }

    private void configurarEventos() {
        botonVerReporte.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ventana.mostrarPanel("reporte");
            }
        });

        botonNuevoViaje.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ventana.getFormularioPerfil().precargarPerfil();
                ventana.mostrarPanel("perfil");
            }
        });
    }

    public void mostrarItinerario(Viaje viaje, ArrayList<DiaDeViaje> itinerario) {
        this.viajeActual = viaje;
        String texto = "";
        texto = texto + "Viaje a: " + viaje.getDestino().toUpperCase() + "\n";
        texto = texto + "Fecha: " + viaje.getFechaInicio() + " al " + viaje.getFechaFin() + "\n";
        texto = texto + "Presupuesto: $" + viaje.getPresupuesto() + "\n";
        texto = texto + "==========================================\n\n";

        for (int i = 0; i < itinerario.size(); i++) {
            DiaDeViaje dia = itinerario.get(i);
            texto = texto + "Dia " + (i + 1) + " - " + dia.getFecha() + "\n";
            texto = texto + "  Costo del dia: $" + dia.getCostoDelDia() + "\n";
            texto = texto + "  Duración total: " + dia.getDuracionTotal() + " horas\n";
            texto = texto + "  Actividades:\n";

            ArrayList<Actividad> actividades = dia.getActividades();
            if (actividades.size() == 0) {
                texto = texto + "    - Sin actividades asignadas para este dia\n";
            } else {
                for (int j = 0; j < actividades.size(); j++) {
                    texto = texto + "    - " + actividades.get(j).toString() + "\n";
                }
            }
            texto = texto + "\n";
        }

        texto = texto + "==========================================\n";
        texto = texto + "Costo Total del viaje: $" + viaje.getCostoTotal() + "\n";

        if (viaje.getCostoTotal() < viaje.getPresupuesto()) {
            texto = texto + "El viaje esta dentro del presupuesto.\n";
        }
        areaItinerario.setText(texto);
        areaItinerario.setCaretPosition(0);
    }

    public Viaje getViajeActual() {
        return viajeActual;
    }
}