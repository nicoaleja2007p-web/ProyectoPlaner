package interfaz;

import modelo.PerfilPreferencias;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
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

public class FormularioPerfil extends JPanel {
    private static final double GASTO_DIARIO_DEFECTO = 100.0;

    private VentanaPrincipal ventana;

    private JLabel labelTitulo;
    private JLabel labelEstilos;
    private JCheckBox checkAventura;
    private JCheckBox checkCultural;
    private JCheckBox checkGastronomico;
    private JCheckBox checkRelajacion;
    private JButton botonGuardar;
    private JLabel labelMensaje;

    public FormularioPerfil(VentanaPrincipal ventana) {
        this.ventana = ventana;
        inicializarComponentes();
        configurarLayout();
        configurarEventos();
    }

    private void inicializarComponentes() {
        setBackground(new Color(245, 245, 250));

        labelTitulo = new JLabel("Configura tu perfil de viaje");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        labelTitulo.setForeground(new Color(41, 98, 160));

        labelEstilos = new JLabel("Estilos de viaje (elige uno o varios):");
        labelEstilos.setFont(new Font("Arial", Font.PLAIN, 14));

        checkAventura = new JCheckBox("Aventura");
        checkCultural = new JCheckBox("Cultural");
        checkGastronomico = new JCheckBox("Gastronomico");
        checkRelajacion = new JCheckBox("Relajación");

        checkAventura.setBackground(new Color(245, 245, 250));
        checkCultural.setBackground(new Color(245, 245, 250));
        checkGastronomico.setBackground(new Color(245, 245, 250));
        checkRelajacion.setBackground(new Color(245, 245, 250));

        checkAventura.setFont(new Font("Arial", Font.PLAIN, 13));
        checkCultural.setFont(new Font("Arial", Font.PLAIN, 13));
        checkGastronomico.setFont(new Font("Arial", Font.PLAIN, 13));
        checkRelajacion.setFont(new Font("Arial", Font.PLAIN, 13));

        botonGuardar = new JButton("Guardar y Continuar");
        botonGuardar.setFont(new Font("Arial", Font.BOLD, 13));
        botonGuardar.setBackground(new Color(41, 98, 160));
        botonGuardar.setForeground(Color.WHITE);
        botonGuardar.setFocusPainted(false);

        labelMensaje = new JLabel(" ");
        labelMensaje.setFont(new Font("Arial", Font.ITALIC, 12));
        labelMensaje.setForeground(Color.RED);
    }

    private void configurarLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(labelTitulo, gbc);

        gbc.gridy = 1; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        add(labelEstilos, gbc);

        gbc.gridy = 2; gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.EAST;
        add(checkAventura, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        add(checkCultural, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.EAST;
        add(checkGastronomico, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        add(checkRelajacion, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(labelMensaje, gbc);

        gbc.gridy = 5;
        add(botonGuardar, gbc);
    }

    private void configurarEventos() {
        botonGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardarPerfil();
            }
        });
    }

    private void guardarPerfil() {
        ArrayList<String> estilosSeleccionados = new ArrayList<String>();

        if (checkAventura.isSelected()) { estilosSeleccionados.add("aventura"); }
        if (checkCultural.isSelected()) { estilosSeleccionados.add("cultural"); }
        if (checkGastronomico.isSelected()) { estilosSeleccionados.add("gastronomia"); }
        if (checkRelajacion.isSelected()) { estilosSeleccionados.add("relajación"); }

        if (estilosSeleccionados.size() == 0) {
            labelMensaje.setText("Error: Selecciona al menos un estilo de viaje.");
            return;
        }

        PerfilPreferencias perfil = new PerfilPreferencias(GASTO_DIARIO_DEFECTO);

        for (int i = 0; i < estilosSeleccionados.size(); i++) {
            String estilo = estilosSeleccionados.get(i);
            perfil.agregarEstilo(estilo);
            perfil.agregarCategoria(estilo, 90.0 - (i * 10.0));
        }

        ventana.setPerfilActual(perfil);

        labelMensaje.setText(" ");
        JOptionPane.showMessageDialog(ventana, "Perfil guardado correctamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
        ventana.mostrarPanel("planificador");
    }

    public void precargarPerfil() {
        PerfilPreferencias perfil = ventana.getPerfilActual();
        ArrayList<String> estilos = perfil.getEstilosViaje();

        checkAventura.setSelected(estilos.contains("aventura"));
        checkCultural.setSelected(estilos.contains("cultural"));
        checkGastronomico.setSelected(estilos.contains("gastronomia"));
        checkRelajacion.setSelected(estilos.contains("relajación"));
    }
}