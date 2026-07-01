package negocio;

import modelo.Actividad;
import modelo.ReporteViaje;
import modelo.Viaje;
import excepciones.PresupuestoExcedidoException;
import java.util.ArrayList;

public class GestorReporte {

    private Viaje viaje;

    public GestorReporte(Viaje viaje) {
        this.viaje = viaje;
    }

    public ReporteViaje generarReporte() {
        ReporteViaje reporte = new ReporteViaje(viaje.getDias());
        return reporte;
    }

    public String generarDesglose() {
        ReporteViaje reporte = generarReporte();
        return reporte.generarDesglose();
    }

    public ArrayList<String> detectarExcesos(double gastoDiarioMax) throws PresupuestoExcedidoException {
        if (gastoDiarioMax <= 0) {
            throw new PresupuestoExcedidoException("El limite de gasto diario debe ser mayor a cero.");
        }
        ReporteViaje reporte = generarReporte();
        return reporte.detectarExcesos(gastoDiarioMax);
    }

    public ArrayList<String> sugerirAlternativas(double gastoDiarioMax, ArrayList<Actividad> todasActividades,
                                                 ArrayList<String> estilosPermitidos) {
        if (!viajeExcedido()) {
            return new ArrayList<String>();
        }
        ReporteViaje reporte = generarReporte();
        return reporte.sugerirAlternativas(gastoDiarioMax, todasActividades, estilosPermitidos);
    }

    public boolean viajeExcedido() {
        ReporteViaje reporte = generarReporte();
        if (reporte.getCostoTotal() > viaje.getPresupuesto()) {
            return true;
        }
        return false;
    }

    public String resumenGeneral() {
        ReporteViaje reporte = generarReporte();
        String resumen = " Resumen General\n";
        resumen = resumen + "Destino: " + viaje.getDestino() + "\n";
        resumen = resumen + "Duración: " + viaje.calcularDuracion() + " dias\n";
        resumen = resumen + "Presupuesto: $" + viaje.getPresupuesto() + "\n";
        resumen = resumen + "Costo Total: $" + reporte.getCostoTotal() + "\n";

        if (viajeExcedido()) {
            resumen = resumen + "ALERTA: El costo supera el presupuesto disponible.\n";
        } else {
            resumen = resumen + "El viaje esta dentro del presupuesto.\n";
        }

        return resumen;
    }

    public Viaje getViaje() {
        return viaje;
    }

    public void setViaje(Viaje viaje) {
        this.viaje = viaje;
    }
}