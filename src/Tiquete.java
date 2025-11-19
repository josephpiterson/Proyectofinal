public class Tiquete {
    private Vuelo vuelo;
    private Pasajero pasajero;
    private String clase;
    private String codigoTiquete;
    private int cantidadAsientos;
    private double precioTotal;
    private java.util.List<String> asientos;

    public Tiquete(String codigoTiquete, Vuelo vuelo, Pasajero pasajero, String clase, int cantidadAsientos, double precioTotal) {
        this.codigoTiquete = codigoTiquete;
        this.vuelo = vuelo;
        this.pasajero = pasajero;
        this.clase = clase;
        this.cantidadAsientos = cantidadAsientos;
        this.precioTotal = precioTotal;
        this.asientos = new java.util.ArrayList<>();
    }

    // Constructor de conveniencia a partir de una Reservacion
    public Tiquete(String codigoTiquete, Reservacion reservacion, double precioTotal) {
        this.codigoTiquete = codigoTiquete;
        this.vuelo = reservacion.getVuelo();
        this.pasajero = reservacion.getPasajero();
        this.clase = reservacion.isEsEjecutiva() ? "EJECUTIVA" : "ECONOMICA";
        this.cantidadAsientos = 1; // UI actual emite 1 tiquete por reservación
        this.precioTotal = precioTotal;
        this.asientos = new java.util.ArrayList<>();
    }

    public void setAsientos(java.util.List<String> asientos) { this.asientos = asientos; }
    public java.util.List<String> getAsientos() { return asientos; }

    public String getCodigoTiquete() { return codigoTiquete; }
    public Vuelo getVuelo() { return vuelo; }
    public Pasajero getPasajero() { return pasajero; }
    public String getClase() { return clase; }
    public int getCantidadAsientos() { return cantidadAsientos; }
    public double getPrecioTotal() { return precioTotal; }

    @Override
    public String toString() {
        return "Tiquete: " + codigoTiquete + "\nVuelo: " + vuelo + "\nPasajero: " + pasajero + 
        "\nClase: " + clase + "\nAsientos: " + asientos + "\nCantidad Asientos: " + cantidadAsientos + "\nPrecio Total: " + precioTotal;
    }
}
