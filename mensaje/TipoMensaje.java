package parte2.mensaje;

public enum TipoMensaje {
    CONEXION,
    CONFIRMACION_CONEXION,
    
    LISTA_USUARIOS,
    CONFIRMACION_LISTA_USUARIOS,
    
    PEDIR_FICHERO,
    EMITIR_FICHERO,
    PREPARADO_CLIENTE_SERVIDOR,
    PREPARADO_SERVIDOR_CLIENTE,
    
    CERRAR_CONEXION,
    CONFIRMACION_CERRAR_CONEXION
}
