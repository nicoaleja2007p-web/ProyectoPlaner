package negocio;

import modelo.Usuario;
import modelo.HistorialViaje;
import modelo.PerfilPreferencias;
import modelo.Viaje;
import excepciones.UsuarioInvalidoException;
import excepciones.SesionException;
import java.util.ArrayList;

public class GestorUsuario {

    private ArrayList<Usuario> usuarios;
    private Usuario usuarioActivo;
    private ArrayList<HistorialViaje> historiales;
    private ArrayList<PerfilPreferencias> perfiles;
    private int intentosFallidos;

    public GestorUsuario() {
        this.usuarios = new ArrayList<Usuario>();
        this.historiales = new ArrayList<HistorialViaje>();
        this.perfiles = new ArrayList<PerfilPreferencias>();
        this.usuarioActivo = null;
        this.intentosFallidos = 0;
    }

    public boolean login(String email, String contrasena) {
        if (intentosFallidos >= 3) {
            System.out.println("Cuenta bloqueada. Demasiados intentos fallidos.");
            return false;
        }

        if (email == null || email.equals("") || contrasena == null || contrasena.equals("")) {
            System.out.println("Error: Email y contraseña son obligatorios.");
            return false;
        }

        for (int i = 0; i < usuarios.size(); i++) {
            Usuario usuario = usuarios.get(i);
            if (usuario.iniciarSesion(email, contrasena)) {
                usuarioActivo = usuario;
                intentosFallidos = 0;
                System.out.println("Sesion iniciada: " + usuario.getNombre());
                return true;
            }
        }

        intentosFallidos = intentosFallidos + 1;
        System.out.println("Credenciales incorrectas. Intento " + intentosFallidos + " de 3.");
        return false;
    }

    public void logout() {
        if (usuarioActivo != null) {
            usuarioActivo.cerrarSesion();
            usuarioActivo = null;
            intentosFallidos = 0;
        }
    }

    public boolean registrarUsuario(String nombre, String email, String contrasena) throws UsuarioInvalidoException {
        return registrarUsuario(nombre, email, contrasena, "", "");
    }

    public boolean registrarUsuario(String nombre, String email, String contrasena,
                                    String preguntaSeguridad, String respuestaSeguridad) throws UsuarioInvalidoException {

        if (nombre == null || nombre.trim().equals("")) {
            throw new UsuarioInvalidoException("El nombre es obligatorio.");
        }
        if (nombre.trim().length() < 3) {
            throw new UsuarioInvalidoException("El nombre debe tener al menos 3 caracteres.");
        }
        if (email == null || email.trim().equals("")) {
            throw new UsuarioInvalidoException("El email es obligatorio.");
        }
        if (!esEmailValido(email.trim())) {
            throw new UsuarioInvalidoException("El email no tiene un formato valido (ejemplo: nombre@correo.com).");
        }
        if (contrasena == null || contrasena.equals("")) {
            throw new UsuarioInvalidoException("La contraseña es obligatoria.");
        }
        if (contrasena.length() < 6) {
            throw new UsuarioInvalidoException("La contraseña debe tener al menos 6 caracteres.");
        }

        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getEmail().equalsIgnoreCase(email.trim())) {
                throw new UsuarioInvalidoException("Ya existe un usuario registrado con ese email.");
            }
        }

        int nuevoId = usuarios.size() + 1;
        Usuario nuevoUsuario = new Usuario(nuevoId, nombre.trim(), email.trim(), contrasena);

        if (preguntaSeguridad != null && !preguntaSeguridad.trim().equals("")
                && respuestaSeguridad != null && !respuestaSeguridad.trim().equals("")) {
            nuevoUsuario.setPreguntaSeguridad(preguntaSeguridad.trim());
            nuevoUsuario.setRespuestaSeguridad(respuestaSeguridad.trim());
        }

        usuarios.add(nuevoUsuario);
        historiales.add(new HistorialViaje());
        perfiles.add(new PerfilPreferencias(100.0));
        System.out.println("Usuario registrado exitosamente.");
        return true;
    }

    public boolean esEmailValido(String email) {
        if (email == null) {
            return false;
        }
        String patron = "^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$";
        return email.matches(patron);
    }

    public Usuario buscarPorEmail(String email) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getEmail().equalsIgnoreCase(email)) {
                return usuarios.get(i);
            }
        }
        return null;
    }


    public String recuperarContrasena(String email, String respuesta) throws SesionException {
        Usuario usuario = buscarPorEmail(email);
        if (usuario == null) {
            throw new SesionException("No existe un usuario registrado con ese email.");
        }
        if (!usuario.verificarRespuestaSeguridad(respuesta)) {
            throw new SesionException("La respuesta de seguridad es incorrecta.");
        }
        return usuario.getContrasena();
    }

    public void agregarViajeAlHistorial(Viaje viaje) {
        int indice = usuarios.indexOf(usuarioActivo);
        if (indice >= 0 && indice < historiales.size()) {
            historiales.get(indice).agregarViaje(viaje);
        }
    }

    public HistorialViaje obtenerHistorial() {
        int indice = usuarios.indexOf(usuarioActivo);
        if (indice >= 0 && indice < historiales.size()) {
            return historiales.get(indice);
        }
        return new HistorialViaje();
    }

    public PerfilPreferencias getPerfilUsuarioActivo() {
        int indice = usuarios.indexOf(usuarioActivo);
        if (indice >= 0 && indice < perfiles.size()) {
            return perfiles.get(indice);
        }
        return new PerfilPreferencias(100.0);
    }

    public void guardarPerfilUsuarioActivo(PerfilPreferencias perfil) {
        int indice = usuarios.indexOf(usuarioActivo);
        if (indice >= 0 && indice < perfiles.size()) {
            perfiles.set(indice, perfil);
        }
    }

    public Usuario getUsuarioActivo() {
        return usuarioActivo;
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public boolean haySesionActiva() {
        if (usuarioActivo != null) {
            return true;
        }
        return false;
    }

    public int getIntentosFallidos() {
        return intentosFallidos;
    }

    public void reiniciarIntentos() {
        this.intentosFallidos = 0;
    }
}