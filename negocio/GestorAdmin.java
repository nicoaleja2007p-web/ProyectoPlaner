package negocio;

import modelo.Admin;
import modelo.Usuario;
import excepciones.SesionException;
import java.util.ArrayList;

public class GestorAdmin {

    private Admin admin;
    private Admin adminActivo;
    private GestorUsuario gestorUsuario;
    private GestorActividades gestorActividades;

    public GestorAdmin(GestorUsuario gestorUsuario, GestorActividades gestorActividades) {
        this.admin = new Admin("adminviaje", "adminviaje123");
        this.adminActivo = null;
        this.gestorUsuario = gestorUsuario;
        this.gestorActividades = gestorActividades;
    }

    public boolean login(String usuario, String contrasena) throws SesionException {
        if (usuario == null || usuario.equals("") || contrasena == null || contrasena.equals("")) {
            throw new SesionException("Usuario y contraseña son obligatorios.");
        }
        if (admin.iniciarSesion(usuario, contrasena)) {
            adminActivo = admin;
            return true;
        }
        throw new SesionException("Usuario o contraseña de administrador incorrectos.");
    }

    public void logout() {
        adminActivo = null;
    }

    public boolean haySesionActiva() {
        return adminActivo != null;
    }

    public ArrayList<Usuario> listarUsuarios() {
        return gestorUsuario.getUsuarios();
    }

    public int contarUsuarios() {
        return gestorUsuario.getUsuarios().size();
    }

    public int contarActividades() {
        return gestorActividades.getActividades().size();
    }

    public boolean registrarActividad(String nombre, double costo, int duracion, String categoria) {
        return gestorActividades.registrarActividad(nombre, costo, duracion, categoria);
    }

    public boolean eliminarActividad(int id) {
        return gestorActividades.eliminarActividad(id);
    }

    public boolean eliminarUsuarioPorEmail(String email) {
        ArrayList<Usuario> usuarios = gestorUsuario.getUsuarios();
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getEmail().equalsIgnoreCase(email)) {
                usuarios.remove(i);
                return true;
            }
        }
        return false;
    }

    public int contarViajesTotales() {
        ArrayList<Usuario> usuarios = gestorUsuario.getUsuarios();
        int total = 0;
        for (int i = 0; i < usuarios.size(); i++) {
            total = total + usuarios.get(i).getHistorial().obtenerViajes().size();
        }
        return total;
    }

    public double calcularGastoTotalSistema() {
        ArrayList<Usuario> usuarios = gestorUsuario.getUsuarios();
        double total = 0.0;
        for (int i = 0; i < usuarios.size(); i++) {
            total = total + usuarios.get(i).getHistorial().calcularGastoTotal();
        }
        return total;
    }

    public String categoriaMasUsada() {
        ArrayList<Usuario> usuarios = gestorUsuario.getUsuarios();
        ArrayList<String> nombresCategorias = new ArrayList<String>();
        ArrayList<Integer> conteos = new ArrayList<Integer>();

        for (int u = 0; u < usuarios.size(); u++) {
            ArrayList<modelo.Viaje> viajes = usuarios.get(u).getHistorial().obtenerViajes();
            for (int v = 0; v < viajes.size(); v++) {
                ArrayList<modelo.DiaDeViaje> dias = viajes.get(v).getDias();
                for (int d = 0; d < dias.size(); d++) {
                    ArrayList<modelo.Actividad> actividades = dias.get(d).getActividades();
                    for (int a = 0; a < actividades.size(); a++) {
                        String categoria = actividades.get(a).getCategoria();
                        int indice = nombresCategorias.indexOf(categoria);
                        if (indice == -1) {
                            nombresCategorias.add(categoria);
                            conteos.add(1);
                        } else {
                            conteos.set(indice, conteos.get(indice) + 1);
                        }
                    }
                }
            }
        }

        if (nombresCategorias.size() == 0) {
            return "Sin datos aún";
        }

        int indiceMax = 0;
        for (int i = 1; i < conteos.size(); i++) {
            if (conteos.get(i) > conteos.get(indiceMax)) {
                indiceMax = i;
            }
        }
        return nombresCategorias.get(indiceMax) + " (" + conteos.get(indiceMax) + " veces)";
    }

    public GestorUsuario getGestorUsuario() {
        return gestorUsuario;
    }

    public GestorActividades getGestorActividades() {
        return gestorActividades;
    }
}