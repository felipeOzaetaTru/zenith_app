package ZenithApp.MiradorOficial.repository.sale;

import ZenithApp.MiradorOficial.commons.domains.entity.sale.SaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISaleRepository extends JpaRepository<SaleEntity, Long> {
}
