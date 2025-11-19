// Clase Pasajero simplificada para principiantes.
public class Pasajero {
    private String nombre;
    private String apellidos;
    private String documento;
    private String email;
    private String nacionalidad;

    public Pasajero(String nombre, String apellidos, String documento, String nacionalidad, String email) {
        this.nombre = nombre == null ? "" : nombre;
        this.apellidos = apellidos == null ? "" : apellidos;
        this.documento = documento == null ? "" : documento;
        this.nacionalidad = nacionalidad == null ? "" : nacionalidad;
        this.email = email == null ? "" : email;
    }

    public String getNombre() { return nombre; }
    public String getApellidos() { return apellidos; }
    public String getDocumento() { return documento; }
    public String getEmail() { return email; }
    public String getNacionalidad() { return nacionalidad; }

    @Override
    public String toString() {
        return nombre + " " + apellidos + " (" + documento + ")";
    }
}
