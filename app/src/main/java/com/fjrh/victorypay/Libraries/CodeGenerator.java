package com.fjrh.victorypay.Libraries;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class CodeGenerator {

    public static String getNewCode(char letra) {
        // Obtener la fecha actual en formato "MMddyy"
        String fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("MMddyy"));

        // Generar una cadena aleatoria de 9 dígitos
        Random random = new Random();
        String cadenaAleatoria = String.format("%09d", random.nextInt(1000000000));

        // Combinar los elementos en el código final
        String codigo = letra + "-" + fecha + "-" + cadenaAleatoria;

        return codigo;
    }

}
