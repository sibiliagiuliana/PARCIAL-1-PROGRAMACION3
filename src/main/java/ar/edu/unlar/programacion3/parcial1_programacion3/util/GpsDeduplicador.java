package ar.edu.unlar.programacion3.parcial1_programacion3.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GpsDeduplicador {

 
    public static List<String> deduplicar(List<String> reportes) {

       
        Set<String> vistos = new HashSet<String>();
        List<String> resultado = new ArrayList<String>();

        for (int i = 0; i < reportes.size(); i++) {
            String reporte = reportes.get(i);

      
            if (vistos.add(reporte)) {
                resultado.add(reporte);
            }
        }

        return resultado;
    }
}