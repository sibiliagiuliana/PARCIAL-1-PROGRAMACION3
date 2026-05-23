package ar.edu.unlar.programacion3.parcial1_programacion3.service;

import ar.edu.unlar.programacion3.parcial1_programacion3.model.BilleteraVirtual;
import ar.edu.unlar.programacion3.parcial1_programacion3.model.TarjetaDeCredito;
import ar.edu.unlar.programacion3.parcial1_programacion3.model.ProcesadorPago;

public class FabricaProcesadorPago {

       public static ProcesadorPago getProcesador(String medioPago) {
        if (medioPago == null) {
            throw new IllegalArgumentException("El medio de pago no puede ser nulo.");
        }

   
        switch (medioPago.toUpperCase().trim()) {
            case "TARJETA":
                return new TarjetaDeCredito();
            
            case "BILLETERA":
                return new BilleteraVirtual();
                
            default:

                throw new RuntimeException("Medio de pago no soportado por EcoRide: " + medioPago);
        }
    }
}


