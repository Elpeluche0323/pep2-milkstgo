package milkstgo.proveedorservice.repositories;

import milkstgo.proveedorservice.entities.ProveedorEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
@Repository
public interface ProveedorRepository extends JpaRepository<ProveedorEntity, String> {

    @Query(value = "select  e from  proveedores e where e.nombre = :nombre",nativeQuery = true)
    ProveedorEntity findByNombre(@Param("nombre") String nombre);

    @Query( value = "select e from proveedores as e where e.codigo = :codigo",nativeQuery = true)
    ProveedorEntity findByCodigo(@Param("codigo")String codigo);
}
