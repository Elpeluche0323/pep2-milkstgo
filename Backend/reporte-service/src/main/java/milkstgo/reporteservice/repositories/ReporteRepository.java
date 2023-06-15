package milkstgo.reporteservice.repositories;

import milkstgo.reporteservice.entities.ReporteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReporteRepository extends JpaRepository<ReporteEntity,Integer> {

    @Query(value = "select * from planilla as e where e.codigo_proveedor = :codigo_proveedor and e.quincena =:quincena limit 1",
            nativeQuery = true)
    ReporteEntity buscarReporte(@Param("codigo_proveedor") String codigo_proveedor, @Param("quincena") String quincena);

    @Query(value = "select * from planilla as e where e.codigo_proveedor = :codigo_proveedor", nativeQuery = true)
    List<ReporteEntity> buscarReportePorCodigo(@Param("codigo_proveedor")String codigo_proveedor);
}
