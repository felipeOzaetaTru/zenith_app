package ZenithApp.MiradorOficial.commons.constans.endPoints.user;

public interface IUserEndPoint {
    String USER_BASE_URL = "/usuario";
    String USER_CRATE = "/crear-usuario";
    String USER_SERVICE = "/servicio-usuario";
    String USER_READ = "/leer-usuario/{userId}";
    String USERS_READ = "/leer-todos-usuario";
    String USER_UPDATE = "/actualizar-usuario";
    String USER_DELETE = "/eliminar-usuario{userId}";
}
