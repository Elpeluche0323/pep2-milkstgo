package milkstgo.proveedorservice.services;


import milkstgo.proveedorservice.entities.ProveedorEntity;
import milkstgo.proveedorservice.models.ReporteModel;
import milkstgo.proveedorservice.repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
@Service
public class ProveedorService {
    @Autowired
    ProveedorRepository proveedorRepository;

    @Autowired
    RestTemplate restTemplate;

    public ArrayList<ProveedorEntity> obtenerProveedor(){
        return (ArrayList<ProveedorEntity>) proveedorRepository.findAll();
    }

    public ProveedorEntity findByCodigo(String codigo){
        return proveedorRepository.findByCodigo(codigo);
    }

    public void guardarProveedor(ProveedorEntity proveedor){
        proveedorRepository.save(proveedor);
    }

    public void eliminarProveedor(){
        proveedorRepository.deleteAll();
    }

    public List<ReporteModel> obtenerReporte(String codigo) {
        List<ReporteModel> reporte = restTemplate.getForObject("http://reporte-service/reporte/porproveedor/" + codigo, List.class);
        return reporte;
    }


}
