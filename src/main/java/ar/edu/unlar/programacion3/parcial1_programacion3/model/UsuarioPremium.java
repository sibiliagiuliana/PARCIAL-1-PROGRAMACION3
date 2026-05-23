package ar.edu.unlar.programacion3.parcial1_programacion3.model;

public class UsuarioPremium extends Cliente {

       private double porcentajeDescuento;

    public UsuarioPremium(String idUsuario, String nombreCompleto, double descuento) {
        super(idUsuario, nombreCompleto);
        this.porcentajeDescuento = descuento;
    }

    @Override
    public double aplicarDescuento(double monto) {
        return monto - (monto * porcentajeDescuento);
    }
}

