package interfaz;

import modelo.HistorialViaje;
import modelo.Viaje;
import modelo.DiaDeViaje;
import modelo.Actividad;

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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class VistaHistorial extends JPanel {

    private VentanaPrincipal ventana;

    private JLabel labelTitulo;
    private JTextArea areaHistorial;
    private JScrollPane scroll;
    private JButton botonVolver;

    public VistaHistorial(VentanaPrincipal ventana) {
        this.ventana = ventana;
        inicializarComponentes();
        configurarLayout();
        configurarEventos();
    }

    private void inicializarComponentes() {
        setBackground(new Color(245, 245, 250));

        labelTitulo = new JLabel("Historial de Viajes");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        labelTitulo.setForeground(new Color(41, 98, 160));

        areaHistorial = new JTextArea();
        areaHistorial.setFont(new Font("Courier New", Font.PLAIN, 13));
        areaHistorial.setEditable(false);
        areaHistorial.setBackground(Color.WHITE);
        areaHistorial.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        scroll = new JScrollPane(areaHistorial);
        scroll.setPreferredSize(new java.awt.Dimension(620, 380));

        botonVolver = new JButton("Volver al Planificador");
        botonVolver.setFont(new Font("Arial", Font.BOLD, 13));
        botonVolver.setBackground(new Color(41, 98, 160));
        botonVolver.setForeground(Color.WHITE);
        botonVolver.setFocusPainted(false);
    }

    private void configurarLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(labelTitulo, gbc);

        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(scroll, gbc);

        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        add(botonVolver, gbc);
    }

    private void configurarEventos() {
        botonVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ventana.mostrarPanel("planificador");
            }
        });
    }

    public void mostrarHistorial(HistorialViaje historial) {
        ArrayList<Viaje> viajes = historial.obtenerViajes();
        String texto = "";

        if (viajes.size() == 0) {
            texto = "Todavía no tienes viajes registrados.\nPlanifica tu primer viaje!";
        } else {
            texto = " Tus Viajes Anteriores   \n\n";
            for (int i = 0; i < viajes.size(); i++) {
                Viaje viaje = viajes.get(i);
                texto = texto + "Viaje " + (i + 1) + ":\n";
                texto = texto + "  Destino:      " + viaje.getDestino() + "\n";
                texto = texto + "  Fechas:       " + viaje.getFechaInicio() + " - " + viaje.getFechaFin() + "\n";
                texto = texto + "  Duración:     " + viaje.calcularDuracion() + " días\n";
                texto = texto + "  Presupuesto:  $" + viaje.getPresupuesto() + "\n";
                texto = texto + "  Gasto total:  $" + viaje.getCostoTotal() + "\n";

                if (viaje.getCostoTotal() > viaje.getPresupuesto()) {
                    texto = texto + "  ALERTA: Supero el presupuesto\n";
                } else {
                    texto = texto + "  Dentro del presupuesto\n";
                }

                ArrayList<DiaDeViaje> dias = viaje.getDias();
                if (dias.size() > 0) {
                    texto = texto + "  Actividades:\n";
                    for (int j = 0; j < dias.size(); j++) {
                        DiaDeViaje dia = dias.get(j);
                        texto = texto + "    Dia " + (j + 1) + " - " + dia.getFecha() + ":\n";
                        ArrayList<Actividad> actividades = dia.getActividades();
                        if (actividades.size() == 0) {
                            texto = texto + "      Sin actividades.\n";
                        } else {
                            for (int k = 0; k < actividades.size(); k++) {
                                Actividad act = actividades.get(k);
                                texto = texto + "      - " + act.getNombre()
                                        + " ($" + act.getCosto() + ")"
                                        + " [" + act.getDuracion() + "h]"
                                        + " - " + act.getCategoria() + "\n";
                            }
                        }
                    }
                }

                texto = texto + "\n";
            }

            texto = texto + "==========================================\n";
            texto = texto + "Gastos Acumulados: $" + historial.calcularGastoTotal() + "\n";
            texto = texto + "Total de Viajes:   " + viajes.size() + "\n";
        }
        areaHistorial.setText(texto);
        areaHistorial.setCaretPosition(0);
    }
}