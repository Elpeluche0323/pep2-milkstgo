package milkstgo.proveedorservice.controllers;

import milkstgo.proveedorservice.entities.ProveedorEntity;
import milkstgo.proveedorservice.services.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proveedor")
public class ProveedorController {

    @Autowired
    ProveedorService proveedorService;

    @GetMapping
    public ResponseEntity<List<ProveedorEntity>> obtenerProveedor(){
        List<ProveedorEntity> proveedor = proveedorService.obtenerProveedor();
        if(proveedor.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(proveedor);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<ProveedorEntity> obtenerPorCodigo(@PathVariable("codigo") String codigo){
        ProveedorEntity proveedor = proveedorService.findByCodigo(codigo);
        if(proveedor == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(proveedor);
    }

    @GetMapping("/{codigo}/categoria")
    public ResponseEntity<String> obtenerCategoria(@PathVariable("codigo") String codigo){
        ProveedorEntity proveedor = proveedorService.findByCodigo(codigo);
        if(proveedor == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(proveedor.getCategoria());
    }

    @PostMapping
    public void guardarProveedor(@RequestBody ProveedorEntity proveedor){
        proveedorService.guardarProveedor(proveedor);
    }

    @GetMapping("/eliminar")
    public void eliminarProveedor() {proveedorService.eliminarProveedor();}



}
