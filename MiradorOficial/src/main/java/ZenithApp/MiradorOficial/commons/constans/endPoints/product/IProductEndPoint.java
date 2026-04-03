package ZenithApp.MiradorOficial.commons.constans.endPoints.product;

public interface IProductEndPoint {
    String BASE_URL = "/producto";
    String CREATE_PRODUCT = "/crear-producto";
    String READ_PRODUCT = "/leer-producto/{id}";
    String READ_PRODUCT_FOR_CATEGORY = "leer-producto-category/{id}";
    String READ_PRODUCTS = "leer-productos";
    String UPDATE_PRODUCT = "actualizar-producto";
    String DELETE_PRODUCT = "eliminar-producto/{id}";
}
