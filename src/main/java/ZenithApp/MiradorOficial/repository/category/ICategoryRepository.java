package ZenithApp.MiradorOficial.repository.category;

import ZenithApp.MiradorOficial.commons.domains.entity.category.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ICategoryRepository extends JpaRepository<CategoryEntity, Long> {
    @Query("SELECT u FROM CategoryEntity u WHERE LOWER(TRIM(u.name)) = LOWER(TRIM(:name))")
    Optional<CategoryEntity> readProductForName(@Param("name") String name);

}
