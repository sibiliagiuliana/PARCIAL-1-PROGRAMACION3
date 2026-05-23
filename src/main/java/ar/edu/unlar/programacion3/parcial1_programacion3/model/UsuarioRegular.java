package ar.edu.unlar.programacion3.parcial1_programacion3.model;

public class UsuarioRegular extends Cliente {

     public UsuarioRegular(String idUsuario, String nombreCompleto) {
        super(idUsuario, nombreCompleto);
    }

    @Override
    public double aplicarDescuento(double monto) {
        return monto;
    }
}


