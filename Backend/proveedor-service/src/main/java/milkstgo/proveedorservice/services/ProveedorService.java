package milkstgo.proveedorservice.services;


import milkstgo.proveedorservice.entities.ProveedorEntity;
import milkstgo.proveedorservice.repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ProveedorService {
    @Autowired
    ProveedorRepository proveedorRepository;

    public ArrayList<ProveedorEntity> obtenerProveedor(){
        return (ArrayList<ProveedorEntity>) proveedorRepository.findAll();
    }

    public ProveedorEntity findByCodigo(String codigo){
        return proveedorRepository.findByCodigo(codigo);
    }

    public ProveedorEntity findByNombre(String nombre){
        return proveedorRepository.findByNombre(nombre);
    }

    public void guardarProveedor(ProveedorEntity proveedor){
        proveedorRepository.save(proveedor);
    }

    public void eliminarProveedor(){
        proveedorRepository.deleteAll();
    }

    public ProveedorEntity buscarCategoria(ProveedorEntity categoria){
        return categoria;
    }



}
