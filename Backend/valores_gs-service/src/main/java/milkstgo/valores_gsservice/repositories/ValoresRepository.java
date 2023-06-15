package milkstgo.valores_gsservice.repositories;

import milkstgo.valores_gsservice.entities.ValoresEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface ValoresRepository extends JpaRepository <ValoresEntity, Integer> {
    @Query(value = "select * from datos_gs as e where e.proveedor = :proveedor  limit 1",
            nativeQuery = true)
    ValoresEntity buscarData(@Param("proveedor") String proveedor);

    @Query(value = "select distinct proveedor from datos_gs", nativeQuery = true)
    List<String> findDistinctProveedor();

    @Query(value = "select *  from datos_gs as e where e.proveedor = :proveedor", nativeQuery = true)
    ArrayList<ValoresEntity> eliminarData(@Param("proveedor")String proveedor);

}
