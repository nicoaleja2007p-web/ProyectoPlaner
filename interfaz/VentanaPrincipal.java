package interfaz;

import negocio.GestorUsuario;
import negocio.GestorActividades;
import negocio.GestorAdmin;
import modelo.PerfilPreferencias;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;

public class VentanaPrincipal extends JFrame {

    private CardLayout cardLayout;
    private JPanel panelPrincipal;

    private GestorUsuario gestorUsuario;
    private GestorActividades gestorActividades;
    private GestorAdmin gestorAdmin;

    private FormularioLogin formularioLogin;
    private FormularioPerfil formularioPerfil;
    private FormularioPlanificador formularioPlanificador;
    private VistaItinerario vistaItinerario;
    private VistaReporte vistaReporte;
    private VistaHistorial vistaHistorial;
    private PanelAdmin panelAdmin;

    public VentanaPrincipal() {
        gestorUsuario = new GestorUsuario();
        gestorActividades = new GestorActividades();
        gestorAdmin = new GestorAdmin(gestorUsuario, gestorActividades);

        cargarActividades();
        inicializarVentana();
        inicializarPaneles();
        mostrarPanel("login");
    }

    private void cargarActividades() {
        // Cultural
        gestorActividades.registrarActividad("Tour ciudad historica", 30.0, 3, "cultural");
        gestorActividades.registrarActividad("Visita a museo de arte", 15.0, 2, "cultural");
        gestorActividades.registrarActividad("Visita a mercado artesanal", 5.0, 1, "cultural");
        gestorActividades.registrarActividad("Recorrido arquitectonico", 20.0, 2, "cultural");
        gestorActividades.registrarActividad("Visita a sitio arqueologico", 25.0, 3, "cultural");
        gestorActividades.registrarActividad("Tour de galeria de arte", 10.0, 2, "cultural");
        gestorActividades.registrarActividad("Visita a iglesia historica", 8.0, 1, "cultural");
        gestorActividades.registrarActividad("Espectáculo de danza folklorica", 35.0, 2, "cultural");
        gestorActividades.registrarActividad("Visita a biblioteca historica", 10.0, 2, "cultural");
        gestorActividades.registrarActividad("Tour de murales urbanos", 12.0, 2, "cultural");
        gestorActividades.registrarActividad("Ceremonia cultural indigena", 20.0, 3, "cultural");
        // Aventura
        gestorActividades.registrarActividad("Senderismo en montaña", 20.0, 5, "aventura");
        gestorActividades.registrarActividad("Rafting en río", 45.0, 4, "aventura");
        gestorActividades.registrarActividad("Paseo en bicicleta", 10.0, 2, "aventura");
        gestorActividades.registrarActividad("Escalada en roca", 55.0, 4, "aventura");
        gestorActividades.registrarActividad("Zipline en bosque", 40.0, 2, "aventura");
        gestorActividades.registrarActividad("Kayak en laguna", 30.0, 3, "aventura");
        gestorActividades.registrarActividad("Cabalgata en campo", 35.0, 3, "aventura");
        gestorActividades.registrarActividad("Caminata nocturna", 15.0, 2, "aventura");
        gestorActividades.registrarActividad("Parapente en valle", 65.0, 3, "aventura");
        gestorActividades.registrarActividad("Buceo en arrecife", 70.0, 4, "aventura");
        gestorActividades.registrarActividad("Canopy nocturno", 35.0, 2, "aventura");
        // Gastronomia
        gestorActividades.registrarActividad("Degustación gastronómica", 50.0, 2, "gastronomía");
        gestorActividades.registrarActividad("Clase de cocina local", 60.0, 3, "gastronomía");
        gestorActividades.registrarActividad("Tour de mercado y cocina", 40.0, 3, "gastronomía");
        gestorActividades.registrarActividad("Cena en restaurante tipico", 35.0, 2, "gastronomía");
        gestorActividades.registrarActividad("Cata de vinos locales", 45.0, 2, "gastronomía");
        gestorActividades.registrarActividad("Desayuno tradicional guiado", 20.0, 1, "gastronomía");
        gestorActividades.registrarActividad("Visita a finca caficultora", 30.0, 3, "gastronomía");
        gestorActividades.registrarActividad("Taller de reposteria local", 25.0, 2, "gastronomía");
        gestorActividades.registrarActividad("Festival de comida callejera", 18.0, 2, "gastronomia");
        gestorActividades.registrarActividad("Cata de quesos artesanales", 22.0, 1, "gastronomia");
        gestorActividades.registrarActividad("Brunch en hacienda local", 28.0, 2, "gastronomia");
        // Relajacion
        gestorActividades.registrarActividad("Sesion de yoga al amanecer", 15.0, 1, "relajación");
        gestorActividades.registrarActividad("Visita a aguas termales", 25.0, 3, "relajación");
        gestorActividades.registrarActividad("Masaje tradicional", 50.0, 2, "relajación");
        gestorActividades.registrarActividad("Paseo en bote por lago", 20.0, 2, "relajación");
        gestorActividades.registrarActividad("Picnic en parque natural", 10.0, 2, "relajación");
        gestorActividades.registrarActividad("Meditacion guiada", 12.0, 1, "relajación");
        gestorActividades.registrarActividad("Tarde libre en playa", 5.0, 4, "relajación");
        gestorActividades.registrarActividad("Observación de estrellas", 18.0, 2, "relajación");
        gestorActividades.registrarActividad("Sesion de spa en naturaleza", 45.0, 2, "relajación");
        gestorActividades.registrarActividad("Caminata de bienestar", 8.0, 2, "relajación");
        gestorActividades.registrarActividad("Retiro de meditación", 30.0, 3, "relajación");
    }

