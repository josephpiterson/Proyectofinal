public class Reservacion {
    public static class Resultado {
        public boolean exito;
        public String mensaje;
        public Tiquete tiquete;
        public Factura factura;
    }

    // Contadores simples para generar códigos legibles (no UUIDs)
    private static int nextTiquete = 1;
    private static int nextFactura = 1;

    public static Resultado reservar(Vuelo vuelo, Pasajero pasajero, String clase, int cantidad) {
        Resultado r = new Resultado();
        if (cantidad <= 0) {
            r.exito = false;
            r.mensaje = "La cantidad debe ser mayor a 0.";
            return r;
        }
        if (!vuelo.getAvion().hayDisponibilidad(clase, cantidad)) {
            r.exito = false;
            r.mensaje = "No hay disponibilidad.";
            return r;
        }
        // asigna asientos automáticamente (la clase Avion hace la tarea)
        java.util.List<String> asignadas = vuelo.getAvion().reservarAsientosAuto(clase, cantidad);
        if (asignadas == null) {
            r.exito = false;
            r.mensaje = "No se pudieron asignar asientos.";
            return r;
        }
        double precioUnit = vuelo.getPrecio(clase);
        double precioTotal = precioUnit * cantidad;
        String codigoT = "T-" + (nextTiquete++);
        Tiquete t = new Tiquete(codigoT, vuelo, pasajero, clase, cantidad, precioTotal);
        t.setAsientos(asignadas);
        String codigoF = "F-" + (nextFactura++);
        Factura f = new Factura(codigoF, t);
        r.exito = true;
        r.mensaje = "Reservación creada";
        r.tiquete = t;
        r.factura = f;
        return r;
    }

    // Versión simple que ignora la petición de asientos específicos y usa asignación automática.
    public static Resultado reservarConAsientos(Vuelo vuelo, Pasajero pasajero, String clase, java.util.List<String> seats) {
        return reservar(vuelo, pasajero, clase, seats == null ? 0 : seats.size());
    }

    // Instancia simple de una reservación (no usada por la UI simplificada)
    private String codigo;
    private Vuelo vuelo;
    private Pasajero pasajero;
    private boolean esEjecutiva;

    public Reservacion() {}

    public Reservacion(String codigo, Vuelo vuelo, Pasajero pasajero, boolean esEjecutiva) {
        this.codigo = codigo;
        this.vuelo = vuelo;
        this.pasajero = pasajero;
        this.esEjecutiva = esEjecutiva;
    }

    public String getCodigo() { return codigo; }
    public Vuelo getVuelo() { return vuelo; }
    public Pasajero getPasajero() { return pasajero; }
    public boolean isEsEjecutiva() { return esEjecutiva; }

    @Override
    public String toString() {
        if (vuelo == null || pasajero == null) return "Reservacion vacía";
        return "Reservacion[" + codigo + "] - " + vuelo.getCodigo() + " - " + pasajero.getNombre();
    }
}
