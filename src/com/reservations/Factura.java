package com.reservations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Factura {
    private String codigoFactura;
    private Tiquete tiquete;
    private LocalDateTime fecha;

    public Factura(String codigoFactura, Tiquete tiquete) {
        this.codigoFactura = codigoFactura;
        this.tiquete = tiquete;
        this.fecha = LocalDateTime.now();
    }

    // Constructor de conveniencia que genera un código automáticamente
    public Factura(Tiquete tiquete) {
        this.codigoFactura = "F-" + java.util.UUID.randomUUID().toString().substring(0,8).toUpperCase();
        this.tiquete = tiquete;
        this.fecha = LocalDateTime.now();
    }

    public String generarTexto() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        StringBuilder sb = new StringBuilder();
        sb.append("Factura: ").append(codigoFactura).append("\n");
        sb.append("Fecha: ").append(fecha.format(f)).append("\n\n");
        sb.append("Pasajero: ").append(tiquete.getPasajero()).append("\n");
        sb.append("Email: ").append(tiquete.getPasajero().getEmail()).append("\n");
        sb.append("Vuelo: ").append(tiquete.getVuelo()).append("\n");
        sb.append("Clase: ").append(tiquete.getClase()).append("\n");
        sb.append("Cantidad de asientos: ").append(tiquete.getCantidadAsientos()).append("\n");
        sb.append("Precio total: ").append(tiquete.getPrecioTotal()).append("\n");
        return sb.toString();
    }
}
