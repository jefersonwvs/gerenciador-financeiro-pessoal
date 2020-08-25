package modelo.entidades.utilitarios;

import java.sql.Timestamp;
import java.util.Date;

public class Utilitaria {

    public static java.sql.Timestamp transformaData(Date data) {
        Timestamp dataTransformada = new Timestamp(data.getTime());
        return dataTransformada;
    }
    
    
} 
