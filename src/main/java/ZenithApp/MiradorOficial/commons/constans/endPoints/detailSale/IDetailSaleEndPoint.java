package ZenithApp.MiradorOficial.commons.constans.endPoints.detailSale;

public interface IDetailSaleEndPoint {
    String BASE_URL = "/detalle";
    String CREATE_DETAIL = "/crear-detalle";
    String READ_DETAIL= "/leer-detalle/{id}";
    String READ_ALL_DETAIL = "leer-detalles";
    String UPDATE_DETAIL = "actualizar-detalle";
    String DELETE_DETAIL = "eliminar-detalle/{id}";
}
