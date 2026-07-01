package modelo;

import java.util.ArrayList;

public class Viaje {

    private int id;
    private String destino;
    private String fechaInicio;
    private String fechaFin;
    private double presupuesto;
    private ArrayList<DiaDeViaje> dias;

    public Viaje(int id, String destino, String fechaInicio, String fechaFin, double presupuesto) {
        this.id = id;
        this.destino = destino;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.presupuesto = presupuesto;
        this.dias = new ArrayList<DiaDeViaje>();
    }

    public void agregarDia(DiaDeViaje dia) {
        dias.add(dia);
    }

    public int calcularDuracion() {
        return dias.size();
    }

    public double getCostoTotal() {
        double total = 0.0;
        for (int i = 0; i < dias.size(); i++) {
            total = total + dias.get(i).getCostoDelDia();
        }
        return total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(double presupuesto) {
        this.presupuesto = presupuesto;
    }

    public ArrayList<DiaDeViaje> getDias() {
        return dias;
    }

    public String toString() {
        return "Viaje a " + destino + " | " + fechaInicio + " - " + fechaFin + " | Presupuesto: $" + presupuesto;
    }
}
