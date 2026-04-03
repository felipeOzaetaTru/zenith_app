package ZenithApp.MiradorOficial.commons.domains.enums.tipoAlerta;

public enum TipoAlerta {
    AGOTADO,   // stockActual <= 0
    CRITICO,   // stockActual <= stockCritico
    BAJO       // stockActual <= stockMinimo
}