    private void inicializarVentana() {
        setTitle("Planificador Inteligente de Viajes");
        setSize(850, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void inicializarPaneles() {
        cardLayout = new CardLayout();
        panelPrincipal = new JPanel(cardLayout);

        formularioLogin = new FormularioLogin(this, gestorUsuario);
        formularioPerfil = new FormularioPerfil(this);
        formularioPlanificador = new FormularioPlanificador(this, gestorActividades);
        vistaItinerario = new VistaItinerario(this);
        vistaReporte = new VistaReporte(this);
        vistaHistorial = new VistaHistorial(this);
        panelAdmin = new PanelAdmin(this, gestorAdmin);

        panelPrincipal.add(formularioLogin, "login");
        panelPrincipal.add(formularioPerfil, "perfil");
        panelPrincipal.add(formularioPlanificador, "planificador");
        panelPrincipal.add(vistaItinerario, "itinerario");
        panelPrincipal.add(vistaReporte, "reporte");
        panelPrincipal.add(vistaHistorial, "historial");
        panelPrincipal.add(panelAdmin, "admin");

        add(panelPrincipal);
    }

    public void mostrarPanel(String nombre) {
        cardLayout.show(panelPrincipal, nombre);
    }

    public GestorUsuario getGestorUsuario() {
        return gestorUsuario;
    }

    public GestorActividades getGestorActividades() {
        return gestorActividades;
    }

    public GestorAdmin getGestorAdmin() {
        return gestorAdmin;
    }

    public PerfilPreferencias getPerfilActual() {
        return gestorUsuario.getPerfilUsuarioActivo();
    }

    public void setPerfilActual(PerfilPreferencias perfil) {
        gestorUsuario.guardarPerfilUsuarioActivo(perfil);
    }

    public VistaItinerario getVistaItinerario() {
        return vistaItinerario;
    }

    public VistaReporte getVistaReporte() {
        return vistaReporte;
    }

    public VistaHistorial getVistaHistorial() {
        return vistaHistorial;
    }

    public FormularioPerfil getFormularioPerfil() {
        return formularioPerfil;
    }

    public PanelAdmin getPanelAdmin() {
        return panelAdmin;
    }

    public static void main(String[] args) {
        VentanaPrincipal ventana = new VentanaPrincipal();
        ventana.setVisible(true);
    }
}