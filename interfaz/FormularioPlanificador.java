package interfaz;

import negocio.GestorActividades;
import negocio.Planificador;
import modelo.DiaDeViaje;
import modelo.PerfilPreferencias;
import modelo.Viaje;
import excepciones.PresupuestoExcedidoException;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FormularioPlanificador extends JPanel {

    private VentanaPrincipal ventana;
    private GestorActividades gestorActividades;

    private JLabel labelTitulo;
    private JLabel labelDestino;
    private JLabel labelFechaInicio;
    private JLabel labelFechaFin;
    private JLabel labelPresupuesto;
    private JTextField campoDestino;
    private JTextField campoFechaInicio;
    private JTextField campoFechaFin;
    private JTextField campoPresupuesto;
    private JButton botonGenerar;
    private JButton botonHistorial;
    private JButton botonCerrarSesion;
    private JLabel labelMensaje;

    public FormularioPlanificador(VentanaPrincipal ventana, GestorActividades gestorActividades) {
        this.ventana = ventana;
        this.gestorActividades = gestorActividades;
        inicializarComponentes();
        configurarLayout();
        configurarEventos();
    }

    private void inicializarComponentes() {
        setBackground(new Color(245, 245, 250));

        labelTitulo = new JLabel("Planifica tu viaje");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        labelTitulo.setForeground(new Color(41, 98, 160));

        labelDestino = new JLabel("Destino:");
        labelFechaInicio = new JLabel("Fecha inicio (dd/mm/aaaa):");
        labelFechaFin = new JLabel("Fecha fin (dd/mm/aaaa):");
        labelPresupuesto = new JLabel("Presupuesto total ($):");

        campoDestino = new JTextField(15);
        campoFechaInicio = new JTextField(15);
        campoFechaFin = new JTextField(15);
        campoPresupuesto = new JTextField(15);

        botonGenerar = new JButton("Generar Itinerario");
        botonGenerar.setFont(new Font("Arial", Font.BOLD, 13));
        botonGenerar.setBackground(new Color(41, 98, 160));
        botonGenerar.setForeground(Color.WHITE);
        botonGenerar.setFocusPainted(false);

        botonHistorial = new JButton("Ver Historial");
        botonHistorial.setFont(new Font("Arial", Font.PLAIN, 13));
        botonHistorial.setBackground(new Color(200, 200, 200));
        botonHistorial.setFocusPainted(false);

        botonCerrarSesion = new JButton("Cerrar Sesion");
        botonCerrarSesion.setFont(new Font("Arial", Font.PLAIN, 13));
        botonCerrarSesion.setBackground(new Color(180, 50, 50));
        botonCerrarSesion.setForeground(Color.WHITE);
        botonCerrarSesion.setFocusPainted(false);

        labelMensaje = new JLabel(" ");
        labelMensaje.setFont(new Font("Arial", Font.ITALIC, 12));
        labelMensaje.setForeground(Color.RED);
    }

    private void configurarLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(labelTitulo, gbc);

        gbc.gridwidth = 1;
        agregarFila(gbc, labelDestino, campoDestino, 1);
        agregarFila(gbc, labelFechaInicio, campoFechaInicio, 2);
        agregarFila(gbc, labelFechaFin, campoFechaFin, 3);
        agregarFila(gbc, labelPresupuesto, campoPresupuesto, 4);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(labelMensaje, gbc);

        gbc.gridy = 6; gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.EAST;
        add(botonGenerar, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        add(botonHistorial, gbc);

        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(botonCerrarSesion, gbc);
    }

    private void agregarFila(GridBagConstraints gbc, JLabel label, JTextField campo, int fila) {
        gbc.gridx = 0; gbc.gridy = fila; gbc.anchor = GridBagConstraints.EAST;
        add(label, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        add(campo, gbc);
    }

    private void configurarEventos() {
        botonGenerar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                generarItinerario();
            }
        });

        botonHistorial.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ventana.getVistaHistorial().mostrarHistorial(ventana.getGestorUsuario().obtenerHistorial());
                ventana.mostrarPanel("historial");
            }
        });

        botonCerrarSesion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int confirmacion = JOptionPane.showConfirmDialog(
                        ventana,
                        "¿Estas seguro que deseas cerrar sesion?",
                        "Cerrar Sesion",
                        JOptionPane.YES_NO_OPTION
                );
                if (confirmacion == JOptionPane.YES_OPTION) {
                    ventana.getGestorUsuario().logout();
                    campoDestino.setText("");
                    campoFechaInicio.setText("");
                    campoFechaFin.setText("");
                    campoPresupuesto.setText("");
                    labelMensaje.setText(" ");
                    ventana.mostrarPanel("login");
                }
            }
        });
    }

    private void generarItinerario() {
        String destino = campoDestino.getText().trim();
        String fechaInicio = campoFechaInicio.getText().trim();
        String fechaFin = campoFechaFin.getText().trim();
        String presupuestoTexto = campoPresupuesto.getText().trim();

        if (destino.equals("") || fechaInicio.equals("") || fechaFin.equals("") || presupuestoTexto.equals("")) {
            labelMensaje.setText("Error: Todos los campos son obligatorios.");
            return;
        }

        if (!esDestinoValido(destino)) {
            labelMensaje.setText("Error: El destino no debe contener numeros.");
            return;
        }

        double presupuesto = 0.0;
        try {
            presupuesto = Double.parseDouble(presupuestoTexto);
        } catch (NumberFormatException ex) {
            labelMensaje.setText("Error: El presupuesto debe ser un numero válido.");
            return;
        }

        if (presupuesto <= 0) {
            labelMensaje.setText("Error: El presupuesto debe ser mayor a cero.");
            return;
        }

        ArrayList<String> fechas = generarFechas(fechaInicio, fechaFin);
        if (fechas.size() == 0) {
            labelMensaje.setText("Error: Las fechas no son validas o la fecha fin es anterior a la inicio.");
            return;
        }

        int duracionDias = fechas.size();
        double presupuestoMinimoRequerido = 1000.0;
        if (duracionDias >= 10 && presupuesto < presupuestoMinimoRequerido) {
            labelMensaje.setText("Error: Para viajes de 10 dias o mas, el presupuesto minimo es $" + presupuestoMinimoRequerido + ".");
            return;
        }

        PerfilPreferencias perfil = ventana.getPerfilActual();
        Viaje viaje = new Viaje(1, destino, fechaInicio, fechaFin, presupuesto);
        Planificador planificador = new Planificador(viaje, gestorActividades.getActividades(), perfil);

        try {
            planificador.ajustarPresupuesto(presupuesto);
        } catch (PresupuestoExcedidoException ex) {
            labelMensaje.setText("Error: " + ex.getMessage());
            return;
        }

        ArrayList<DiaDeViaje> itinerario = planificador.generarItinerario(fechas);

        ventana.getGestorUsuario().agregarViajeAlHistorial(viaje);
        ventana.getVistaItinerario().mostrarItinerario(viaje, itinerario);
        ventana.getVistaReporte().mostrarReporte(viaje);
        labelMensaje.setText(" ");
        ventana.mostrarPanel("itinerario");
    }

    private boolean esDestinoValido(String destino) {
        return !destino.matches(".*\\d.*");
    }

    private ArrayList<String> generarFechas(String inicio, String fin) {
        ArrayList<String> fechas = new ArrayList<String>();
        try {
            String[] parteInicio = inicio.split("/");
            String[] parteFin = fin.split("/");

            if (parteInicio.length != 3 || parteFin.length != 3) {
                return fechas;
            }

            int diaInicio = Integer.parseInt(parteInicio[0]);
            int mesInicio = Integer.parseInt(parteInicio[1]);
            int anioInicio = Integer.parseInt(parteInicio[2]);

            int diaFin = Integer.parseInt(parteFin[0]);
            int mesFin = Integer.parseInt(parteFin[1]);
            int anioFin = Integer.parseInt(parteFin[2]);

            if (diaInicio < 1 || diaInicio > 31 || diaFin < 1 || diaFin > 31) { return fechas; }
            if (mesInicio < 1 || mesInicio > 12 || mesFin < 1 || mesFin > 12) { return fechas; }

            int totalInicio = anioInicio * 10000 + mesInicio * 100 + diaInicio;
            int totalFin = anioFin * 10000 + mesFin * 100 + diaFin;

            int dia = diaInicio;
            int mes = mesInicio;
            int anio = anioInicio;
            int contador = 0;

            while (contador < 30) {
                fechas.add(dia + "/" + mes + "/" + anio);
                if (dia == diaFin && mes == mesFin && anio == anioFin) { break; }
                dia = dia + 1;
                if (dia > 30) { dia = 1; mes = mes + 1; }
                if (mes > 12) { mes = 1; anio = anio + 1; }
                contador = contador + 1;
            }
        } catch (NumberFormatException ex) {
            return new ArrayList<String>();
        }
        return fechas;
    }
}