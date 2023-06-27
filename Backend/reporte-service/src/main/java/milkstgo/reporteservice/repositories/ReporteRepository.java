package milkstgo.reporteservice.repositories;

import milkstgo.reporteservice.entities.ReporteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReporteRepository extends JpaRepository<ReporteEntity,Integer> {

    public ReporteEntity findByCodigo(String codigo_proveedor);

    @Query(value = "insert into planilla(codigo_proveedor) values(?)",
            nativeQuery = true)
    void insertarDatos(@Param("codigo_proveedor") String codigo_proveedor);


}
