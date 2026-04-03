// ZenithApp/MiradorOficial/commons/domains/enums/EstadoStock.java
package ZenithApp.MiradorOficial.commons.domains.enums.estadoStock;

public enum EstadoStock {
    BIEN,      // stockActual > umbralBajo
    BAJO,      // stockActual <= umbralBajo  y  > umbralCritico
    CRITICO,   // stockActual <= umbralCritico y  > 0
    AGOTADO    // stockActual <= 0
}