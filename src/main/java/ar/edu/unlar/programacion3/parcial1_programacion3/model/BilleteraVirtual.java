package ar.edu.unlar.programacion3.parcial1_programacion3.model;

public class BilleteraVirtual implements ProcesadorPago {

        @Override
    public void procesarPago(Cliente cliente, double monto) {
       System.out.println("Cobro exitoso de $" + String.format("%.2f", monto) + " realizado con Billetera Virtual");
    }
}


