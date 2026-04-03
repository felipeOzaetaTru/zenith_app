package ZenithApp.MiradorOficial.commons.constans.endPoints.category;

public interface ICategoryEndPoint {
    String BASE_URL = "/categoria";
    String CREATE_CATEGORY = "/crear-categoria";
    String READ_CATEGORY = "/leer-categoria/{id}";
    String READ_ALL_CATEGORY = "leer-categorias";
    String UPDATE_CATEGORY = "actualizar-categoria";
    String DELETE_CATEGORY = "eliminar-categoria/{id}";
}
