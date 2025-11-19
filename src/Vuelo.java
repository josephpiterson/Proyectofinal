import java.util.HashMap;
import java.util.HashSet;

public class Vuelo {
    private final String codigo;
    private final String origen;
    private final String destino;
    private Avion avion;
    private double precioEconomica;
    private double precioEjecutiva;
    private String visaId; // ID de la visa si es requerida
    
    // Países que requieren visa (mapa con tipo de visa)
    private static final HashMap<String, String> COUNTRIES_REQUIRING_VISA = new HashMap<>();
    // Países que NO requieren visa
    private static final HashSet<String> COUNTRIES_NO_VISA = new HashSet<>();

    static {
        // Países que requieren visa con su tipo
        COUNTRIES_REQUIRING_VISA.put("Estados Unidos", "B1/B2");
        COUNTRIES_REQUIRING_VISA.put("China", "L");
        COUNTRIES_REQUIRING_VISA.put("Rusia", "TR");
        COUNTRIES_REQUIRING_VISA.put("India", "e-Visa");
        COUNTRIES_REQUIRING_VISA.put("Reino Unido", "Standard Visitor");
        COUNTRIES_REQUIRING_VISA.put("Francia", "Schengen");
        COUNTRIES_REQUIRING_VISA.put("Canada", "eTA");
        COUNTRIES_REQUIRING_VISA.put("Australia", "ETA");
        
        // Países que NO requieren visa
        COUNTRIES_NO_VISA.add("Mexico");
        COUNTRIES_NO_VISA.add("Panama");
        COUNTRIES_NO_VISA.add("Argentina");
        COUNTRIES_NO_VISA.add("Colombia");
        COUNTRIES_NO_VISA.add("Peru");
        COUNTRIES_NO_VISA.add("Brasil");
        COUNTRIES_NO_VISA.add("Chile");
        COUNTRIES_NO_VISA.add("Uruguay");
    }

    public Vuelo(String codigo, String origen, String destino, double precioEjecutiva, double precioEconomica, Avion avion) {
        this.codigo = codigo;
        this.origen = origen;
        this.destino = destino;
        this.avion = avion;
        this.precioEconomica = precioEconomica;
        this.precioEjecutiva = precioEjecutiva;
    }

    // Getters
    public String getCodigo() { 
        return codigo; 
    }
    
    public String getOrigen() { 
        return origen; 
    }
    
    public String getDestino() { 
        return destino; 
    }
    
    public Avion getAvion() { 
        return avion; 
    }
    
    public double getPrecioEconomica() { 
        return precioEconomica; 
    }
    
    public double getPrecioEjecutiva() { 
        return precioEjecutiva; 
    }
    
    public String getVisaId() { 
        return visaId; 
    }

    // Setters
    public void setAvion(Avion avion) {
        this.avion = avion;
    }
    
    public void setPrecioEconomica(double precioEconomica) { 
        this.precioEconomica = precioEconomica; 
    }
    
    public void setPrecioEjecutiva(double precioEjecutiva) { 
        this.precioEjecutiva = precioEjecutiva; 
    }
    
    public void setPrecio(String clase, double precio) {
        if ("EJECUTIVA".equalsIgnoreCase(clase)) {
            setPrecioEjecutiva(precio);
        } else {
            setPrecioEconomica(precio);
        }
    }
    
    public void setVisaId(String visaId) { 
        this.visaId = visaId; 
    }

    // Métodos de negocio
    public boolean requiereVisa() {
        if (destino == null) return false;
        return COUNTRIES_REQUIRING_VISA.containsKey(destino.trim());
    }

    public String getTipoVisaRequerida() {
        if (destino == null) return null;
        return COUNTRIES_REQUIRING_VISA.get(destino.trim());
    }

    // Métodos estáticos de conveniencia para consultar requisitos por país
    public static boolean requiresVisaFor(String destino) {
        if (destino == null) return false;
        return COUNTRIES_REQUIRING_VISA.containsKey(destino.trim());
    }

    public static boolean noVisaFor(String destino) {
        if (destino == null) return false;
        return COUNTRIES_NO_VISA.contains(destino.trim());
    }

    public static String getTipoVisaFor(String destino) {
        if (destino == null) return null;
        return COUNTRIES_REQUIRING_VISA.get(destino.trim());
    }

    public boolean destinoSinVisa() {
        if (destino == null) return false;
        return COUNTRIES_NO_VISA.contains(destino.trim());
    }

    public boolean hayDisponibilidad(String clase) {
        return avion.hayDisponibilidad(clase);
    }

    public boolean hayDisponibilidad(String clase, int cantidad) {
        return avion.hayDisponibilidad(clase, cantidad);
    }

    public boolean reservar(String clase, int cantidad) {
        return avion.reservarAsientos(clase, cantidad);
    }

    public double getPrecio(String clase) {
        if ("EJECUTIVA".equalsIgnoreCase(clase)) {
            return precioEjecutiva;
        }
        return precioEconomica;
    }

    @Override
    public String toString() {
        return codigo + " - " + origen + " -> " + destino + " (" + avion.getMatricula() + ")";
    }
}
