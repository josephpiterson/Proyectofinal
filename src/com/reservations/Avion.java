package com.reservations;

import java.util.ArrayList;
import java.util.List;

// Versión muy simple de Avion, pensada para principiantes.
public class Avion {
    private String matricula;
    private String modelo;
    private int capacidadEjecutiva;
    private int capacidadEconomica;
    private int ocupadosEjecutiva;
    private int ocupadosEconomica;

    public Avion(String modelo) {
        // valores por defecto simples
        this(modelo, 10, 20);
    }

    public Avion(String modelo, int capacidadEjecutiva, int capacidadEconomica) {
        this.modelo = modelo;
        this.matricula = "AV-" + System.currentTimeMillis() % 10000; // id simple
        this.capacidadEjecutiva = capacidadEjecutiva;
        this.capacidadEconomica = capacidadEconomica;
        this.ocupadosEjecutiva = 0;
        this.ocupadosEconomica = 0;
    }

    public String getModelo() { return modelo; }

    // Devuelve una lista simple de asientos disponibles como E1, E2... o C1, C2...
    public List<String> getAvailableSeats(String clase) {
        List<String> res = new ArrayList<>();
        if ("EJECUTIVA".equalsIgnoreCase(clase)) {
            for (int i = ocupadosEjecutiva + 1; i <= capacidadEjecutiva; i++) {
                res.add("E" + i);
            }
        } else {
            for (int i = ocupadosEconomica + 1; i <= capacidadEconomica; i++) {
                res.add("C" + i);
            }
        }
        return res;
    }

    // Reserva asientos automáticamente y devuelve los identificadores asignados.
    public List<String> reservarAsientosAuto(String clase, int cantidad) {
        if (!hayDisponibilidad(clase, cantidad)) return null;
        List<String> asignadas = new ArrayList<>();
        if ("EJECUTIVA".equalsIgnoreCase(clase)) {
            for (int i = 0; i < cantidad; i++) {
                ocupadosEjecutiva++;
                asignadas.add("E" + ocupadosEjecutiva);
            }
        } else {
            for (int i = 0; i < cantidad; i++) {
                ocupadosEconomica++;
                asignadas.add("C" + ocupadosEconomica);
            }
        }
        return asignadas;
    }

    public boolean hayDisponibilidad(String clase) {
        return hayDisponibilidad(clase, 1);
    }

    public boolean hayDisponibilidad(String clase, int cantidad) {
        if ("EJECUTIVA".equalsIgnoreCase(clase)) {
            return ocupadosEjecutiva + cantidad <= capacidadEjecutiva;
        } else {
            return ocupadosEconomica + cantidad <= capacidadEconomica;
        }
    }

    public boolean reservarAsientos(String clase, int cantidad) {
        if (!hayDisponibilidad(clase, cantidad)) return false;
        if ("EJECUTIVA".equalsIgnoreCase(clase)) {
            ocupadosEjecutiva += cantidad;
        } else {
            ocupadosEconomica += cantidad;
        }
        return true;
    }

    public int getDisponibles(String clase) {
        if ("EJECUTIVA".equalsIgnoreCase(clase)) {
            return capacidadEjecutiva - ocupadosEjecutiva;
        }
        return capacidadEconomica - ocupadosEconomica;
    }

    public String getMatricula() { return matricula; }

    @Override
    public String toString() {
        return "Avion " + matricula + " (" + modelo + ")";
    }
}
