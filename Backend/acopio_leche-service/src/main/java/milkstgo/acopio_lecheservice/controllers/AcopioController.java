package milkstgo.acopio_lecheservice.controllers;

import milkstgo.acopio_lecheservice.entities.AcopioEntity;
import milkstgo.acopio_lecheservice.services.AcopioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.ws.rs.Path;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/acopio_leche")
public class AcopioController {

    @Autowired
    private AcopioService acopioService;

    @GetMapping
    public ResponseEntity<ArrayList<AcopioEntity>> obtenerData() {
        ArrayList<AcopioEntity> data = acopioService.obtenerData();
        if (data.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(data);
    }

    @GetMapping("/proveedor")
    public ResponseEntity<List<String>> obtenerProveedorDeData(){
        List<String> proveedor = acopioService.obtenerProveedor();
        if(proveedor.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(proveedor);
    }

    @GetMapping("/acopio/{proveedor}/{fecha}")
    public ResponseEntity<AcopioEntity> obtenerFechaPorProveedor(
            @PathVariable("proveedor") String proveedor, @PathVariable("fecha") String fecha)
    {
        AcopioEntity fecha_envio = acopioService.obtenerAcopio(proveedor, fecha);
        return ResponseEntity.ok(fecha_envio);
    }

    @GetMapping("/primeraasistencia/{proveedor}")
    public ResponseEntity<String> obtenerPrimeraAsistencia(@PathVariable("proveedor") String proveedor){
        String fecha = acopioService.obtenerFechaProveedor(proveedor);
        if (fecha.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(fecha);
    }

    @PostMapping
    public void guardarData(@RequestParam("file") MultipartFile file, RedirectAttributes ms) throws FileNotFoundException, ParseException{
        acopioService.guardar(file);
        acopioService.leerCsv("Acopio.csv");
    }

}
