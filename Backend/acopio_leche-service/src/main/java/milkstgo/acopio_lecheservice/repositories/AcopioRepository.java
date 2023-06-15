package milkstgo.acopio_lecheservice.repositories;

import milkstgo.acopio_lecheservice.entities.AcopioEntity;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.ArrayList;
import java.util.List;
@Repository
public interface AcopioRepository  extends JpaRepository <AcopioEntity, Integer>{

    @Query(value = "select * from datos_acopio as e where e.proveedor = :proveedor and e.fecha =:fecha limit 1",
            nativeQuery = true)
    AcopioEntity buscarData(@Param("proveedor") String proveedor, @Param("fecha") String fecha);

    @Query(value = "select distinct proveedor from datos_acopio", nativeQuery = true)
    List<String> findDistinctProveedor();

    @Query(value = "select e.fecha from datos_acopio as e where e.proveedor = :proveedor limit 1", nativeQuery = true)
    String buscarFechaProveedor(@Param("proveedor")String proveedor);

    @Query(value = "select *  from datos_acopio as e where e.proveedor = :proveedor", nativeQuery = true)
    ArrayList<AcopioEntity> eliminarData(@Param("proveedor")String proveedor);

}
