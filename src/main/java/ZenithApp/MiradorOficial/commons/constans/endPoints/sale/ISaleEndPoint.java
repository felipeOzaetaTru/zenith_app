package ZenithApp.MiradorOficial.commons.constans.endPoints.sale;

public interface ISaleEndPoint {
    String BASE_URL = "/venta";
    String CREATE_SALE = "/crear-venta";
    String READ_SALE = "/leer-venta/{id}";
    String READ_ALL_SALE = "leer-ventas";
    String UPDATE_SALE = "actualizar-venta";
    String DELETE_SALE = "eliminar-venta/{id}";
}
