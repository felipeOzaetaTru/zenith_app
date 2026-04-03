package ZenithApp.MiradorOficial.repository.product;

import ZenithApp.MiradorOficial.commons.domains.entity.product.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface IProductRepository extends JpaRepository<ProductEntity, Long> {
    @Query("SELECT u FROM ProductEntity u WHERE LOWER(TRIM(u.name)) = LOWER(TRIM(:name))")
    Optional<ProductEntity> readProductForName(@Param("name") String name);

    List<ProductEntity> findByCategory_Id(Long categoryId);
    // En iProductRepository
    @Modifying
    @Transactional
    @Query(value = "UPDATE producto_mirador SET stock = stock - :quantity WHERE id = :productId AND stock >= :quantity", nativeQuery = true)
    int decrementStock(@Param("productId") Long productId, @Param("quantity") int quantity);

}
